package vectorwing.farmersdelight.refabricated.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes an {@link ItemHandler} as NeoForge's transactional item capability.
 * Each slot keeps a snapshot of its stack, restored when the transaction is aborted.
 */
public class ItemHandlerResourceHandler implements ResourceHandler<ItemResource>
{
	private final ItemHandler handler;
	private final List<SlotJournal> journals;

	public ItemHandlerResourceHandler(ItemHandler handler) {
		this.handler = handler;
		this.journals = new ArrayList<>(handler.getSlotCount());
		for (int slot = 0; slot < handler.getSlotCount(); slot++) {
			journals.add(new SlotJournal(slot));
		}
	}

	@Override
	public int size() {
		return handler.getSlotCount();
	}

	@Override
	public ItemResource getResource(int index) {
		return ItemResource.of(handler.getStackInSlot(index));
	}

	@Override
	public long getAmountAsLong(int index) {
		return handler.getStackInSlot(index).getCount();
	}

	@Override
	public long getCapacityAsLong(int index, ItemResource resource) {
		int limit = handler.getSlotLimit(index);
		return resource.isEmpty() ? limit : Math.min(limit, resource.getMaxStackSize());
	}

	@Override
	public boolean isValid(int index, ItemResource resource) {
		return resource.isEmpty() || handler.isItemValid(index, resource.toStack());
	}

	@Override
	public int insert(int index, ItemResource resource, int amount, TransactionContext transaction) {
		if (resource.isEmpty() || amount <= 0) {
			return 0;
		}
		int inserted = amount - handler.insertItem(index, resource.toStack(amount), true).getCount();
		if (inserted > 0) {
			journals.get(index).updateSnapshots(transaction);
			handler.insertItem(index, resource.toStack(inserted), false);
		}
		return inserted;
	}

	@Override
	public int extract(int index, ItemResource resource, int amount, TransactionContext transaction) {
		if (resource.isEmpty() || amount <= 0 || !resource.matches(handler.getStackInSlot(index))) {
			return 0;
		}
		int extracted = handler.extractItem(index, amount, true).getCount();
		if (extracted > 0) {
			journals.get(index).updateSnapshots(transaction);
			handler.extractItem(index, extracted, false);
		}
		return extracted;
	}

	private class SlotJournal extends SnapshotJournal<ItemStack>
	{
		private final int slot;

		SlotJournal(int slot) {
			this.slot = slot;
		}

		@Override
		protected ItemStack createSnapshot() {
			return handler.getStackInSlot(slot).copy();
		}

		@Override
		protected void revertToSnapshot(ItemStack snapshot) {
			handler.setStackInSlot(slot, snapshot);
		}
	}
}

package vectorwing.farmersdelight.common.block.entity.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import vectorwing.farmersdelight.refabricated.inventory.ItemStackHandler;
import vectorwing.farmersdelight.refabricated.inventory.ItemHandler;


public class CookingPotItemHandler implements ItemHandler {
	private static final int SLOTS_INPUT = 6;
	private static final int SLOT_CONTAINER_INPUT = 7;
	private static final int SLOT_MEAL_OUTPUT = 8;
	private final ItemStackHandler itemHandler;
	private final Direction side;

	public CookingPotItemHandler(ItemStackHandler itemHandler, @Nullable Direction side) {
		this.itemHandler = itemHandler;
		this.side = side;
	}

	public boolean isItemValid(int slot, @NonNull ItemStack stack) {
		return itemHandler.isItemValid(slot, stack);
	}

	@Override
	public int getSlotCount() {
		return itemHandler.getSlotCount();
	}

	@Override
	public @NonNull ItemStack getStackInSlot(int slot) {
		return itemHandler.getStackInSlot(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		this.itemHandler.setStackInSlot(slot, stack);
	}

	@NonNull
	public ItemStack insertItem(int slot, @NonNull ItemStack stack, boolean simulate) {
		if (side == null || side.equals(Direction.UP)) {
			return slot < SLOTS_INPUT ? itemHandler.insertItem(slot, stack, simulate) : stack;
		}
		return slot == SLOT_CONTAINER_INPUT ? itemHandler.insertItem(slot, stack, simulate) : stack;
	}

	@NonNull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (side == null || side.equals(Direction.UP)) {
			return slot < SLOTS_INPUT ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
		}
		return slot == SLOT_MEAL_OUTPUT ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		return itemHandler.getSlotLimit(slot);
	}
}

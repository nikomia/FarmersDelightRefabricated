package vectorwing.farmersdelight.common.registry;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import vectorwing.farmersdelight.common.block.entity.inventory.BasketInvWrapper;
import vectorwing.farmersdelight.refabricated.inventory.ItemHandlerResourceHandler;

public class ModCapabilities
{
	public static void register(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.COOKING_POT.get(),
				(be, side) -> new ItemHandlerResourceHandler(be.getStorage(side)));
		event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.CUTTING_BOARD.get(),
				(be, side) -> new ItemHandlerResourceHandler(be.getInventory()));
		event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.BASKET.get(),
				(be, side) -> new ItemHandlerResourceHandler(new BasketInvWrapper(be)));
		event.registerBlockEntity(Capabilities.Item.BLOCK, ModBlockEntityTypes.CABINET.get(),
				(be, side) -> VanillaContainerWrapper.of(be));
	}
}

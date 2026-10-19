package vectorwing.farmersdelight.common.registry;

import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMenu;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.refabricated.RegUtils.regMenu;

public class ModMenuTypes {
    public static final Supplier<MenuType<CookingPotMenu>> COOKING_POT = regMenu("cooking_pot", () -> IMenuTypeExtension.create((windowId, inventory, data) -> new CookingPotMenu(windowId, inventory, data.readBlockPos())));

    public static void touch() {

    }
}

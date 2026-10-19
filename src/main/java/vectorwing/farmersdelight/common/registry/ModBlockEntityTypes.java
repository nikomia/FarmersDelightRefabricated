package vectorwing.farmersdelight.common.registry;

import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import vectorwing.farmersdelight.common.block.entity.*;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.refabricated.RegUtils.regBlockEntity;

public class ModBlockEntityTypes
{
	public static final Supplier<BlockEntityType<StoveBlockEntity>> STOVE = regBlockEntity("stove",
			() -> new BlockEntityType<>(StoveBlockEntity::new, ModBlocks.STOVE.get()));
	public static final Supplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT = regBlockEntity("cooking_pot",
			() -> new BlockEntityType<>(CookingPotBlockEntity::new, ModBlocks.COOKING_POT.get()));
	public static final Supplier<BlockEntityType<BasketBlockEntity>> BASKET = regBlockEntity("basket",
			() -> new BlockEntityType<>(BasketBlockEntity::new, ModBlocks.WOODEN_BASKET.get(), ModBlocks.BAMBOO_BASKET.get()));
	public static final Supplier<BlockEntityType<CuttingBoardBlockEntity>> CUTTING_BOARD = regBlockEntity("cutting_board",
			() -> new BlockEntityType<>(CuttingBoardBlockEntity::new, ModBlocks.CUTTING_BOARD.get()));
	public static final Supplier<BlockEntityType<SkilletBlockEntity>> SKILLET = regBlockEntity("skillet",
			() -> new BlockEntityType<>(SkilletBlockEntity::new, ModBlocks.SKILLET.get()));
	public static final Supplier<BlockEntityType<CabinetBlockEntity>> CABINET = regBlockEntity("cabinet",
			() -> new BlockEntityType<>(CabinetBlockEntity::new,
							ModBlocks.OAK_CABINET.get(),
							ModBlocks.BIRCH_CABINET.get(),
							ModBlocks.SPRUCE_CABINET.get(),
							ModBlocks.JUNGLE_CABINET.get(),
							ModBlocks.ACACIA_CABINET.get(),
							ModBlocks.DARK_OAK_CABINET.get(),
							ModBlocks.MANGROVE_CABINET.get(),
							ModBlocks.BAMBOO_CABINET.get(),
							ModBlocks.CHERRY_CABINET.get(),
							ModBlocks.PALE_OAK_CABINET.get(),
							ModBlocks.POPLAR_CABINET.get(),
							ModBlocks.CRIMSON_CABINET.get(),
							ModBlocks.WARPED_CABINET.get()));
	
	@SafeVarargs
	private static void addSupportedBlocks(BlockEntityTypeAddBlocksEvent event, BlockEntityType<?> type, Supplier<Block>... blocks) {
		event.modify(type, java.util.Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new));
	}

	public static void touch() {
	}

	// Canvas signs are vanilla signs: their blocks are added to the vanilla sign types
	public static void onAddBlocks(BlockEntityTypeAddBlocksEvent event) {
		addSupportedBlocks(event, BlockEntityTypes.SIGN,
			ModBlocks.CANVAS_SIGN,
			ModBlocks.WHITE_CANVAS_SIGN,
			ModBlocks.ORANGE_CANVAS_SIGN,
			ModBlocks.MAGENTA_CANVAS_SIGN,
			ModBlocks.LIGHT_BLUE_CANVAS_SIGN,
			ModBlocks.YELLOW_CANVAS_SIGN,
			ModBlocks.LIME_CANVAS_SIGN,
			ModBlocks.PINK_CANVAS_SIGN,
			ModBlocks.GRAY_CANVAS_SIGN,
			ModBlocks.LIGHT_GRAY_CANVAS_SIGN,
			ModBlocks.CYAN_CANVAS_SIGN,
			ModBlocks.PURPLE_CANVAS_SIGN,
			ModBlocks.BLUE_CANVAS_SIGN,
			ModBlocks.BROWN_CANVAS_SIGN,
			ModBlocks.GREEN_CANVAS_SIGN,
			ModBlocks.RED_CANVAS_SIGN,
			ModBlocks.BLACK_CANVAS_SIGN,
			ModBlocks.CANVAS_WALL_SIGN,
			ModBlocks.WHITE_CANVAS_WALL_SIGN,
			ModBlocks.ORANGE_CANVAS_WALL_SIGN,
			ModBlocks.MAGENTA_CANVAS_WALL_SIGN,
			ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN,
			ModBlocks.YELLOW_CANVAS_WALL_SIGN,
			ModBlocks.LIME_CANVAS_WALL_SIGN,
			ModBlocks.PINK_CANVAS_WALL_SIGN,
			ModBlocks.GRAY_CANVAS_WALL_SIGN,
			ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN,
			ModBlocks.CYAN_CANVAS_WALL_SIGN,
			ModBlocks.PURPLE_CANVAS_WALL_SIGN,
			ModBlocks.BLUE_CANVAS_WALL_SIGN,
			ModBlocks.BROWN_CANVAS_WALL_SIGN,
			ModBlocks.GREEN_CANVAS_WALL_SIGN,
			ModBlocks.RED_CANVAS_WALL_SIGN,
			ModBlocks.BLACK_CANVAS_WALL_SIGN);
		addSupportedBlocks(event, BlockEntityTypes.HANGING_SIGN,
			ModBlocks.HANGING_CANVAS_SIGN,
			ModBlocks.WHITE_HANGING_CANVAS_SIGN,
			ModBlocks.ORANGE_HANGING_CANVAS_SIGN,
			ModBlocks.MAGENTA_HANGING_CANVAS_SIGN,
			ModBlocks.LIGHT_BLUE_HANGING_CANVAS_SIGN,
			ModBlocks.YELLOW_HANGING_CANVAS_SIGN,
			ModBlocks.LIME_HANGING_CANVAS_SIGN,
			ModBlocks.PINK_HANGING_CANVAS_SIGN,
			ModBlocks.GRAY_HANGING_CANVAS_SIGN,
			ModBlocks.LIGHT_GRAY_HANGING_CANVAS_SIGN,
			ModBlocks.CYAN_HANGING_CANVAS_SIGN,
			ModBlocks.PURPLE_HANGING_CANVAS_SIGN,
			ModBlocks.BLUE_HANGING_CANVAS_SIGN,
			ModBlocks.BROWN_HANGING_CANVAS_SIGN,
			ModBlocks.GREEN_HANGING_CANVAS_SIGN,
			ModBlocks.RED_HANGING_CANVAS_SIGN,
			ModBlocks.BLACK_HANGING_CANVAS_SIGN,
			ModBlocks.HANGING_CANVAS_WALL_SIGN,
			ModBlocks.WHITE_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.ORANGE_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.MAGENTA_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.LIGHT_BLUE_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.YELLOW_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.LIME_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.PINK_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.GRAY_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.LIGHT_GRAY_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.CYAN_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.PURPLE_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.BLUE_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.BROWN_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.GREEN_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.RED_HANGING_CANVAS_WALL_SIGN,
			ModBlocks.BLACK_HANGING_CANVAS_WALL_SIGN);
	}
}

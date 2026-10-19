package vectorwing.farmersdelight.integration.jei;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import org.jspecify.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.List;

@EventBusSubscriber(modid = FarmersDelight.MODID, value = Dist.CLIENT)
public class FDRecipes
{
	private static @Nullable RecipeMap recipeMap;

	public FDRecipes() {
		if (recipeMap == null) {
			throw new NullPointerException("Recipes have not been received from the server yet.");
		}
	}

	@SubscribeEvent
	public static void receiveRecipes(RecipesReceivedEvent event) {
		recipeMap = event.getRecipeMap();
	}

	public List<RecipeHolder<CookingPotRecipe>> getCookingPotRecipes() {
		return List.copyOf(recipeMap.byType(ModRecipeTypes.COOKING.get()));
	}

	public List<RecipeHolder<CuttingBoardRecipe>> getCuttingBoardRecipes() {
		return List.copyOf(recipeMap.byType(ModRecipeTypes.CUTTING.get()));
	}
}

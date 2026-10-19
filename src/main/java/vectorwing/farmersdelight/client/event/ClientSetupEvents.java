package vectorwing.farmersdelight.client.event;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.client.gui.CookingPotScreen;
import vectorwing.farmersdelight.client.gui.CookingPotTooltip;
import vectorwing.farmersdelight.client.model.SkilletCookingConditionalItemModelProperty;
import vectorwing.farmersdelight.client.particle.SparkleParticle;
import vectorwing.farmersdelight.client.particle.StarParticle;
import vectorwing.farmersdelight.client.particle.SteamParticle;
import vectorwing.farmersdelight.client.renderer.CuttingBoardRenderer;
import vectorwing.farmersdelight.client.renderer.DefaultStoveRenderer;
import vectorwing.farmersdelight.client.renderer.SkilletFlipItemRenderer;
import vectorwing.farmersdelight.client.renderer.SkilletRenderer;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModEntityTypes;
import vectorwing.farmersdelight.common.registry.ModMenuTypes;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.registry.ModRecipeBookCategories;

public class ClientSetupEvents
{
	public static void registerRecipeBookCategories(RegisterRecipeBookSearchCategoriesEvent event) {
		event.register(ModRecipeBookCategories.COOKING_SEARCH.get(), ModRecipeBookCategories.COOKING_MEALS.get(),
				ModRecipeBookCategories.COOKING_DRINKS.get(), ModRecipeBookCategories.COOKING_MISC.get());
	}

	public static void registerCustomTooltipRenderers(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(CookingPotTooltip.CookingPotTooltipComponent.class, CookingPotTooltip::new);
	}

	public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntityTypes.ROTTEN_TOMATO.get(), ThrownItemRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntityTypes.STOVE.get(), DefaultStoveRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntityTypes.CUTTING_BOARD.get(), CuttingBoardRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntityTypes.SKILLET.get(), SkilletRenderer::new);
	}

	public static void registerMenuScreens(RegisterMenuScreensEvent event) {
		event.register(ModMenuTypes.COOKING_POT.get(), CookingPotScreen::new);
	}

	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.STAR.get(), StarParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.STEAM.get(), SteamParticle.Factory::new);
		event.registerSpriteSet(ModParticleTypes.SPARKLE.get(), SparkleParticle.Factory::new);
	}

	public static void registerItemModels(RegisterItemModelsEvent event) {
		event.register(SkilletFlipItemRenderer.ID, SkilletFlipItemRenderer.Unbaked.CODEC);
	}

	public static void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
		event.register(FarmersDelight.id("skillet/is_cooking"), SkilletCookingConditionalItemModelProperty.MAP_CODEC);
	}
}

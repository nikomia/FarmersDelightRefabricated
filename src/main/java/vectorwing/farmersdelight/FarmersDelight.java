package vectorwing.farmersdelight;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vectorwing.farmersdelight.common.CommonSetup;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.event.CommonModBusEvents;
import vectorwing.farmersdelight.common.event.NeoForgeEvents;
import vectorwing.farmersdelight.common.network.ModNetworking;
import vectorwing.farmersdelight.common.registry.*;
import vectorwing.farmersdelight.refabricated.CanItemPerformAbility;
import vectorwing.farmersdelight.refabricated.CompostableHelper;

@Mod(FarmersDelight.MODID)
public class FarmersDelight
{
	public static final String MODID = "farmersdelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(FarmersDelight.class);

	public static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(MODID, name);
	}

	public FarmersDelight(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.LOCAL, Configuration.COMMON_CONFIG);
		modContainer.registerConfig(ModConfig.Type.CLIENT, Configuration.CLIENT_CONFIG);

		modEventBus.addListener(FarmersDelight::registerAll);
		ModConditionCodecs.CONDITION_CODECS.register(modEventBus);
		ModIngredientTypes.INGREDIENT_TYPES.register(modEventBus);
		ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
		ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);

		modEventBus.addListener((FMLCommonSetupEvent event) -> event.enqueueWork(CommonSetup::init));
		modEventBus.addListener((ModifyDefaultComponentsEvent event) -> {
			CommonModBusEvents.onModifyDefaultComponents(event);
			CompostableHelper.apply(event);
		});
		modEventBus.addListener(ModBlockEntityTypes::onAddBlocks);
		modEventBus.addListener(ModCapabilities::register);
		modEventBus.addListener(ModNetworking::register);

		NeoForgeEvents.register(NeoForge.EVENT_BUS);
	}

	// Registries are open only while RegisterEvent is fired, so everything is registered on its first call
	private static boolean registered = false;

	private static void registerAll(RegisterEvent event) {
		if (registered || !event.getRegistryKey().equals(Registries.BLOCK)) return;
		registered = true;

		ModSounds.touch();
		ModBlocks.touch();
		ModItems.touch();
		ModEffects.touch();
		ModParticleTypes.touch();
		ModDataComponents.touch();
		ModEntityTypes.touch();
		ModBlockEntityTypes.touch();
		ModMenuTypes.touch();
		ModRecipeTypes.touch();
		ModRecipeSerializers.touch();
		ModBiomeFeatures.touch();
		ModAdvancements.touch();
		ModPlacementModifiers.touch();
		ModLootFunctions.touch();
		ModCreativeTabs.touch();
		CanItemPerformAbility.init();
		ModConsumeEffectTypes.touch();
		ModRecipeDisplays.touch();
		ModRecipeBookCategories.touch();

		RegistryAliases.addRegistryAliases();
	}
}

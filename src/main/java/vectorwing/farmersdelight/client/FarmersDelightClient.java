package vectorwing.farmersdelight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.client.event.ClientSetupEvents;
import vectorwing.farmersdelight.client.event.TooltipEvents;
import vectorwing.farmersdelight.common.item.SkilletItem;
import vectorwing.farmersdelight.common.network.ModNetworking;
import vectorwing.farmersdelight.common.network.payload.FlipSkilletPayload;
import vectorwing.farmersdelight.common.registry.ModDataComponents;

@Mod(value = FarmersDelight.MODID, dist = Dist.CLIENT)
public class FarmersDelightClient
{
    public static boolean naturalRegenerationEnabled = false;

    public FarmersDelightClient(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modEventBus.addListener(ClientSetupEvents::registerCustomTooltipRenderers);
        modEventBus.addListener(ClientSetupEvents::onRegisterRenderers);
        modEventBus.addListener(ClientSetupEvents::registerParticles);
        modEventBus.addListener(ClientSetupEvents::registerMenuScreens);
        modEventBus.addListener(ClientSetupEvents::registerItemModels);
        modEventBus.addListener(ClientSetupEvents::registerConditionalItemModelProperties);
        modEventBus.addListener(ClientSetupEvents::registerRecipeBookCategories);
        modEventBus.addListener(ModNetworking.ClientPayloadHandler::register);

        NeoForge.EVENT_BUS.addListener((ItemTooltipEvent event) ->
                TooltipEvents.addTooltipToVanillaSoups(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip()));
        NeoForge.EVENT_BUS.addListener(FarmersDelightClient::onClientTick);
    }

    // Attacking while holding up a Skillet flips the food in it. Vanilla drops attack clicks while an item is in use
    // and fires no input event for them, so they are taken here, right before handleKeybinds would discard them.
    private static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || player.isSpectator() || minecraft.gui.screen() != null || minecraft.gui.overlay() != null
                || !player.isUsingItem() || !(player.getUseItem().getItem() instanceof SkilletItem)) {
            return;
        }
        boolean clicked = false;
        while (minecraft.options.keyAttack.consumeClick()) {
            clicked = true;
        }
        if (clicked && !player.getUseItem().has(ModDataComponents.SKILLET_FLIP_TIMESTAMP.get())) {
            ClientPacketDistributor.sendToServer(FlipSkilletPayload.INSTANCE);
        }
    }
}

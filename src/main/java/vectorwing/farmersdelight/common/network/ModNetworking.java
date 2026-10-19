package vectorwing.farmersdelight.common.network;

import net.minecraft.world.item.BoneMealItem;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import vectorwing.farmersdelight.common.network.payload.FlipSkilletPayload;
import vectorwing.farmersdelight.common.network.payload.RichSoilBoostParticlesPayload;
import vectorwing.farmersdelight.common.network.payload.SendNaturalRegenerationValuePayload;
import net.minecraft.server.level.ServerPlayer;

public class ModNetworking {

	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1").optional();
		registrar.playToClient(RichSoilBoostParticlesPayload.TYPE, RichSoilBoostParticlesPayload.STREAM_CODEC);
		registrar.playToClient(SendNaturalRegenerationValuePayload.TYPE, SendNaturalRegenerationValuePayload.STREAM_CODEC);
		registrar.playToServer(FlipSkilletPayload.TYPE, FlipSkilletPayload.STREAM_CODEC,
				(payload, context) -> payload.handle(context.player().level().getServer(), (ServerPlayer) context.player()));
	}

	public static class ClientPayloadHandler
	{
		public static void register(RegisterClientPayloadHandlersEvent event) {
			event.register(RichSoilBoostParticlesPayload.TYPE, ClientPayloadHandler::handleRichSoilBoostParticles);
			event.register(SendNaturalRegenerationValuePayload.TYPE, (payload, context) -> payload.handle());
		}

		public static void handleRichSoilBoostParticles(RichSoilBoostParticlesPayload payload, IPayloadContext context) {
			BoneMealItem.addGrowthParticles(context.player().level(), payload.pos(), 15);
		}
	}
}

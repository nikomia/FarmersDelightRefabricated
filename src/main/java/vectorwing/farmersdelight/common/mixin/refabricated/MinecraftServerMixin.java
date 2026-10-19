package vectorwing.farmersdelight.common.mixin.refabricated;

import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.network.payload.SendNaturalRegenerationValuePayload;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Shadow
	private PlayerList playerList;

	// Silly little workaround for gamerules not being synced to the client.
	// Youer adds a ServerLevel parameter to onGameRuleChanged; plain NeoForge (singleplayer) keeps the vanilla one.
	@Inject(method = "onGameRuleChanged(Lnet/minecraft/world/level/gamerules/GameRule;Ljava/lang/Object;)V", at = @At("HEAD"), require = 0)
	private <T> void fdrf$syncNaturalRegenerationGameRule(GameRule<T> rule, T value, CallbackInfo ci) {
		fdrf$sync(rule, value);
	}

	@Inject(method = "onGameRuleChanged(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/gamerules/GameRule;Ljava/lang/Object;)V", at = @At("HEAD"), require = 0)
	private <T> void fdrf$syncNaturalRegenerationGameRuleYouer(ServerLevel level, GameRule<T> rule, T value, CallbackInfo ci) {
		fdrf$sync(rule, value);
	}

	@Unique
	private <T> void fdrf$sync(GameRule<T> rule, T value) {
		if (rule == GameRules.NATURAL_HEALTH_REGENERATION) {
			this.playerList.getPlayers().forEach((player) -> PacketDistributor.sendToPlayer(player, new SendNaturalRegenerationValuePayload((boolean) value)));
		}
	}
}

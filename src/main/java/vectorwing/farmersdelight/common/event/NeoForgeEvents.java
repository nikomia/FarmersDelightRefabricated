package vectorwing.farmersdelight.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.item.DogFoodItem;
import vectorwing.farmersdelight.common.item.HorseFeedItem;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.item.SkilletItem;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;
import vectorwing.farmersdelight.common.network.payload.SendNaturalRegenerationValuePayload;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.world.VillageStructures;

/**
 * Game events that Farmer's Delight Refabricated hooks through Fabric callbacks and mixins.
 */
public class NeoForgeEvents
{
	public static void register(IEventBus bus) {
		bus.addListener(NeoForgeEvents::onRightClickBlock);
		bus.addListener(NeoForgeEvents::onEntityInteract);
		bus.addListener((ServerAboutToStartEvent event) -> VillageStructures.addNewVillageBuilding(event.getServer()));
		bus.addListener(NeoForgeEvents::onPlayerLoggedIn);
		bus.addListener((OnDatapackSyncEvent event) -> event.sendRecipes(ModRecipeTypes.COOKING.get(), ModRecipeTypes.CUTTING.get()));
		bus.addListener(NeoForgeEvents::onIncomingDamage);
		bus.addListener(NeoForgeEvents::onDamage);
		bus.addListener(NeoForgeEvents::onKnockBack);
	}

	private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		BlockHitResult hit = event.getHitVec();
		InteractionResult result = CuttingBoardBlock.ToolCarvingEvent.onSneakPlaceTool(event.getEntity(), event.getLevel(), event.getHand(), hit);
		if (result == InteractionResult.PASS) {
			result = KnifeItem.KnifeEvents.onCakeInteraction(event.getEntity(), event.getLevel(), event.getHand(), hit);
		}
		if (result != InteractionResult.PASS) {
			event.setCanceled(true);
			event.setCancellationResult(result);
		}
	}

	private static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		InteractionResult result = DogFoodItem.DogFoodEvent.onDogFoodApplied(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget(), null);
		if (result == InteractionResult.PASS) {
			result = HorseFeedItem.HorseFeedEvent.onHorseFeedApplied(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget(), null);
		}
		if (result != InteractionResult.PASS) {
			event.setCanceled(true);
			event.setCancellationResult(result);
		}
	}

	private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			boolean naturalRegeneration = player.level().getGameRules().get(GameRules.NATURAL_HEALTH_REGENERATION);
			PacketDistributor.sendToPlayer(player, new SendNaturalRegenerationValuePayload(naturalRegeneration));
		}
	}

	private static void onIncomingDamage(LivingIncomingDamageEvent event) {
		if (event.getAmount() > 0) {
			event.setAmount(BackstabbingEnchantment.BackstabbingEvent.onKnifeBackstab(event.getEntity(), event.getSource(), event.getAmount()));
		}
	}

	// Attack strength is still intact here, unlike in the Fabric port
	private static void onDamage(LivingDamageEvent.Pre event) {
		if (event.getSource().getDirectEntity() instanceof Player player) {
			SkilletItem.SkilletEvents.attackPower = player.getAttackStrengthScale(0.0F);
		}
		SkilletItem.SkilletEvents.playSkilletAttackSound(event.getEntity(), event.getSource());
	}

	private static void onKnockBack(LivingKnockBackEvent event) {
		event.setStrength((float) KnifeItem.KnifeEvents.onKnifeKnockback(event.getStrength(), event.getEntity()));
	}
}

package vectorwing.farmersdelight.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

// Kept out of common classes so a dedicated server never loads client-only types
public class ClientPlayerAccess
{
	public static Player getPlayer() {
		return Minecraft.getInstance().player;
	}
}

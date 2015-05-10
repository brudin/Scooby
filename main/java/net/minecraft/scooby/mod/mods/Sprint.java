package net.minecraft.scooby.mod.mods;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;

/**
 * Sprint is a mod that will automatically sprint for the player.  It is currently using the existing keycode for sprint,
 * though this works differently than Minecraft's keybind.  If this is enabled, this will always sprint (if you meet
 * a certain criteria).  With Minecraft's, it will sprint once, and then once you stop sprinting you have to press
 * the button again.
 *
 * @author b
 * @since 1:29 PM on 3/16/2015
 */
public class Sprint extends Mod {

	public Sprint(Scooby scooby) {
		super(scooby, scooby.mc.gameSettings.keyBindSprint.getKeyCode());
	}

	@Override
	public void onLivingUpdate(EntityPlayerSP player) {
		player.setSprinting(shouldSprint(player));
	}

	/**
	 * Checks if the player should sprint.  Basic checks so that you won't be sprinting while standing still,
	 * sneaking, using items (eating), etc.
	 *
	 * @param player	The target player for sprint checking.
	 * @return			<code>true</code> if the player can sprint, else <code>false</code>.
	 */
	private boolean shouldSprint(EntityPlayerSP player) {
		return player.moveForward > 0 && !player.isSneaking() && !player.isUsingItem()
				&& player.getFoodStats().getFoodLevel() > 6;
	}

	@Override
	public void onWorldUnload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerClone(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClientTickPost() {
		// TODO Auto-generated method stub
		
	}
}

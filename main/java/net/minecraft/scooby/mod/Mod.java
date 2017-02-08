package net.minecraft.scooby.mod;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scooby.Scooby;
import net.minecraft.world.World;

/**
 * @author b
 * @since 3:40 PM on 3/15/2015
 */
public abstract class Mod {

	/* Instance of the Scooby class */
	protected Scooby scooby;

	/* The key code that is used to toggle the mod */
	private int toggleKey = -1;

	/* Whether or not the mod is currently enabled */
	private boolean enabled = false;

	public Mod(Scooby scooby, int toggleKey) {
		this.scooby = scooby;
		this.toggleKey = toggleKey;
	}

	/**
	 * This is the key code used to toggle the mod.
	 */
	public int getToggleKey() {
		return this.toggleKey;
	}

	/**
	 * This method toggles the mod based on the specified state.
	 *
	 * @param	enabled	<code>true</code> if you want the mod enabled, else <code>false</code>.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * This method returns <code>true</code> if the mod is enabled.
	 *
	 * @return	<code>true</code> if the mod is currently enabled, else <code>false</code>.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Called with the LivingEvent.LivingUpdateEvent event.
	 * @param 	player	The player, aka you
	 * @see 	net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent#LivingUpdateEvent
	 */
	public abstract void onLivingUpdate(EntityPlayerSP player);
	public abstract void onWorldUnload();
	public abstract void onPlayerRespawn(EntityPlayerSP player);
	public abstract void onClientTickPost();
	public abstract void onAttackPlayer(EntityPlayer target);
}

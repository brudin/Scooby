package net.minecraft.scooby.mod.mods;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;

import java.util.Random;

/**
 * TriggerBot is a mod that automatically attacks a player if your mouse is currently over them.  Not to be confused
 * with something like a KillAura where it would automatically attack an entity without having to have your mouse over
 * them.
 *
 * This uses a Timer with a random delay to wait between each hits.  I've heard some servers like BadLion check for
 * consistent hits, so in theory this should bypass it; it's basically emulating legitimate PVP.
 *
 * @author b
 * @since 3:42 PM on 3/15/2015
 */
public class TriggerBot extends Mod {

	/* Used for waiting between attacking */
	private Timer timer;

	/* Used to generate a random time to wait for between attacking */
	private Random random = new Random();

	public TriggerBot(Scooby scooby) {
		super(scooby, Keyboard.KEY_R);
		this.timer = new Timer();
	}

	@Override
	public void onLivingUpdate(EntityPlayerSP player) {
		float delay = random.nextFloat() / 2; //Fraction of a second that it should wait for attacking
		if (scooby.mc.objectMouseOver != null) {
			if (scooby.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				Entity entity = scooby.mc.objectMouseOver.entityHit;
				if (entity instanceof EntityPlayer && entity.isEntityAlive()) {
					if (timer.hasReach(delay)) {
						this.attackEntity((EntityPlayer)entity);
						timer.reset();
					}
				}
			}
		}
	}

	/**
	 * Attacks the specified entity.
	 * Copied directly from the clickMouse() method in Minecraft.java.
	 * This is literally what happens when you click your mouse in vanilla. Nothing more.
	 *
	 * @param entity	An instance of <code>net.minecraft.entity.player.EntityPlayer</code> to be attacked.
	 */
	private void attackEntity(EntityLivingBase entity) {
		if (!canAttack(scooby.mc.thePlayer, entity)) {
			return;
		}
		scooby.mc.thePlayer.swingItem();
		scooby.mc.playerController.attackEntity(scooby.mc.thePlayer, entity);
	}

	/**
	 * Checks if the <code>player</code> is able to attack the <code>target</code>.
	 * I check if the <code>currentScreen</code> is null since some people pointed out that attacking with
	 * your inventory open is obvious.
	 * <code>player.isUsingItem()</code> is if the player is doing something like eating, pulling back a bow, blocking,
	 * etc.
	 *
	 * @param player	The player that is going to attack the target.
	 * @param target	The target entity that the player will attack.
	 * @return
	 */
	private boolean canAttack(EntityPlayerSP player, EntityLivingBase target) {
		return scooby.mc.currentScreen == null && !target.isInvisible() && !player.isUsingItem();
	}

	private class Timer {

		private long lastCheck = getSystemTime();

		/**
		 * Checks if the specified amount of second(s) has passed.
		 *
		 * @param seconds	The specified seconds since last check.
		 * @return			<code>true</code> if the time has passed, else <code>false</code>.
		 */
		public boolean hasReach(float seconds) {
			return getTimePassed() >= (seconds * 1000);
		}

		/**
		 * Resets the <code>lastCheck</code>
		 */
		public void reset() {
			lastCheck = getSystemTime();
		}

		/**
		 * @return The amount of time (in milliseconds) since the <code>lastCheck</code>.
		 */
		private long getTimePassed() {
			return getSystemTime() - lastCheck;
		}

		/**
		 * @return The current system time (in milliseconds).
		 */
		private long getSystemTime() {
			return System.nanoTime() / (long) (1E6);
		}
	}
}

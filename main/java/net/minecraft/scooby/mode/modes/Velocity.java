package net.minecraft.scooby.mode.modes;

import java.util.Random;

import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mode.Mode;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import org.lwjgl.input.Keyboard;

/**
 * Velocity makes you take less velocity, you dingus. Key bind to enable it is the 'V' key.
 * @author pootPoot
 * @since brudin started ignoring my pull request... :'C
 */
public class Velocity extends Mode {

	private float prevHealth = -999.0F; // Because Minecraft Forge is dumb and doesn't have an Event for velocity being added to an Entity...
	private Random rand = new Random();
	public Velocity(Scooby scooby) {
		super(scooby, Keyboard.KEY_V);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if (event instanceof LivingUpdateEvent && ((LivingUpdateEvent) event).entity.equals(scooby.mc.thePlayer)) {
			float currentHealth = scooby.mc.thePlayer.getHealth();
			if (prevHealth == -999.0F || currentHealth > prevHealth) {
				prevHealth = currentHealth;
			}
			else if (currentHealth < prevHealth && (scooby.mc.thePlayer.motionX != 0.0D || scooby.mc.thePlayer.motionY != 0.0D || scooby.mc.thePlayer.motionZ != 0.0D)) {
				double randHMultiplier = rand.nextDouble() * 0.75D;
				while (randHMultiplier < 0.5D) {
					randHMultiplier = rand.nextDouble() * 0.75D;
				}
				scooby.mc.thePlayer.motionX *= randHMultiplier;
				scooby.mc.thePlayer.motionZ *= randHMultiplier;
				double randYMultiplier = rand.nextDouble() * 0.75D;
				while (randYMultiplier < 0.5D) {
					randYMultiplier = rand.nextDouble() * 0.75D;
				}
				scooby.mc.thePlayer.motionY *= randYMultiplier;
				prevHealth = currentHealth;
			}
		}
		else if (event instanceof WorldEvent.Unload || (event instanceof PlayerEvent.Clone && ((PlayerEvent.Clone) event).wasDeath)) {
			prevHealth = -999.0F;
		}
	}

}

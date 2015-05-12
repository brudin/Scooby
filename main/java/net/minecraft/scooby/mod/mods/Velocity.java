package net.minecraft.scooby.mod.mods;

import java.util.Random;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;

import org.lwjgl.input.Keyboard;

/**
 * Velocity makes you take less velocity, you dingus. Key bind to enable it is the 'V' key.
 * @author pootPoot
 * @since brudin started ignoring my pull request... :'C
 */
public class Velocity extends Mod {

	private float prevHealth = -999.0F; // Because Minecraft Forge is dumb and doesn't have an Event for velocity being added to an Entity...
	private Random rand = new Random();
	public Velocity(Scooby scooby) {
		super(scooby, Keyboard.KEY_V);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClientTickPost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLivingUpdate(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		float currentHealth = player.getHealth();
		if (prevHealth == -999.0F || currentHealth > prevHealth) {
			prevHealth = currentHealth;
		}
		else if (currentHealth < prevHealth && (player.motionX != 0.0D || player.motionY != 0.0D || player.motionZ != 0.0D)) {
			double randHMultiplier = rand.nextDouble() * 0.75D;
			while (randHMultiplier < 0.5D) {
				randHMultiplier = rand.nextDouble() * 0.75D;
			}
			player.motionX *= randHMultiplier;
			player.motionZ *= randHMultiplier;
			double randYMultiplier = rand.nextDouble() * 0.75D;
			while (randYMultiplier < 0.5D) {
				randYMultiplier = rand.nextDouble() * 0.75D;
			}
			player.motionY *= randYMultiplier;
			prevHealth = currentHealth;
		}
	}

	@Override
	public void onPlayerClone(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		prevHealth = -999.0F;
	}

	@Override
	public void onWorldUnload() {
		// TODO Auto-generated method stub
		prevHealth = -999.0F;
	}

}

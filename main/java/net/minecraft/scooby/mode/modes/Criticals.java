package net.minecraft.scooby.mode.modes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mode.Mode;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import org.lwjgl.input.Keyboard;

public class Criticals extends Mode {

	public Criticals(Scooby scooby) {
		super(scooby, Keyboard.KEY_C);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if (event instanceof AttackEntityEvent) {
			AttackEntityEvent attackEntityEvent = (AttackEntityEvent) event;
			if (attackEntityEvent.entityPlayer.equals(scooby.mc.thePlayer) && attackEntityEvent.target instanceof EntityPlayer && scooby.mc.thePlayer.onGround && !scooby.mc.thePlayer.isOnLadder() && !scooby.mc.thePlayer.isInWater() && !scooby.mc.thePlayer.isPotionActive(Potion.blindness) && scooby.mc.thePlayer.ridingEntity == null) {
				scooby.mc.thePlayer.jump();
			}
		}
	}

}

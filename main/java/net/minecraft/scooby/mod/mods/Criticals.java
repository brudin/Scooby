package net.minecraft.scooby.mod.mods;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;

import org.lwjgl.input.Keyboard;

public class Criticals extends Mod {

	public Criticals(Scooby scooby) {
		super(scooby, Keyboard.KEY_C);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttackPlayer(EntityPlayer target) {
		// TODO Auto-generated method stub
		if (scooby.mc.thePlayer.onGround && !scooby.mc.thePlayer.isOnLadder() && !scooby.mc.thePlayer.isInWater() && !scooby.mc.thePlayer.isPotionActive(Potion.blindness) && scooby.mc.thePlayer.ridingEntity == null) {
			scooby.mc.thePlayer.jump();
		}
	}

	@Override
	public void onClientTickPost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLivingUpdate(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWorldUnload() {
		// TODO Auto-generated method stub
		
	}

}

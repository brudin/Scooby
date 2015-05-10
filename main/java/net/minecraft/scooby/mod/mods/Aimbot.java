package net.minecraft.scooby.mod.mods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.input.Keyboard;

public class Aimbot extends Mod {

	private EntityPlayer targetPlayer;
	private Random rand = new Random();
	private int slowRotationsCount;
	private float slowRotationIncrement;
	public Aimbot(Scooby scooby) {
		super(scooby, Keyboard.KEY_I);
		// TODO Auto-generated constructor stub
	}

	private boolean canSeePlayer(EntityPlayer player) {
		return scooby.mc.thePlayer.worldObj.rayTraceBlocks(new Vec3(scooby.mc.thePlayer.posX, scooby.mc.thePlayer.posY + scooby.mc.thePlayer.getEyeHeight(), scooby.mc.thePlayer.posZ), new Vec3(player.posX, player.getEntityBoundingBox().minY + player.getEyeHeight() + player.getYOffset(), player.posZ)) == null;
	}

	private void facePlayer(EntityPlayer player, float maxIncrementYaw, float maxIncrementPitch)
	{
		double deltaX = player.posX - scooby.mc.thePlayer.posX, deltaZ = player.posZ - scooby.mc.thePlayer.posZ;
		scooby.mc.thePlayer.rotationYaw = updateRotation(scooby.mc.thePlayer.rotationYaw, (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F, maxIncrementYaw);
		scooby.mc.thePlayer.rotationPitch = updateRotation(scooby.mc.thePlayer.rotationPitch, (float) -(Math.atan2(player.getEntityBoundingBox().minY + player.getEyeHeight() + player.getYOffset() - (scooby.mc.thePlayer.posY + scooby.mc.thePlayer.getEyeHeight()), MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ)) * 180.0D / Math.PI), maxIncrementPitch);
	}
	@Override
	public void onClientTickPost() {
		// TODO Auto-generated method stub
		if (scooby.mc.currentScreen != null) {
			return;
		}
		float reachDistance = scooby.mc.playerController.getBlockReachDistance();
		if (targetPlayer != null && slowRotationsCount == 0) {
			float prevRotationYaw = scooby.mc.thePlayer.rotationYaw, prevRotationPitch = scooby.mc.thePlayer.rotationPitch;
			facePlayer(targetPlayer, 360.0F, 360.0F);
			float distanceToPlayer = scooby.mc.thePlayer.getDistanceToEntity(targetPlayer);
			if (distanceToPlayer > reachDistance) {
				distanceToPlayer = reachDistance;
			}
			float deltaAngle = MathHelper.abs(MathHelper.wrapAngleTo180_float(prevRotationYaw) - MathHelper.wrapAngleTo180_float(scooby.mc.thePlayer.rotationYaw)) + MathHelper.abs(MathHelper.wrapAngleTo180_float(prevRotationPitch) - MathHelper.wrapAngleTo180_float(scooby.mc.thePlayer.rotationPitch)), randMaxAngle = rand.nextFloat() * 10.0F / reachDistance * distanceToPlayer, minRandAngle = 5.0F / reachDistance * distanceToPlayer;
			while (randMaxAngle < minRandAngle) {
				randMaxAngle = rand.nextFloat() * 10.0F / reachDistance * distanceToPlayer;
			}
			if (deltaAngle <= randMaxAngle) {
				slowRotationsCount = 2;
				slowRotationIncrement = randMaxAngle / slowRotationsCount;
			}
			scooby.mc.thePlayer.rotationYaw = prevRotationYaw;
			scooby.mc.thePlayer.rotationPitch = prevRotationPitch;
			if (slowRotationsCount > 0) {
				return;
			}
		}
		List sortedPlayers = new ArrayList();
		sortedPlayers.addAll(scooby.mc.theWorld.playerEntities);
		sortedPlayers.remove(scooby.mc.thePlayer);
		Collections.sort(sortedPlayers, new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				return Float.compare(((Entity) arg0).getDistanceToEntity(scooby.mc.thePlayer), ((Entity) arg1).getDistanceToEntity(scooby.mc.thePlayer));
			}

		});
		boolean hasFacedPlayer = false;
		if (targetPlayer != null && !targetPlayer.isEntityAlive()) {
			targetPlayer = null;
			slowRotationsCount = 0;
			slowRotationIncrement = 0.0F;
		}
		float randIncrementYaw = rand.nextFloat() * 45.0F;
		while (randIncrementYaw < 30.0F) {
			randIncrementYaw = rand.nextFloat() * 45.0F;
		}
		float randIncrementPitch = rand.nextFloat() * 45.0F;
		while (randIncrementPitch < 30.0F) {
			randIncrementPitch = rand.nextFloat() * 45.0F;
		}
		if (targetPlayer != null && (scooby.mc.objectMouseOver == null || scooby.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || (scooby.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && scooby.mc.objectMouseOver.entityHit.equals(targetPlayer)))) {
			for (Object currentObj : sortedPlayers) {
				EntityPlayer currentPlayer = (EntityPlayer) currentObj;
				if (currentPlayer.equals(targetPlayer) && currentPlayer.isEntityAlive() && currentPlayer.getDistanceToEntity(scooby.mc.thePlayer) < reachDistance && canSeePlayer(currentPlayer) && !currentPlayer.isInvisible()) {
					facePlayer(targetPlayer, slowRotationsCount == 0 ? randIncrementYaw : slowRotationIncrement, slowRotationsCount == 0 ? randIncrementPitch : slowRotationIncrement);
					hasFacedPlayer = true;
					if (slowRotationsCount > 0) {
						--slowRotationsCount;
					}
					break;
				}
			}
		}
		if (!hasFacedPlayer) {
			for (Object currentObj : sortedPlayers) {
				EntityPlayer currentPlayer = (EntityPlayer) currentObj;
				if (currentPlayer.isEntityAlive() && currentPlayer.getDistanceToEntity(scooby.mc.thePlayer) < reachDistance && canSeePlayer(currentPlayer) && !currentPlayer.isInvisible()) {
					facePlayer(currentPlayer, randIncrementYaw, randIncrementPitch);
					targetPlayer = currentPlayer;
					slowRotationsCount = 0;
					slowRotationIncrement = 0.0F;
					break;
				}
			}
		}
	}

	@Override
	public void onLivingUpdate(EntityPlayerSP player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerClone(EntityPlayerSP player) {
		// TODO Auto-generated method stub
		setEnabled(false);
	}

	@Override
	public void onWorldUnload() {
		// TODO Auto-generated method stub
		setEnabled(false);
	}
	@Override
	public void setEnabled(boolean enabled) {
		if (!enabled) {
			targetPlayer = null;
			slowRotationsCount = 0;
			slowRotationIncrement = 0.0F;
		}
		super.setEnabled(enabled);
	}
	private float updateRotation(float currentRotation, float targetRotation, float maxIncrement)
	{
		float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
		if (deltaAngle > maxIncrement)
		{
			deltaAngle = maxIncrement;
		}
		if (deltaAngle < -maxIncrement)
		{
			deltaAngle = -maxIncrement;
		}
		return currentRotation + deltaAngle;
	}
}

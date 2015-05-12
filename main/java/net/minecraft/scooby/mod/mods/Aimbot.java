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

/**
 * What the fuck do you think it does? Key bind to enable it is the 'I' key.
 * @author pootPoot
 * @since brudin started ignoring my pull request... :'C
 */
public class Aimbot extends Mod {

	private EntityPlayer targetPlayer; // Makes Aimbot focus on one player before switching because kek.
	private Random rand = new Random();
	private int slowRotationCount;
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
		double randXOffset, randYOffset, randZOffset;
		if (player.posX - player.prevPosX != 0.0D || player.posY - player.prevPosY != 0.0D || player.posZ - player.prevPosZ != 0.0D || scooby.mc.thePlayer.motionX != 0.0D || scooby.mc.thePlayer.motionY > 0.0D || (scooby.mc.thePlayer.motionY < 0.0D && !scooby.mc.thePlayer.onGround) || scooby.mc.thePlayer.motionZ != 0.0D) {
			randXOffset = rand.nextDouble() * 0.05D;
			while (randXOffset < 0.01D) {
				randXOffset = rand.nextDouble() * 0.05D;
			}
			double minRandYOffset, maxRandYOffset;
			if (player.posY - player.prevPosY != 0.0D && (scooby.mc.thePlayer.motionY > 0.0D || (scooby.mc.thePlayer.motionY < 0.0D && !scooby.mc.thePlayer.onGround))) {
				minRandYOffset = 0.05D;
				maxRandYOffset = 0.1D;
			}
			else {
				minRandYOffset = 0.01D;
				maxRandYOffset = 0.05D;
			}
			randYOffset = rand.nextDouble() * maxRandYOffset;
			while (randYOffset < minRandYOffset) {
				randYOffset = rand.nextDouble() * maxRandYOffset;
			}
			randZOffset = rand.nextDouble() * 0.05D;
			while (randZOffset < 0.01D) {
				randZOffset = rand.nextDouble() * 0.05D;
			}
		}
		else {
			randXOffset = randYOffset = randZOffset = 0.0D;
		}
		double deltaX = player.posX + (rand.nextBoolean() ? randXOffset : -randXOffset) - scooby.mc.thePlayer.posX, deltaZ = player.posZ + (rand.nextBoolean() ? randZOffset : -randZOffset) - scooby.mc.thePlayer.posZ;
		scooby.mc.thePlayer.rotationYaw = updateRotation(scooby.mc.thePlayer.rotationYaw, (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F, maxIncrementYaw);
		scooby.mc.thePlayer.rotationPitch = updateRotation(scooby.mc.thePlayer.rotationPitch, (float) -(Math.atan2(player.getEntityBoundingBox().minY + player.getEyeHeight() + player.getYOffset() + (rand.nextBoolean() ? randYOffset : -randYOffset) - (scooby.mc.thePlayer.posY + scooby.mc.thePlayer.getEyeHeight()), MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ)) * 180.0D / Math.PI), maxIncrementPitch);
	}
	@Override
	public void onClientTickPost() {
		// TODO Auto-generated method stub
		if (scooby.mc.currentScreen != null) {
			return;
		}
		float reachDistance = scooby.mc.playerController.getBlockReachDistance();
		if (targetPlayer != null && slowRotationCount == 0) {
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
				slowRotationCount = 2;
				slowRotationIncrement = randMaxAngle / slowRotationCount;
			}
			scooby.mc.thePlayer.rotationYaw = prevRotationYaw;
			scooby.mc.thePlayer.rotationPitch = prevRotationPitch;
			if (slowRotationCount > 0) {
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
			slowRotationCount = 0;
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
					facePlayer(targetPlayer, slowRotationCount == 0 ? randIncrementYaw : slowRotationIncrement, slowRotationCount == 0 ? randIncrementPitch : slowRotationIncrement);
					hasFacedPlayer = true;
					if (slowRotationCount > 0) {
						--slowRotationCount;
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
					slowRotationCount = 0;
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
			slowRotationCount = 0;
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

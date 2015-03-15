package net.minecraft.scooby.event;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.Mod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * Basic event manager using the existing events provided by Forge.
 * @author b
 * @since 3:37 PM on 3/15/2015
 */
public class EventManager {

	private Scooby scooby;

	public EventManager(Scooby scooby) {
		this.scooby = scooby;
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
		if (event.entity == scooby.mc.thePlayer) {
			for (Mod mod : scooby.modManager.getMods()) {
				if (mod.isEnabled()) {
					mod.onLivingUpdate((EntityPlayerSP)event.entity);
				}
			}
		}
	}

	@SubscribeEvent
	public void onKeyPressed(InputEvent.KeyInputEvent event) {
		if (!Keyboard.getEventKeyState())
			return;
		int keyCode = Keyboard.getEventKey();
		for (Mod mod : scooby.modManager.getMods()) {
			if (mod.getToggleKey() == keyCode) {
				mod.setEnabled(!mod.isEnabled());
			}
		}
	}
}

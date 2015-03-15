package net.minecraft.scooby.mod;

import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.mod.mods.TriggerBot;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author b
 * @since 3:50 PM on 3/15/2015
 */
public class ModManager {

	private List<Mod> mods = new CopyOnWriteArrayList<Mod>();
	private Scooby scooby;

	public ModManager(Scooby scooby) {
		this.scooby = scooby;
	}

	public void loadMods() {
		registerMod(new TriggerBot(scooby));
	}

	private void registerMod(Mod mod) {
		this.mods.add(mod);
	}

	public List<Mod> getMods() {
		return this.mods;
	}
}

package net.minecraft.scooby.mod;

import net.minecraft.scooby.Scooby;
import net.minecraft.scooby.handlers.Handler;
import net.minecraft.scooby.mod.mods.Sprint;
import net.minecraft.scooby.mod.mods.TriggerBot;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author b
 * @since 3:50 PM on 3/15/2015
 */
public class ModHandler implements Handler {

	/* The list of mods.  This will allow us to loop through the mods to call stuff rather than individually. */
	private List<Mod> mods = new CopyOnWriteArrayList<Mod>();

	/**
	 * 'Registers'/loads the mods.  Basically just loading them into the game by making instances of them.
	 */
	@Override
	public void init(Scooby scooby) {
		registerMod(new TriggerBot(scooby));
		registerMod(new Sprint(scooby));
	}

	/**
	 * Adds the specified Mod instance to the mods list.  Since this will only be used in this class, it's private.
	 * @param mod	The specified Mod to 'register'.
	 */
	private void registerMod(Mod mod) {
		this.mods.add(mod);
	}

	/**
	 * @return	The mod list, rather than having it be public.  Basic getter.
	 */
	public List<Mod> getMods() {
		return this.mods;
	}
}

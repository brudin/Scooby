package net.minecraft.scooby;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.scooby.event.EventManager;
import net.minecraft.scooby.mod.ModManager;

/**
 * Scooby is my take on the recent 'Ghost Client' trend.
 * Ghost clients are generally used to give a player an advantage for things like PVP on Minecraft servers.
 * While I personally think the concept is stupid, I wanted to provide a free and open source alternative since most
 * people are selling their own ghost clients that are all basically the same.
 *
 * You are not to use any of the code provided in this mod for your own monetary gain, nor are you allowed to use
 * any code provided in this mod without given credit and permission.  You are free to use and modify it for personal
 * use, feel free to fork this repo and have fun.
 *
 * tl;dr: don't make money off of this stuff, don't take credit for this
 *
 * @author b
 * @since 3:31 PM on 3/15/2015
 */
@Mod(modid = Scooby.MOD_NAME)
public class Scooby {

	/**
	 * Feel free to change the name, I heard that Forge sends mod names to the server, so I went with
	 * a common mod name. This isn't affiliated with OptiFine in any way.
	 */
	public static final String MOD_NAME = "OptiFine HD C7";

	public Minecraft mc;
	public ModManager modManager;
	public EventManager eventManager;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		this.mc = Minecraft.getMinecraft();
		this.modManager = new ModManager(this);
		this.eventManager = new EventManager(this);

		FMLCommonHandler.instance().bus().register(this.eventManager);
		MinecraftForge.EVENT_BUS.register(this.eventManager);

		this.modManager.loadMods();
	}
}

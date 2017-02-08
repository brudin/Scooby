package net.minecraft.scooby;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.scooby.event.EventHandler;
import net.minecraft.scooby.handlers.Handler;
import net.minecraft.scooby.mode.ModeHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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
	 * a random mod name. I might use the http://modlist.mcf.li/ API to grab a random mod name upon startup.
	 */
	public static final String MOD_NAME = "OpenComputers";
	public final Minecraft mc = Minecraft.getMinecraft();
	private final List<Handler> handlers = new ArrayList<Handler>();
	
	public ModeHandler modeHandler;
	public EventHandler eventHandler;

	public void addHandler(Handler handler) {
		getHandlers().add(handler);
		handler.init(this);
	}

	public List<Handler> getHandlers() {
		return handlers;
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		addHandler(modeHandler = new ModeHandler());
		addHandler(eventHandler = new EventHandler());
		FMLCommonHandler.instance().bus().register(eventHandler);
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}
}

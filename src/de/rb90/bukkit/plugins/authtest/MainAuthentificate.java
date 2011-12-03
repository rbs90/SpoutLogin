package de.rb90.bukkit.plugins.authtest;
import java.util.Vector;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.player.SpoutPlayer;


public class MainAuthentificate extends JavaPlugin{

	public static final Logger log = Logger.getLogger("Minecraft");
	public Vector<SpoutPlayer> guest_players = new Vector<SpoutPlayer>();
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName()+" version "+pdfFile.getVersion()+" is disabled!");
	}

	@Override
	public void onEnable() {
		System.out.println("STARTING!!!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, new PlayerjoinAuthListener(this, this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type.PLAYER_INVENTORY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName()+" "+pdfFile.getVersion() + " enabled!");
	}
	
	

}

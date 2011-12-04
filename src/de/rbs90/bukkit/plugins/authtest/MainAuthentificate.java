package de.rbs90.bukkit.plugins.authtest;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.player.SpoutPlayer;


public class MainAuthentificate extends JavaPlugin{

	public static final Logger log = Logger.getLogger("Minecraft");
	public static Vector<SpoutPlayer> guest_players = new Vector<SpoutPlayer>();
	public static Map<SpoutPlayer, Location> old_locations = new HashMap<SpoutPlayer, Location>();
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName()+" version "+pdfFile.getVersion()+" is disabled!");
	}

	@Override
	public void onEnable() {
		//System.out.println("STARTING!!!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, new PlayerjoinAuthListener(this, this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type., new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, new MonsterAttackListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type.PLAYER_INVENTORY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName()+" "+pdfFile.getVersion() + " enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		boolean success = false;
		
		if (command.getName().equalsIgnoreCase("slogin")){
			success = true;
			new AuthentificationPopup(this, (SpoutPlayer) sender);
		}
		
		return success;
	}

}

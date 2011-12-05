package de.rbs90.bukkit.plugins.spoutlogin;
import java.sql.SQLException;
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

import de.rbs90.bukkit.plugins.spoutlogin.crypting.LoginChecker;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.AuthentificationPopup;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyAttackListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyBlockListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyPlayerListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyScreenListener;


public class MainAuthentificate extends JavaPlugin{

	public static final Logger log = Logger.getLogger("Minecraft");
	public Vector<SpoutPlayer> guest_players = new Vector<SpoutPlayer>();
	public Map<SpoutPlayer, Location> old_locations = new HashMap<SpoutPlayer, Location>();
	
	public LoginChecker loginChecker;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName()+" version "+pdfFile.getVersion()+" is disabled!");
	}

	@Override
	public void onEnable() {
		//System.out.println("STARTING!!!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, new MyBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, new MyBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, new MyBlockListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type., new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, new MyAttackListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type.PLAYER_INVENTORY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new MyScreenListener(this), Event.Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName()+" "+pdfFile.getVersion() + " enabled!");
		
		new Thread(){
		
			public void run() {
				try {
					loginChecker = new LoginChecker();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
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
	
	public void setPlayerAsGuest(SpoutPlayer player){
		guest_players.add(player);
		old_locations.put(player, player.getLocation());
		player.teleport(player.getWorld().getSpawnLocation());
	}
	
	public void setPlayerAsAuthentificated(SpoutPlayer player){
		player.teleport(old_locations.get(player));
		guest_players.remove(player);
	}

}

package de.rbs90.bukkit.plugins.spoutlogin;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.crypting.LoginChecker;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.WelcomePopup;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyAttackListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyBlockListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyInputListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyPlayerListener;
import de.rbs90.bukkit.plugins.spoutlogin.listener.MyScreenListener;


public class SpoutLogin extends JavaPlugin{

	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static SettingsManager settings;
	public static LoginChecker loginChecker;
	
	private static ConcurrentHashMap<String, SLoginPlayer> players = new ConcurrentHashMap<String, SLoginPlayer>();
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        
		//release data of all players:
        for (SLoginPlayer player : players.values()){
        	player.release();
        }
        
        log.info(pdfFile.getName()+" version "+pdfFile.getVersion()+" is disabled!");
	}

	@Override
	public void onEnable() {
		settings = new SettingsManager();
		settings.load();
		////System.out.println("STARTING!!!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, new MyPlayerListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, new MyBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, new MyBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, new MyBlockListener(this), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type., new PlayerPlaceBlockListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, new MyAttackListener(), Event.Priority.Normal, this);
		//pm.registerEvent(Event.Type.PLAYER_INVENTORY, new PlayerActionDenieListener(this), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new MyScreenListener(), Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, new MyInputListener(this), Event.Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName()+" "+pdfFile.getVersion() + " enabled!");
		
		new Thread(){
		
			@Override
			public void run() {
					loginChecker = new LoginChecker();
			};
		}.start();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		boolean success = false;
		if (command.getName().equalsIgnoreCase("slogin")){
			success = true;
			new WelcomePopup(this, (SpoutPlayer) sender);
		}
		
		return success;
	}
	
	public static void addGuestPlayer(SpoutPlayer player){
		SLoginPlayer sLoginPlayer = new SLoginPlayer(player);
		sLoginPlayer.setGuest(true);
		sLoginPlayer.setPopup(new WelcomePopup(null, player)); //TODO
		players.put(player.getName(), sLoginPlayer);
	}
	
	public static SLoginPlayer getSLPlayer(Player player){
		return players.get(player.getName());
	}
	
	public static Boolean isPlayerEntityGuest(int id){ //for AttackListener
		for (SLoginPlayer splayer : players.values()){
			if (splayer.getSpoutPlayer().getEntityId() == id)
				return splayer.isGuest();
		}
		
		return null;
	}
	
	public static void updatePlayer(SLoginPlayer player){
		System.out.println("Updating player...");
		players.remove(player.getSpoutPlayer().getName());
		players.put(player.getSpoutPlayer().getName(), player);
		players.get(player.getSpoutPlayer().getName()).printLoc();
	}

}

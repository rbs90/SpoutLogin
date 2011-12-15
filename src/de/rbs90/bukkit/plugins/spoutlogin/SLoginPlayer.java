package de.rbs90.bukkit.plugins.spoutlogin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;


public class SLoginPlayer{
	private static final boolean debug = true;
	SpoutPlayer player;
	private PlayerInventory inventory_backup;
	private Location location_backup;
	private Boolean guest;
	private float exp_backup;
	private int health_backup;
	private int foodLevel_backup;
	
	public SLoginPlayer(SpoutPlayer player) {
		this.player = player;
	}
	
	public void setGuest(Boolean guest){
		this.guest = guest;
		if (guest == true)
			protect();
		else
			release();
		printLoc();
		SpoutLogin.updatePlayer(this);
	}
	
	public void protect(){
		
		//backup:
		location_backup = player.getLocation();
		inventory_backup = player.getInventory();
		player.setDisplayName("[Guest] " + player.getName());
		exp_backup = player.getExp();
		health_backup = player.getHealth();
		foodLevel_backup = player.getFoodLevel();
		
		//create empty player:
		PlayerInventory playerInv = player.getInventory();
		playerInv.clear();
		playerInv.setHelmet(null);
		playerInv.setChestplate(null);
		playerInv.setLeggings(null);
		playerInv.setBoots(null);
		player.setExp(0);
		player.setHealth(20); //=max
		player.setFoodLevel(20);
		
		Location spawnLocation = player.getWorld().getSpawnLocation();
		spawnLocation.setY(player.getWorld().getHighestBlockYAt(spawnLocation));
		player.teleport(spawnLocation);
	}
	
	public void release(){
		printLoc();
		player.teleport(location_backup);
		PlayerInventory inventory = player.getInventory();
		inventory.setContents(inventory_backup.getContents());
		inventory.setArmorContents(inventory_backup.getArmorContents());
		player.setExp(exp_backup);
		player.setHealth(health_backup);
		player.setFoodLevel(foodLevel_backup);
		player.setDisplayName(player.getName());
	}
	
	private void dbSynchronize(){
		//TODO sync players with database?!
	}
	
	public void setPopup(GenericPopup popup){
		PopupScreen activePopup = player.getMainScreen().getActivePopup();
		if (activePopup != null)
			activePopup.close();
		player.getMainScreen().attachPopupScreen(popup);
	}
	
	public PopupScreen getPopup(){
		return player.getMainScreen().getActivePopup();
	}
	
	
	public SpoutPlayer getSpoutPlayer(){
		return player;
	}
	
	public Boolean isGuest(){
		return guest;
	}

	public static boolean debugActive() {
		return debug;
	}

	public void printLoc() {
		System.out.println("location-print: " + location_backup);
	}
}

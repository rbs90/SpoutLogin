package de.rb90.bukkit.plugins.authtest;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerjoinAuthListener extends PlayerListener{

	Plugin plugin;
	MainAuthentificate main;
	
	public PlayerjoinAuthListener(Plugin plugin, MainAuthentificate main) {
		this.plugin = plugin;
		this.main = main;
		
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		//super.onPlayerJoin(event);
		main.guest_players.add((SpoutPlayer) event.getPlayer());
		main.old_locations.put((SpoutPlayer) event.getPlayer(), event.getPlayer().getLocation());
		event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
		new AuthentificationPopup(plugin, (SpoutPlayer) event.getPlayer());
	}
}

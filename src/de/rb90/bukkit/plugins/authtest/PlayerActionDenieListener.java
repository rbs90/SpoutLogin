package de.rb90.bukkit.plugins.authtest;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerActionDenieListener extends PlayerListener{

	
	MainAuthentificate main;
	
	public PlayerActionDenieListener(MainAuthentificate mainAuthentificate) {
		main = mainAuthentificate;
	}
	
	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
		else
			super.onPlayerPickupItem(event);
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
		else
			super.onPlayerInteract(event);
	}
	
	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
		else
			super.onPlayerInteractEntity(event);
	}
}

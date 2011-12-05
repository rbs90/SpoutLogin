package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.event.player.*;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.AuthentificationPopup;

public class MyPlayerListener extends PlayerListener{

	
	MainAuthentificate main;
	
	public MyPlayerListener(MainAuthentificate mainAuthentificate) {
		main = mainAuthentificate;
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		//super.onPlayerJoin(event);
		main.setPlayerAsGuest((SpoutPlayer) event.getPlayer());
		new AuthentificationPopup(main, (SpoutPlayer) event.getPlayer());
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
	
	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		// TODO Auto-generated method stub
		super.onPlayerCommandPreprocess(event);
	}
}

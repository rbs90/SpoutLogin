package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.SLoginPlayer;
import de.rbs90.bukkit.plugins.spoutlogin.SpoutLogin;

public class MyPlayerListener extends PlayerListener{

	
	SpoutLogin main;
	
	private static final String LOGIN_MSG = "You dont have the permission to do this. Please use /slogin or press 'L' to login or register.";
	
	public MyPlayerListener(SpoutLogin mainAuthentificate) {
		main = mainAuthentificate;
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		SpoutLogin.addGuestPlayer((SpoutPlayer) event.getPlayer());
	}
	
	@Override
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		SLoginPlayer slPlayer = SpoutLogin.getSLPlayer(event.getPlayer());
		
		if (slPlayer.isGuest()){
			if (!event.getMessage().toLowerCase().equals("/slogin")){
				event.getPlayer().sendMessage(LOGIN_MSG);
				event.setMessage(""); //for WorldEdit
				event.setCancelled(true);
			}
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	//DENIE for guests:
	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerBucketFill(PlayerBucketFillEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerFish(PlayerFishEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		denieAccessForGuests(event);
	}
	
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		denieAccessForGuests(event);
	}
	
	@Override
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		denieAccessForGuests(event);
	}
		
	public void denieAccessForGuests(Event event){
		SLoginPlayer slPlayer = SpoutLogin.getSLPlayer(((PlayerEvent) event).getPlayer());
		
		Player player = ((PlayerEvent) event).getPlayer();
		if (slPlayer.isGuest()){
			player.sendMessage(LOGIN_MSG);
			((Cancellable) event).setCancelled(true);
		}
	}
}

package de.rb90.bukkit.plugins.authtest;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlockListener extends BlockListener{
	
	MainAuthentificate main;
	
	
	public PlayerPlaceBlockListener(MainAuthentificate mainAuthentificate) {
		main = mainAuthentificate;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event){ 
		if (main.guest_players.contains(event.getPlayer())){
			event.getPlayer().sendMessage("You dont have the permission to do this. Please use /slogin to login or register.");
			System.out.println("block_event");
			event.setCancelled(true);
		}
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
	}
	
	
	
}

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
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
		else
			super.onBlockBreak(event);
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
		else
			super.onBlockPlace(event);
	}
}
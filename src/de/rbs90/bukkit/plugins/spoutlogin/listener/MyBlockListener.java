package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;

public class MyBlockListener extends BlockListener{
	
	MainAuthentificate main;
	
	
	public MyBlockListener(MainAuthentificate mainAuthentificate) {
		main = mainAuthentificate;
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event){ 
		//System.out.println("block_event_all");
		if (main.guest_players.contains(event.getPlayer())){
			event.getPlayer().sendMessage("You dont have the permission to do this. Please use /slogin to login or register.");
			//System.out.println("block_event");
			//event.setCancelled(true);
		}
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		if (main.guest_players.contains(event.getPlayer()))
			event.setCancelled(true);
	}
	
	
	
}

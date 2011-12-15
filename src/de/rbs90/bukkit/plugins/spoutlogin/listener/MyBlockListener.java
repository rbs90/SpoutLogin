package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.rbs90.bukkit.plugins.spoutlogin.SLoginPlayer;
import de.rbs90.bukkit.plugins.spoutlogin.SpoutLogin;

public class MyBlockListener extends BlockListener{
	
	SpoutLogin main;
	
	
	public MyBlockListener(SpoutLogin mainAuthentificate) {
		main = mainAuthentificate;
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event){ 
		//System.out.println("block_event_all");
		SLoginPlayer slPlayer = SpoutLogin.getSLPlayer(event.getPlayer());
		if (slPlayer.isGuest()){
			event.getPlayer().sendMessage("You dont have the permission to do this. Please use /slogin to login or register.");
			//System.out.println("block_event");
			//event.setCancelled(true);
		}
	}
	
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		SLoginPlayer slPlayer = SpoutLogin.getSLPlayer(event.getPlayer());
		if (slPlayer.isGuest())
			event.setCancelled(true);
	}
	
	
	
}

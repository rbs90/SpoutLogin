package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;

public class MyAttackListener extends EntityListener {

	private final MainAuthentificate main;
	
	public MyAttackListener(MainAuthentificate main) {
		this.main = main;
	}
	
	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		for(SpoutPlayer player : main.guest_players){
			////System.out.println(player.getEntityId() + " ==  " + event.getTarget().getEntityId() + " ?");
			if (player.getEntityId() == event.getTarget().getEntityId())
			{
				event.setCancelled(true);
				return;
			}
		}
	}	
}


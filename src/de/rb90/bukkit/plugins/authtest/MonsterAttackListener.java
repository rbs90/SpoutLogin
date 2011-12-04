package de.rb90.bukkit.plugins.authtest;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MonsterAttackListener extends EntityListener {

	private final MainAuthentificate main;
	
	public MonsterAttackListener(MainAuthentificate main) {
		this.main = main;
	}
	
	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		for(SpoutPlayer player : main.guest_players){
			//System.out.println(player.getEntityId() + " ==  " + event.getTarget().getEntityId() + " ?");
			if (player.getEntityId() == event.getTarget().getEntityId())
			{
				event.setCancelled(true);
				return;
			}
		}
	}	
}


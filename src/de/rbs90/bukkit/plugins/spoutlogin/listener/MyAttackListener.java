package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

import de.rbs90.bukkit.plugins.spoutlogin.SpoutLogin;

public class MyAttackListener extends EntityListener {

	public MyAttackListener() {
	}
	
	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		if (SpoutLogin.isPlayerEntityGuest(event.getTarget().getEntityId()))
			event.setCancelled(true);
	}	
}


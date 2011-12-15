package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.SLoginPlayer;
import de.rbs90.bukkit.plugins.spoutlogin.SpoutLogin;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.WelcomePopup;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.RegisterPopup;

public class MyInputListener extends InputListener{
	
	private final Plugin plugin;

	public MyInputListener(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onKeyPressedEvent(final KeyPressedEvent event) {
		
		final SLoginPlayer slPlayer = SpoutLogin.getSLPlayer(event.getPlayer());
					
		if (event.getKey() == Keyboard.KEY_ESCAPE && slPlayer.isGuest()){
			final SpoutPlayer player = event.getPlayer();
			PopupScreen activePopup = player.getMainScreen().getActivePopup();
			if (activePopup instanceof WelcomePopup || activePopup instanceof RegisterPopup){
				player.closeActiveWindow();  
	            player.getMainScreen().closePopup();
	            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	                public void run() {
	                	slPlayer.setPopup(new WelcomePopup(plugin, player));
	                }
	            });
			}
			
		}
		
		if ((event.getKey() == Keyboard.KEY_L) && slPlayer.isGuest()) //TODO: doesn't work
		{
			event.getPlayer().getMainScreen().attachPopupScreen(new WelcomePopup(plugin, event.getPlayer()));
		}
		
		if ((event.getKey() == Keyboard.KEY_RETURN) && (slPlayer.getPopup() instanceof WelcomePopup)){
			
		}
	}
	
}

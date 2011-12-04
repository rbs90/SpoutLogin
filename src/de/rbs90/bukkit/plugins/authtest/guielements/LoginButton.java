package de.rbs90.bukkit.plugins.authtest.guielements;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.authtest.AuthentificationPopup;
import de.rbs90.bukkit.plugins.authtest.MainAuthentificate;

public class LoginButton extends GenericButton {

	

	private final AuthentificationPopup popup;

	public LoginButton(AuthentificationPopup popup) {
		this.popup = popup;
		
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		SpoutPlayer player = event.getPlayer();
		//System.out.println("Buttonevent");
		if(isLoginOK()){
			player.teleport(MainAuthentificate.old_locations.get(player));
			player.setDisplayName(popup.getUserName()); //TODO: set real name??
			MainAuthentificate.guest_players.remove(player);
			popup.close();
		}
		else 
			label.setVisible(true);
	}
	
	private boolean isLoginOK()
	{
		if (popup.getUserName().equals("rbs90") && popup.getPassword().equals("123"))
			return true;
		else
			return false;
	}
}
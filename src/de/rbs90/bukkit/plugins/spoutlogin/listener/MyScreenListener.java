package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.AuthentificationPopup;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.RegisterPopup;

public class MyScreenListener extends ScreenListener{

	private final MainAuthentificate main;

	public MyScreenListener(MainAuthentificate main) {
		this.main = main;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onButtonClick(ButtonClickEvent event) {
		Button button = event.getButton();
		
		SpoutPlayer player = event.getPlayer();
		PopupScreen activePopup = player.getMainScreen().getActivePopup();
		
		if (button.getText().equals("Login")){
			Boolean loginOK = main.loginChecker.checkLogin(player, (AuthentificationPopup) activePopup);
			if (loginOK)
				main.setPlayerAsAuthentificated(player);
		}
		else if (button.getText().equals("Guest")){
			activePopup.close();
		}
		else if (button.getText().equals("Register")){
			activePopup.close();
			player.getMainScreen().attachPopupScreen(new RegisterPopup(main));
		}
		else if (button.getText().startsWith("Send")){
			RegisterPopup popup = (RegisterPopup) activePopup;
			if (popup.proveData()){
				main.loginChecker.createUser(popup.getUserData());
				activePopup.close();
			}
		}
		
			
		super.onButtonClick(event);
	}
	
	
}

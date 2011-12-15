package de.rbs90.bukkit.plugins.spoutlogin.listener;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.PopupScreen;

import de.rbs90.bukkit.plugins.spoutlogin.SLoginPlayer;
import de.rbs90.bukkit.plugins.spoutlogin.SpoutLogin;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.WelcomePopup;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.RegisterPopup;

public class MyScreenListener extends ScreenListener{

	public MyScreenListener() {
	}
	
	
	@Override
	public void onButtonClick(ButtonClickEvent event) {
		Button button = event.getButton();
		
		SLoginPlayer splayer = SpoutLogin.getSLPlayer(event.getPlayer());
		System.out.println("Clicking user: " + splayer.getSpoutPlayer().getName());
				
		PopupScreen activePopup = splayer.getPopup();
		
		if (button.getText().equals("Login")){
			Boolean loginOK = SpoutLogin.loginChecker.checkLogin(splayer.getSpoutPlayer(), (WelcomePopup) activePopup);
			if (loginOK)
				splayer.setGuest(false);
		}
		else if (button.getText().equals("Guest")){
			activePopup.close();
		}
		else if (button.getText().equals("Register")){
			activePopup.close();
			splayer.setPopup(new RegisterPopup());
		}
		else if (button.getText().startsWith("Send")){
			RegisterPopup popup = (RegisterPopup) activePopup;
			if (popup.proveData()){
				SpoutLogin.loginChecker.createUser(popup.getUserData());
				activePopup.close();
			}
		}
		else if (button.getText().startsWith("Cancel")){
			activePopup.close();
			splayer.setPopup(new WelcomePopup(activePopup.getPlugin(), splayer.getSpoutPlayer()));
		}
		
			
		super.onButtonClick(event);
	}
	
	
}

package de.rbs90.bukkit.plugins.authtest;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rb90.bukkit.plugins.authtest.gui.LoginButton;

public class AuthentificationPopup{
	
    GenericPopup popup;
    GenericTextField userBox;
    GenericTextField passBox;
    GenericButton loginBtn;
    GenericButton cancelBtn;
    GenericLabel statusLbl;
    GenericLabel userLbl;
    GenericLabel infoLbl;
    GenericLabel passLbl;
    GenericLabel welcomeLbl;
    GenericTexture texture;
	Plugin plugin;
    
    
    public AuthentificationPopup(Plugin plugin, SpoutPlayer player) {
		this.plugin = plugin;
		createScreen(player);
	}
    
    
	public void createScreen(SpoutPlayer player)
    {
		System.out.println("Creating screen...");
        SpoutPlayer splayer = player;
        popup = new GenericPopup();
        
        texture = new GenericTexture("http://wiki.getspout.org/skins/common/images/logo.png");
        texture.setWidth(60).setHeight(60);
        texture.setX(30);
        texture.setY(30);
        
        welcomeLbl = new GenericLabel("Welcome to" + player.getServer().getName());
        welcomeLbl.setX(splayer.getMainScreen().getMaxWidth() / 2);
        welcomeLbl.setY(20);
        
        infoLbl = new GenericLabel("This is a test of multiline Labels;)" + "\n and they seem to work:D");
        infoLbl.setX(splayer.getMainScreen().getMaxWidth() / 2);
        infoLbl.setY(20);
        
        userLbl = new GenericLabel("user: ");
        userLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        userLbl.setY(splayer.getMainScreen().getMaxHeight() - 60);
        
        passLbl = new GenericLabel("password: ");
        passLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        passLbl.setY(splayer.getMainScreen().getMaxHeight() - 40);
        
        statusLbl = new GenericLabel("Login incorrect:(");
        statusLbl.setTextColor(new Color(255, 0, 0));
        statusLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        statusLbl.setY(splayer.getMainScreen().getMaxHeight() - 20);
        statusLbl.setVisible(false);
        
        userBox = new GenericTextField();
        userBox.setX(splayer.getMainScreen().getMaxWidth()  - 100);
        userBox.setY(splayer.getMainScreen().getMaxHeight() - 60);
        userBox.setHeight(15).setWidth(80);
        //passBox.setPriority(RenderPriority.Low);
        userBox.setMaximumCharacters(18);
        userBox.setMaximumLines(1);
        
        passBox = new GenericTextField();
        passBox.setX(splayer.getMainScreen().getMaxWidth() - 100);
        passBox.setY(splayer.getMainScreen().getMaxHeight() - 40);
        passBox.setPasswordField(true);
        passBox.setHeight(15).setWidth(80);
        //passBox.setPriority(RenderPriority.Low);
        passBox.setMaximumCharacters(18);
        passBox.setMaximumLines(1);

        loginBtn = new LoginButton(this);
        loginBtn.setText("Login");
        loginBtn.setHeight(15).setWidth(80);
        loginBtn.setX(splayer.getMainScreen().getMaxWidth() - 100);
        loginBtn.setY(splayer.getMainScreen().getMaxHeight() - 20);

        cancelBtn = new GenericButton("cancel");
        cancelBtn.setText("cancel");
        cancelBtn.setHeight(10).setWidth(55);
        cancelBtn.setY(splayer.getMainScreen().getMaxHeight() / 2 + 64 - 30);
        cancelBtn.setX(splayer.getMainScreen().getMaxWidth() / 2 - 18);
        
        popup.attachWidget(plugin, texture);
        popup.attachWidget(plugin, passBox);
        popup.attachWidget(plugin, userBox);
        popup.attachWidget(plugin, userLbl);
        popup.attachWidget(plugin, passLbl);
        popup.attachWidget(plugin, infoLbl);
        popup.attachWidget(plugin, loginBtn);
        popup.attachWidget(plugin, statusLbl);
        //popup.attachWidget(plugin, cancelBtn);
        
        
        splayer.getMainScreen().attachPopupScreen(popup);
    }


	public String getUserName() {
		return userBox.getText();
	}
	
	public String getPassword() {
		return passBox.getText();
	}
	
	public GenericLabel getStatusLabel(){
		return statusLbl;
	}


	public void close() {
		popup.close();
	}
	

}

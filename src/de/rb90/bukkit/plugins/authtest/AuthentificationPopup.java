package de.rb90.bukkit.plugins.authtest;

import javax.swing.Popup;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.player.SpoutPlayer;

public class AuthentificationPopup extends GenericPopup{
	
    GenericPopup popup;
    GenericTextField userBox;
    GenericTextField passBox;
    GenericButton registerBtn;
    GenericButton cancelBtn;
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
        
        GenericTexture texture = new GenericTexture("http://wiki.getspout.org/skins/common/images/logo.png");
        texture.setWidth(60).setHeight(60);
        texture.setX(30);
        texture.setY(30);
        
        
        GenericLabel welcomeLbl = new GenericLabel("Welcome to" + player.getServer().getName());
        welcomeLbl.setX(splayer.getMainScreen().getMaxWidth() / 2);
        welcomeLbl.setY(20);
        
        GenericLabel infoLbl = new GenericLabel("This is a test of multiline Labels;)" + "\n and they seem to work:D");
        infoLbl.setX(splayer.getMainScreen().getMaxWidth() / 2);
        infoLbl.setY(20);
        
        GenericLabel userLbl = new GenericLabel("user: ");
        userLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        userLbl.setY(splayer.getMainScreen().getMaxHeight() - 40);
        
        GenericLabel passLbl = new GenericLabel("password: ");
        passLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        passLbl.setY(splayer.getMainScreen().getMaxHeight() - 20);
        
        userBox = new GenericTextField();
        userBox.setX(splayer.getMainScreen().getMaxWidth()  - 100);
        userBox.setY(splayer.getMainScreen().getMaxHeight() - 40);
        userBox.setHeight(10).setWidth(80);
        //passBox.setPriority(RenderPriority.Low);
        userBox.setMaximumCharacters(18);
        userBox.setMaximumLines(1);

        
        passBox = new GenericTextField();
        passBox.setX(splayer.getMainScreen().getMaxWidth() - 100);
        passBox.setY(splayer.getMainScreen().getMaxHeight() - 20);
        passBox.setPasswordField(true);
        passBox.setHeight(10).setWidth(80);
        //passBox.setPriority(RenderPriority.Low);
        passBox.setMaximumCharacters(18);
        passBox.setMaximumLines(1);

        registerBtn = new GenericButton("register");
        registerBtn.setText("register");
        registerBtn.setHeight(10).setWidth(55);
        registerBtn.setY(splayer.getMainScreen().getMaxHeight() / 2 + 52 - 30);
        registerBtn.setX(splayer.getMainScreen().getMaxWidth() / 2 - 18);

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
        //popup.attachWidget(plugin, registerBtn);
        //popup.attachWidget(plugin, cancelBtn);
        
        
        splayer.getMainScreen().attachPopupScreen(popup);
    }
	
	public void handleClick(Button button, Player player) {
        if (button.getId().equals(registerBtn.getId())) 
        	player.performCommand("register " + passBox.getText());
        if (button.getId().equals(cancelBtn.getId())) 
        	((SpoutPlayer)player).getMainScreen().closePopup();
        if (button.equals(registerBtn)) 
        	player.performCommand("register " + passBox.getText());
        if (button.equals(cancelBtn)) 
        	((SpoutPlayer)player).getMainScreen().closePopup();
        if (button.getText() == "register") 
        	player.performCommand("register " + passBox.getText());
        if (button.getText() == "cancel") 
        	((SpoutPlayer)player).getMainScreen().closePopup();
        System.out.println("Click handled");
    }

}

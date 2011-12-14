package de.rbs90.bukkit.plugins.spoutlogin.guielements;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.player.SpoutPlayer;


public class AuthentificationPopup extends GenericPopup{
	
    GenericTextField userBox;
    GenericTextField passBox;
    GenericButton loginBtn;
    GenericButton regBtn;
    GenericButton guestBtn;
    GenericLabel statusLbl;
    GenericLabel userLbl;
    GenericLabel infoLbl;
    GenericLabel passLbl;
    GenericLabel welcomeLbl;
    GenericLabel opsLbl;
    GenericLabel playerLbl;
    GenericTexture texture;
	Plugin plugin;
    
    
    public AuthentificationPopup(Plugin plugin, SpoutPlayer player) {
		this.plugin = plugin;
		createScreen(player);
		setTransparent(false);
	}
    
    
	public void createScreen(SpoutPlayer player)
    {
		//System.out.println("Creating screen...");
        SpoutPlayer splayer = player;
        
        //System.out.println("res: " + splayer.getMainScreen().getMaxWidth() + "x" + splayer.getMainScreen().getMaxHeight());
        splayer.getMainScreen().getMaxHeight();
        
        texture = new GenericTexture("http://cdn.getspout.org/img/logo/spout_xmas_290x135-2.png");
        texture.setWidth(60).setHeight(60);
        texture.setX(30);
        texture.setY(30);
        
        welcomeLbl = new GenericLabel(ChatColor.GOLD + "Welcome to " + player.getServer().getName());
        welcomeLbl.setX(100);
        welcomeLbl.setY(20);
        welcomeLbl.setWidth(310);
        welcomeLbl.setHeight(10);
        
        infoLbl = new GenericLabel(ChatColor.WHITE + "We are a community on a small bukkit based minecraft server."
        		+ "\n" + "Feel free to join our community and press the \n\"register\"-button."
        		+ "\n" + "With your registrated username and password you would also \nget access to our forum at forum.example.org"
        		+ "\n\n" + ChatColor.GRAY +"This is only a test welcome-text, in the future"
        		+ "\n" + ChatColor.GRAY + "this should be configurable via file."
        		+ "\n" + ChatColor.GREEN + "Thx to: The whole Spout/Spoutcraft team, without them such a" 
        		+ "\n" + ChatColor.GREEN + "plugin won't be possible"
        		+ "\n" + ChatColor.BLUE + "@pro-devs: Is there a possibility to get automated line breaks?");
        
        infoLbl.setX(100);
        infoLbl.setY(30);
        infoLbl.setWidth(310);
        infoLbl.setHeight(130);
        
        playerLbl = new GenericLabel(ChatColor.YELLOW + "Players online: " + Bukkit.getServer().getOnlinePlayers().length);
        playerLbl.setX(10);
        playerLbl.setY(splayer.getMainScreen().getMaxHeight() - 80);
        
        Vector<Player> onlineOps = getOnlineOps();
        String opstext = ChatColor.BLUE + "currently online ops (" + onlineOps.size() + "):" + ChatColor.WHITE;
        for (Player op : onlineOps){
        	opstext += "\n" + op.getName();
        }
        
        opsLbl= new GenericLabel(opstext);
        opsLbl.setX(10);
        opsLbl.setY(splayer.getMainScreen().getMaxHeight() - 70);
        
        
        userLbl = new GenericLabel("user:");
        userLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        userLbl.setY(splayer.getMainScreen().getMaxHeight() - 60);
        
        passLbl = new GenericLabel("password:");
        passLbl.setX(splayer.getMainScreen().getMaxWidth()  - 160);
        passLbl.setY(splayer.getMainScreen().getMaxHeight() - 40);
        
        statusLbl = new GenericLabel("Login incorrect:(");
        statusLbl.setTextColor(new Color(255, 0, 0));
        statusLbl.setX(splayer.getMainScreen().getMaxWidth()  - 240);
        statusLbl.setY(splayer.getMainScreen().getMaxHeight() - 70);
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

        loginBtn = new GenericButton("Login");
        loginBtn.setHeight(15).setWidth(80);
        loginBtn.setX(splayer.getMainScreen().getMaxWidth() - 100);
        loginBtn.setY(splayer.getMainScreen().getMaxHeight() - 20);
        
        regBtn = new GenericButton("Register");
        regBtn.setHeight(15).setWidth(50);
        regBtn.setX(splayer.getMainScreen().getMaxWidth() - 150);
        regBtn.setY(splayer.getMainScreen().getMaxHeight() - 20);
        
        guestBtn = new GenericButton("Guest");
        guestBtn.setHeight(15).setWidth(50);
        guestBtn.setX(splayer.getMainScreen().getMaxWidth() - 200);
        guestBtn.setY(splayer.getMainScreen().getMaxHeight() - 20);
        
        attachWidget(plugin, texture);
        attachWidget(plugin, passBox);
        attachWidget(plugin, userBox);
        attachWidget(plugin, userLbl);
        attachWidget(plugin, passLbl);
        attachWidget(plugin, infoLbl);
        attachWidget(plugin, loginBtn);
        attachWidget(plugin, statusLbl);
        attachWidget(plugin, playerLbl);
        attachWidget(plugin, welcomeLbl);
        attachWidget(plugin, opsLbl);
        attachWidget(plugin, regBtn);
        attachWidget(plugin, guestBtn);
        
        
        splayer.getMainScreen().attachPopupScreen(this);
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
	
	private Vector<Player> getOnlineOps(){
		Vector<Player> onlineOps = new Vector<Player>();
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		for (Player player : onlinePlayers)
			if (player.isOp())
				onlineOps.add(player);
		
		return onlineOps;
	}

}

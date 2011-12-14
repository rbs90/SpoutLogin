package de.rbs90.bukkit.plugins.spoutlogin.crypting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.ConfigurationSection;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.MainAuthentificate;
import de.rbs90.bukkit.plugins.spoutlogin.guielements.AuthentificationPopup;

public class LoginChecker {
	
	private Statement statement;
	private ConfigurationSection config;
	

	public LoginChecker(){
		//init SQL Connection...

		try {
			Class.forName("com.mysql.jdbc.Driver");
			config = MainAuthentificate.settings.getDatabaseSettings();
			Connection connection = DriverManager.getConnection("jdbc:" + config.getString("address") + ":" + config.getString("port") + "/" + config.getString("table"), config.getString("user"), config.getString("pass"));
			statement = connection.createStatement();
			
			//check if table exists:
			statement.execute("CREATE TABLE IF NOT EXISTS `users` ("
					 + "`id` int(10) NOT NULL auto_increment,"
					 + "`name` text NOT NULL,"
					 + "`registredsince` timestamp NOT NULL default CURRENT_TIMESTAMP,"
					 + "`email` text NOT NULL,"
					 + "`password` text NOT NULL,"
					 + "`approved` tinyint(1) NOT NULL default '0',"
					 + "PRIMARY KEY  (`id`)"
					 + ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public Boolean checkLogin(SpoutPlayer player, AuthentificationPopup popup){
		try {
			if(isLoginOK(popup.getUserName(), popup.getPassword())){
				player.setDisplayName(popup.getUserName()); //TODO: set real name??
				popup.close();
				return true;
			}
			else{
				popup.getStatusLabel().setVisible(true);
				popup.getStatusLabel().setText("Login incorrect. Try again.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			popup.getStatusLabel().setText("Something went wrong:( SQL Server down?");
			popup.getStatusLabel().setVisible(true);
			e.printStackTrace();
		}
		return false;
		 
	}
	
	private boolean isLoginOK(String user, String pass)
	{
		ResultSet rs;
		try {
			rs = statement.executeQuery("SELECT password FROM users WHERE name='"+ user +"'");
			while (rs.next()){
				if (BCrypt.checkpw(pass, rs.getString("password")))
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		return false;
	}
	
	public boolean isNameAvailable(String user){
		try {
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users WHERE name='"+ user +"'");
			rs.next();
			if (rs.getInt("COUNT(*)") == 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	public void createUser(UserData data) {
		
		String pwHash = BCrypt.hashpw(data.getPass(), BCrypt.gensalt());
		int approvedInt = data.getApproved() ? 1 : 0;
		
		try {
			String sql_state = "INSERT INTO users (name, email, password, approved) VALUES ('" + data.getName() + "', '" + data.getEmail() + "', '" + pwHash + "', '" 
					+ approvedInt + "')";
			//System.out.println("SQL STATEMENT: \n" + sql_state);
			statement.execute(sql_state);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Created user");
		
	}
	
}

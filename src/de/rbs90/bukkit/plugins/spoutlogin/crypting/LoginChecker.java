package de.rbs90.bukkit.plugins.spoutlogin.crypting;

import java.sql.*;

import org.getspout.spoutapi.player.SpoutPlayer;

import de.rbs90.bukkit.plugins.spoutlogin.guielements.AuthentificationPopup;

public class LoginChecker {
	
	private Statement statement;

	public LoginChecker() throws SQLException, ClassNotFoundException {
		//init SQL Connection...

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://91.226.88.173:3306/mcpass", "mcpass", "DqGJwEaEUYK783sR");
		statement = connection.createStatement();
		
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
	
	private boolean isLoginOK(String user, String pass) throws ClassNotFoundException, SQLException
	{
		ResultSet rs = statement.executeQuery("SELECT password FROM users WHERE name='"+ user +"'");
		
		while (rs.next()){
			if (BCrypt.checkpw(pass, rs.getString("password")))
				return true;
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
			System.out.println("SQL STATEMENT: \n" + sql_state);
			statement.execute(sql_state);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created user");
		
	}
	
}

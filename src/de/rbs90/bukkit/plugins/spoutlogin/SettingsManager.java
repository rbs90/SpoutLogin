package de.rbs90.bukkit.plugins.spoutlogin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {

	public static String mainDir = "plugins" + File.separator + "SpoutLogin";
	private File settingsFile = new File(mainDir + File.separator + "properties.yml");
	private FileConfiguration config = new YamlConfiguration();
	private ConfigurationSection database;
	private ConfigurationSection serverPass;
	private ConfigurationSection registration;
	private ConfigurationSection auth;
	
	
	public SettingsManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void load(){
			if (!settingsFile.exists()){
				new File(mainDir).mkdirs();
				try {
					settingsFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createDefaultSettingsFile();
			}
			else{
				try {
					config.load(settingsFile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			database = config.getConfigurationSection("database");
			serverPass = config.getConfigurationSection("server-password");
			registration = config.getConfigurationSection("registration");

	}
	
	public void save(){
		try {
			config.save(settingsFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ConfigurationSection getDatabaseSettings(){
		return database;
	}
	
	public ConfigurationSection getServerPassSettings(){
		return serverPass;
	}
	
	public ConfigurationSection getRegistrationSettings(){
		return registration;
	}
	
	public void createDefaultSettingsFile(){
		config.createSection("auth_variant");
		auth = config.getConfigurationSection("auth_variant");
		auth.set("variant", "database");
		
		config.createSection("database");
		database = config.getConfigurationSection("database");
		database.set("address","mysql://www.example.org");
		database.set("port","3306");
		database.set("table","slogin");
		database.set("user","slogin");
		database.set("pass","yourpasswordhere");
		
		config.createSection("server-password");
		serverPass = config.getConfigurationSection("server-password");
		serverPass.set("enabled", false);
		serverPass.set("password", "yourPasswordhere");
		
		config.createSection("registration");
		registration = config.getConfigurationSection("registration");
		registration.set("email", "not yet implemented.");
		
		config.createSection("phpBB");
		registration = config.getConfigurationSection("registration");
		registration.set("email", "not yet implemented.");
		
		
		
		save();
	}
	
	
	
}

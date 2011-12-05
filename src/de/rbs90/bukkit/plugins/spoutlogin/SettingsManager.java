package de.rbs90.bukkit.plugins.spoutlogin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {

	public static String mainDir = "plugin" + File.separator + "SpoutLogin";
	private File settingsFile = new File(mainDir + File.separator + "properties.yml");
	private FileConfiguration config = new YamlConfiguration();
	
	public SettingsManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void load(){
		try {
			if (!settingsFile.exists())
			{
				settingsFile.mkdirs();
				settingsFile.createNewFile();
			}
			getConfig().load(settingsFile);
		} catch (IOException
				| InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(){
		try {
			getConfig().save(settingsFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}
	
	
	
}

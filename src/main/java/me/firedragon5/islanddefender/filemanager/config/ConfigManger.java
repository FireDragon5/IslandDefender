package me.firedragon5.islanddefender.filemanager.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManger {

	static ConfigManger instance = new ConfigManger();


	File configFile;

	FileConfiguration config;

	public static ConfigManger getFileManager() {
		return instance;
	}


	public void setup() {

		configFile = new File("plugins/IslandDefender/config.yml");

		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}


	public FileConfiguration getConfig() {
		return config;
	}


	//	Config check
	public void loadConfigFile() {
		config.addDefault("staffChatFormat", "&8[&4Staff&8] &7%player%&8: &7%message%");
//		chat format
		config.addDefault("chatFormat", "&7[&b%clan%&7] &f%player%&7: &f%message%");
		config.options().copyDefaults(true);
		saveConfig();

	}

	//	Check if the config file has all the required values
	public void checkConfig() {
		if (!config.contains("staffChatFormat")) {
			config.addDefault("staffChatFormat", "&8[&4Staff&8] &7%player%&8: &7%message%");
			saveConfig();
		}
		if (!config.contains("chatFormat")) {
			config.addDefault("chatFormat", "&7[&b%clan%&7] &f%player%&7: &f%message%");
			saveConfig();
		}
	}


	//	Get the staff chat format
	public String getStaffChatFormat() {
		return config.getString("staffChatFormat");
	}

	//	Get the chat format
	public String getChatFormat() {
		return config.getString("chatFormat");
	}


}

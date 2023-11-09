package me.firedragon5.islanddefender.filemanager.config;

import me.firedragon5.islanddefender.IslandDefender;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

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

		checkConfig();

		config.options().copyDefaults(true);
		saveConfig();

	}

	//	Check if the config file has all the required values
	public void checkConfig() {

		if (!config.contains("chatFormat")) {
			config.addDefault("chatFormat", "&8[&4%staffRank%&8] &7[&b%rank%&7] &7[&b%clan%&7] &f%player%&7: &f%message%");
		}

//		Max coin pay amount
		if (!config.contains("max-coin-pay-amount")) {
			config.addDefault("max-coin-pay-amount", 1000000);
		}

		if (!config.contains("staffChatFormat")) {
			config.addDefault("staffChatFormat", "&8[&4Staff&8] &7%player%&8: &7%message%");
		}

//		Admin chat format
		if (!config.contains("adminChatFormat")) {
			config.addDefault("adminChatFormat", "&8[&4Admin&8] &7%player%&8: &7%message%");
		}

//		When a player hovers over players names in chat this will show
		if (!config.contains("chatHover")) {
			config.addDefault("chatHover", List.of("&7Rank: &b%rank%", "&7Clan: &b%clan%"));
		}

//		Header for the tablist
		if (!config.contains("tablistHeader")) {
			config.addDefault("tablistHeader", "&8&m----------------------");
		}

//		Footer for the tablist
		if (!config.contains("tablistFooter")) {
			config.addDefault("tablistFooter", "&8&m----------------------");
		}

//		HubSpawn location
		if (!config.contains("hubSpawn")) {
			config.addDefault("hubSpawn", "none");
		}


		saveConfig();


	}

	//	Get the max coin pay amount
	public int getMaxCoinPayAmount() {
		return config.getInt("max-coin-pay-amount");
	}


	//	Get the staff chat format
	public String getStaffChatFormat() {
		return config.getString("staffChatFormat");
	}

	//	Get the chat format
	public String getChatFormat() {
		return config.getString("chatFormat");
	}

	//	Get the chat format
	public String getChatFormatNoClan() {
		return config.getString("chatFormatNoClan");
	}

	//	Get the admin chat format
	public String getAdminChatFormat() {
		return config.getString("adminChatFormat");
	}

	//	Get the chat hover
	public List<String> getChatHover() {
		return config.getStringList("chatHover");
	}

	//	get the staff format
	public String getStaffFormat() {
		return config.getString("staffFormat");
	}


	public boolean isChatMuted() {

		return IslandDefender.isChatMuted;

	}

	public void setChatMuted(boolean b) {
		IslandDefender.isChatMuted = b;

	}

	public String getTablistHeader() {
		return config.getString("tablistHeader");
	}

	public String getTablistFooter() {
		return config.getString("tablistFooter");
	}

	public void setHubWorld(Location location) {
		config.set("hubSpawn", location);
		saveConfig();
	}

	public Location getHubWorld() {
		return (Location) config.get("hubSpawn");
	}
}

package me.firedragon5.clashcraft.filemanager.player;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerFileManager {

//	Folder structure
//	plugins/ClashCraft/players/<playername>.yml



// Add the player to the folder
	public static void addPlayer(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

//		Check if the folder exists
		File playerFolder = new File("plugins/ClashCraft/players");

		if (!playerFolder.exists()) {
			playerFolder.mkdirs();
		}


//		Check if the file exists
		File playerFile = new File("plugins/ClashCraft/players/" + playerUUID + ".yml");

		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();

//				Add default values
				FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

//				clan name
				playerConfig.set("clan-name", "none");
//				rank
				playerConfig.set("rank", "none");

				playerConfig.save(playerFile);
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);



			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

//	Get player clan name
	public static String getPlayerClanName(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/ClashCraft/players/" + playerUUID + ".yml");


		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("clan-name");
		}

		return null;

	}

//	Get player rank
	public static String getPlayerRank(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();


		File playerFile = new File("plugins/ClashCraft/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("rank");
		}

		return null;
	}

//	Set player clan name
	public static void setPlayerClanName(Player playerName, String clanName) {


		UUID playerUUID = playerName.getUniqueId();


		File playerFile = new File("plugins/ClashCraft/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("clan-name", clanName);
		}
	}

//	Set player rank
	public static void setPlayerRank(Player playerName, String rank) {

//		get the player uuid
		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/ClashCraft/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("rank", rank);
		}
	}



}

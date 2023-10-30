package me.firedragon5.islanddefender.filemanager.player;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerFileManager {

//	Folder structure
//	plugins/islanddefender/players/<playername>.yml


	// Add the player to the folder
	public static void addPlayer(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

//		Check if the folder exists
		File playerFolder = new File("plugins/islanddefender/players");

		if (!playerFolder.exists()) {
			playerFolder.mkdirs();
		}


//		Check if the file exists
		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();

//				Add default values
				FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

//				clan name
				playerConfig.set("clan-name", "none");
//				rank
				playerConfig.set("rank", "Default");
//				coins
				playerConfig.set("coins", 0);
//				crystals
				playerConfig.set("crystals", 0);
//				mana
				playerConfig.set("mana", 0);
//				mine
				playerConfig.set("mine", "Default");

//				Friends
				playerConfig.set("friends", new String[]{});

//				Friends ignore
				playerConfig.set("friends-ignore", new String[]{});


				playerConfig.save(playerFile);
				playerConfig.options().copyDefaults(true);
				playerConfig.save(playerFile);


			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	make a method that checks if the players has all the correct stuff in their yml file
	public static void checkPlayer(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			if (playerConfig.getString("clan-name") == null) {
				playerConfig.set("clan-name", "none");
			}
			if (playerConfig.getString("rank") == null) {
				playerConfig.set("rank", "Default");
			}

//			Coins
			if (playerConfig.getString("coins") == null) {
				playerConfig.set("coins", 0);
			}

//			Crystals
			if (playerConfig.getString("crystals") == null) {
				playerConfig.set("crystals", 0);
			}

//			Mana
			if (playerConfig.getString("mana") == null) {
				playerConfig.set("mana", 0);
			}

//			mine
			if (playerConfig.getString("mine") == null) {
				playerConfig.set("mine", "Default");
			}

//			Friends
			if (playerConfig.getStringList("friends") == null) {
				playerConfig.set("friends", new String[]{});
			}

//			Friends ignore
			if (playerConfig.getStringList("friends-ignore") == null) {
				playerConfig.set("friends-ignore", new String[]{});
			}


			try {
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

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("clan-name");
		}

//		Return null if the player file does not exist
		return null;

	}

	//	Get mine name
	public static String getPlayerMine(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("mine");
		}

		return null;
	}

	//	Set mine name
	public static void setPlayerMine(Player playerName, String mineName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("mine", mineName);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Get player friends
	//friends:
	//- 56e7b2dc-d6af-46cf-a401-43d53f77f61e

	public static List<String> getPlayerFriends(Player playerName) {
		UUID playerUUID = playerName.getUniqueId();
		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
			return playerConfig.getStringList("friends");
		}

		return new ArrayList<>(); // Return an empty list if the file doesn't exist or if there are no friends.
	}


	//	Add player friends
	public static void addPlayerFriends(Player playerName, Player friendName) {
		UUID playerUUID = playerName.getUniqueId();
		UUID friendUUID = friendName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");
		File friendFile = new File("plugins/islanddefender/players/" + friendUUID + ".yml");

		if (playerFile.exists() && friendFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			// Check if the friend request exists in the pending requests
			if (IslandDefender.pendingFriendRequests.containsKey(friendName) &&
					IslandDefender.pendingFriendRequests.get(friendName).equals(playerName)) {

				String[] friends = playerConfig.getStringList("friends").toArray(new String[0]);

				// Check if the friend is not already in the friends list
				if (!Arrays.asList(friends).contains(friendUUID.toString())) {
					String[] newFriends = Arrays.copyOf(friends, friends.length + 1);
					newFriends[newFriends.length - 1] = friendUUID.toString();

					playerConfig.set("friends", Arrays.asList(newFriends));

					try {
						playerConfig.save(playerFile);
					} catch (Exception e) {
						e.printStackTrace();
					}

					UtilsMessage.sendMessage(playerName, "&aYou have added &e" + friendName.getName() +
							" &ato your friends list!");

					// Remove the friend request from the pending requests
					IslandDefender.pendingFriendRequests.remove(friendName);
				} else {
					UtilsMessage.errorMessage(playerName, "You are already friends with this player.");
				}
			} else {
				UtilsMessage.errorMessage(playerName, "The friend request was not accepted.");
			}
		} else {
			UtilsMessage.errorMessage(playerName, "The player does not exist!");
		}
	}


	//	Remove player friends
	public static void removePlayerFriends(Player playerName, Player friendName) {
		UUID playerUUID = playerName.getUniqueId();
		UUID friendUUID = friendName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");
		File friendFile = new File("plugins/islanddefender/players/" + friendUUID + ".yml");

		if (playerFile.exists() && friendFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			List<String> friends = playerConfig.getStringList("friends");

			if (friends.isEmpty()) {
				UtilsMessage.errorMessage(playerName, "Your friends list is empty.");
				return;
			}

			if (friends.contains(friendUUID.toString())) {
				friends.remove(friendUUID.toString());
				playerConfig.set("friends", friends);

				try {
					playerConfig.save(playerFile);
					UtilsMessage.sendMessage(playerName, "&aYou have removed &e" + friendName.getName() + " &afrom your friends list!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				UtilsMessage.errorMessage(playerName, "This player is not in your friends list.");
			}
		}
	}

	//	Add player friends ignore
	public static void addPlayerFriendsIgnore(Player playerName, Player friendName) {

		UUID playerUUID = playerName.getUniqueId();

		UUID friendUUID = friendName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		File friendFile = new File("plugins/islanddefender/players/" + friendUUID + ".yml");

		if (playerFile.exists() && friendFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			String[] friends = playerConfig.getStringList("friends-ignore").toArray(new String[0]);

			String[] newFriends = new String[friends.length + 1];

			System.arraycopy(friends, 0, newFriends, 0, friends.length);

			newFriends[newFriends.length - 1] = String.valueOf(friendUUID);

			playerConfig.set("friends-ignore", newFriends);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//	Remove player friends ignore
	public static void removePlayerFriendsIgnore(Player playerName, Player friendName) {

		UUID playerUUID = playerName.getUniqueId();

		UUID friendUUID = friendName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		File friendFile = new File("plugins/islanddefender/players/" + friendUUID + ".yml");

		if (playerFile.exists() && friendFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			String[] friends = playerConfig.getStringList("friends-ignore").toArray(new String[0]);

			String[] newFriends = new String[friends.length - 1];

			int index = 0;

			for (String friend : friends) {
				if (!friend.equals(String.valueOf(friendUUID))) {
					newFriends[index] = friend;
					index++;
				}
			}

			playerConfig.set("friends-ignore", newFriends);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

	//	Get player friends ignore
	public static String[] getPlayerFriendsIgnore(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getStringList("friends-ignore").toArray(new String[0]);
		}

		return null;
	}

	//	Get player rank
	public static String getPlayerRank(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getString("rank");
		}

		return null;
	}

	//	Set player clan name
	public static void setPlayerClanName(Player playerName, String clanName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("clan-name", clanName);


			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			addPlayer(playerName);
			setPlayerClanName(playerName, clanName);
		}
	}

	//	Set player rank
	public static void setPlayerRank(Player playerName, String rank) {

//		get the player uuid
		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("rank", rank);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

	//	Get player coins
	public static int getPlayerCoins(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("coins");
		}

		return 0;
	}

	//	Set player coins
	public static void setPlayerCoins(Player playerName, int coins) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("coins", coins);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Get player crystals
	public static int getPlayerCrystals(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("crystals");
		}

		return 0;
	}

	//	Set player crystals
	public static void setPlayerCrystals(Player playerName, int crystals) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("crystals", crystals);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Get player mana
	public static int getPlayerMana(Player playerName) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			return playerConfig.getInt("mana");
		}

		return 0;
	}

	//	Set player mana
	public static void setPlayerMana(Player playerName, int mana) {

		UUID playerUUID = playerName.getUniqueId();

		File playerFile = new File("plugins/islanddefender/players/" + playerUUID + ".yml");

		if (playerFile.exists()) {
			FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

			playerConfig.set("mana", mana);

			try {
				playerConfig.save(playerFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	//	Remove Coins
	public static void removePlayerCoins(Player playerName, int coins) {
		int currentCoins = getPlayerCoins(playerName);
		int newCoins = currentCoins - coins;
		setPlayerCoins(playerName, newCoins);
	}

	//	Add Coins
	public static void addPlayerCoins(Player playerName, int coins) {
		int currentCoins = getPlayerCoins(playerName);
		int newCoins = currentCoins + coins;
		setPlayerCoins(playerName, newCoins);
	}

	//	Remove Crystals
	public static void removePlayerCrystals(Player playerName, int crystals) {
		int currentCrystals = getPlayerCrystals(playerName);
		int newCrystals = currentCrystals - crystals;
		setPlayerCrystals(playerName, newCrystals);
	}

	//	Add Crystals
	public static void addPlayerCrystals(Player playerName, int crystals) {
		int currentCrystals = getPlayerCrystals(playerName);
		int newCrystals = currentCrystals + crystals;
		setPlayerCrystals(playerName, newCrystals);
	}

	//	Remove Mana
	public static void removePlayerMana(Player playerName, int mana) {
		int currentMana = getPlayerMana(playerName);
		int newMana = currentMana - mana;
		setPlayerMana(playerName, newMana);
	}

	//	Add Mana
	public static void addPlayerMana(Player playerName, int mana) {
		int currentMana = getPlayerMana(playerName);
		int newMana = currentMana + mana;
		setPlayerMana(playerName, newMana);
	}


	public static boolean isFriend(Player player, Player targetPlayer) {

		List<String> friends = getPlayerFriends(player);

		UUID friendUUID = targetPlayer.getUniqueId();


		for (String friend : friends) {
			if (friend.equals(String.valueOf(friendUUID))) {
				return true;
			}
		}


		return false;

	}

	public static boolean isIgnoring(Player targetPlayer, Player player) {

		String[] friendsIgnore = getPlayerFriendsIgnore(targetPlayer);

		UUID friendUUID = targetPlayer.getUniqueId();

		if (friendsIgnore != null) {
			for (String friend : friendsIgnore) {
				if (friend.equals(String.valueOf(friendUUID))) {
					return true;
				}
			}
		}

		return false;

	}
}

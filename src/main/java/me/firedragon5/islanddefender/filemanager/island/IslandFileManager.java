package me.firedragon5.islanddefender.filemanager.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;


public class IslandFileManager {


	private static int x;
	private static int y;
	private static int z;
	private static final int yaw = 0;
	private static final int pitch = 0;
	private static final String worldName = "islandWorld";


	public static void addIsland(Player player) {
		File islandFolder = new File("plugins/islanddefender/islands");

		if (!islandFolder.exists()) {
			islandFolder.mkdirs();
		}

		UUID playerUUID = player.getUniqueId();

		File islandFile = new File("plugins/islanddefender/islands/" + playerUUID + ".yml");

		if (!islandFile.exists()) {
			try {
				islandFile.createNewFile();
				setPlayerIslandLocation(player);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private static final File islandsFolder = new File("plugins/islanddefender/islands");

	public static boolean hasIsland(Player player) {
		UUID playerUUID = player.getUniqueId();
		File islandFile = new File(islandsFolder, playerUUID + ".yml");
		return islandFile.exists();
	}


	public static Location getIslandLocation(Player player) {
		UUID playerUUID = player.getUniqueId();
		File islandFile = new File(islandsFolder, playerUUID + ".yml");

		if (islandFile.exists()) {
			FileConfiguration islandConfig = YamlConfiguration.loadConfiguration(islandFile);

			double x = islandConfig.getDouble("location.x");
			double y = islandConfig.getDouble("location.y");
			double z = islandConfig.getDouble("location.z");
			float yaw = (float) islandConfig.getDouble("location.yaw");
			float pitch = (float) islandConfig.getDouble("location.pitch");
			String worldName = islandConfig.getString("location.world");

			return new Location(player.getServer().getWorld(worldName), x, y, z, yaw, pitch);
		} else {
			return null;
		}
	}

	//	16515858 1 1643771
	public static void setPlayerIslandLocation(Player player) {
		World islandWorld = player.getServer().getWorld("islandWorld");
		WorldBorder worldBorder = islandWorld.getWorldBorder();

		// Generate a random spawn location at least 25 chunks away from other players
		Location newSpawnLocation = generateRandomLocation(islandWorld, worldBorder);

		// Set the player's spawn location
		player.teleport(newSpawnLocation);
		player.setBedSpawnLocation(newSpawnLocation, true);

		// Save the spawn location to the player's YML file
		saveIslandLocation(player, newSpawnLocation);
	}

	private static Location generateRandomLocation(World world, WorldBorder worldBorder) {
		Random random = new Random();

		// Define the border size (25 chunks away)
		int borderSizeChunks = 25;
		int chunkSize = 16; // Chunk size (typically 16 blocks)

		// Calculate the minimum and maximum chunk coordinates based on the border size
		int minX = -borderSizeChunks;
		int maxX = borderSizeChunks;
		int minZ = -borderSizeChunks;
		int maxZ = borderSizeChunks;

		// Generate random chunk coordinates within the specified range
		int chunkX = random.nextInt(maxX - minX + 1) + minX;
		int chunkZ = random.nextInt(maxZ - minZ + 1) + minZ;

		// Calculate the middle of the selected chunk
		int x = (chunkX * chunkSize) + (chunkSize / 2);
		int z = (chunkZ * chunkSize) + (chunkSize / 2);

		// Create a Location object at the center of the chunk
		Location newSpawnLocation = new Location(world, x, 0, z);

		// Check that this location is at least 25 chunks away from other players
		while (isLocationTooCloseToOthers(newSpawnLocation)) {
			// Recalculate the location if it's too close to other players
			chunkX = random.nextInt(maxX - minX + 1) + minX;
			chunkZ = random.nextInt(maxZ - minZ + 1) + minZ;
			x = (chunkX * chunkSize) + (chunkSize / 2);
			z = (chunkZ * chunkSize) + (chunkSize / 2);
			newSpawnLocation = new Location(world, x, 0, z);
		}

		setX(x);
		setZ(z);
		setY(0);

		return newSpawnLocation;
	}


	private static boolean isLocationTooCloseToOthers(Location location) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer != null && onlinePlayer.getWorld() == location.getWorld()) {
				Location playerLocation = onlinePlayer.getLocation();
				double distance = location.distance(playerLocation);

				// Check if the distance in chunks is less than the minimum
				if (distance < (25 * 16)) {
					return true; // Too close to another player
				}
			}
		}

		return false; // Not too close to any player
	}

	public static void saveIslandLocation(Player player, Location location) {
		UUID playerUUID = player.getUniqueId();
		File islandFile = new File(islandsFolder, playerUUID + ".yml");
		FileConfiguration islandConfig = YamlConfiguration.loadConfiguration(islandFile);

		islandConfig.set("location.x", getX());
		islandConfig.set("location.y", getY());
		islandConfig.set("location.z", getZ());
		islandConfig.set("location.yaw", location.getYaw());
		islandConfig.set("location.pitch", location.getPitch());
		islandConfig.set("location.world", "islandWorld");

		try {
			islandConfig.save(islandFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//	Teleport the player to their island
	public static void teleportPlayerToIsland(Player player) {
		Location islandLocation = getIslandLocation(player);

		if (islandLocation != null) {
			player.teleport(islandLocation);
		} else {
			setPlayerIslandLocation(player);
		}
	}


	//	getters and setter
	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static int getZ() {
		return z;
	}

	public static int getYaw() {
		return yaw;
	}

	public static int getPitch() {
		return pitch;
	}

	public static String getWorldName() {
		return worldName;
	}

	//	Set
	public static void setX(int x) {
		IslandFileManager.x = x;
	}

	public static void setY(int y) {
		IslandFileManager.y = y;
	}

	public static void setZ(int z) {
		IslandFileManager.z = z;
	}


}

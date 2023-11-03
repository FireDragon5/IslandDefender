package me.firedragon5.islanddefender.manager;

import me.firedragon5.islanddefender.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.OptionalInt;

public class NameTagManager {

	// Set the name tag based on the player's rank
	public static void setNameTags(Player player) {
		LuckPerms luckPerms = LuckPermsProvider.get();
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();

		// Ensure the prefix is not null
		if (prefix == null) {
			prefix = "";
		}

		// Get or create a team with the player's prefix
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(prefix);
		if (team == null) {
			team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(prefix);
		}

		// Set the prefix for the team
		team.setPrefix(Objects.requireNonNull(Utils.chat(prefix)));

		// Add the player to the team
		team.addEntry(player.getName());
	}

	// Add the player to the teams of all online players
// Add the player to the teams of all online players, sorted by group weight
	public static void newTag(Player player) {
		LuckPerms luckPerms = LuckPermsProvider.get();

		// Get the player's rank and its weight
		String playerRank = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		@NonNull OptionalInt playerWeight = luckPerms.getGroupManager().getGroup(playerRank).getWeight();

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			String onlinePlayerRank = Objects.requireNonNull(luckPerms.getUserManager().getUser(onlinePlayer.getUniqueId())).getPrimaryGroup();
			@NonNull OptionalInt onlinePlayerWeight = luckPerms.getGroupManager().getGroup(onlinePlayerRank).getWeight();

			if (playerWeight.orElse(0) < onlinePlayerWeight.orElse(0)) {
				Team playerTeam = onlinePlayer.getScoreboard().getTeam(player.getName());
				if (playerTeam != null) {
					playerTeam.addEntry(player.getName());
				}
			} else {
				Team onlinePlayerTeam = player.getScoreboard().getTeam(onlinePlayer.getName());
				if (onlinePlayerTeam != null) {
					onlinePlayerTeam.addEntry(onlinePlayer.getName());
				}
			}
		}
	}

	// Remove the player from all scoreboards
	public static void removeTag(Player player) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (onlinePlayer.getScoreboard().getTeam(player.getName()) != null) {
				onlinePlayer.getScoreboard().getEntityTeam(player).removeEntry(player.getName());
			}
		}
	}
}

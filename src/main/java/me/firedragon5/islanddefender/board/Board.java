package me.firedragon5.islanddefender.board;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class Board implements Runnable{

	private final static Board instance = new Board();

	@Override
	public void run() {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getScoreboard().getObjective(IslandDefender.getInstance().getName()) == null) {
				createNewScoreboard(player);
			} else {
				updateScoreboard(player);
			}
		}

	}


	private void createNewScoreboard(Player player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective(IslandDefender.getInstance().getName(), "dummy");

		LuckPerms luckPerms = LuckPermsProvider.get();

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(Objects.requireNonNull(Utils.chat("&6&lIsland Defender")));

//		Name / Rank (primary group)
//		Coins:
//		Crystal:
//		Dark Crystal:
//		----------------

//		get the primary group of the player prefix
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();


		objective.getScore(Objects.requireNonNull(Utils.chat("&7&m-----------------------"))).setScore(8);
		objective.getScore(Objects.requireNonNull(Utils.chat(" "))).setScore(7);
		objective.getScore(Objects.requireNonNull(Utils.chat("&6&l" + player.getName() +
				" | " + prefix))).setScore(6);
		objective.getScore(Objects.requireNonNull(Utils.chat("  "))).setScore(5);

//		coins
		objective.getScore(Objects.requireNonNull(Utils.chat("&eCoins: " + PlayerFileManager.getPlayerCoins(player)))).setScore(4);
//		crystal
		objective.getScore(Objects.requireNonNull(Utils.chat("&bCrystal: " + PlayerFileManager.getPlayerCrystals(player)))).setScore(3);
//		dark crystal
		objective.getScore(Objects.requireNonNull(Utils.chat("&5Dark Crystal: " + PlayerFileManager.getPlayerDarkCrystals(player)))).setScore(2);

		objective.getScore(Objects.requireNonNull(Utils.chat(""))).setScore(1);


		objective.getScore(Objects.requireNonNull(Utils.chat("&7&m----------------------"))).setScore(0);


		player.setScoreboard(scoreboard);



	}

	private void updateScoreboard(Player player) {
		Scoreboard scoreboard = player.getScoreboard();
		Objective objective = scoreboard.getObjective(IslandDefender.getInstance().getName());

		LuckPerms luckPerms = LuckPermsProvider.get();
//		get the primary group of the player prefix
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();


		assert objective != null;
		objective.getScore(Objects.requireNonNull(Utils.chat("&7&m-----------------------"))).setScore(8);
		objective.getScore(Objects.requireNonNull(Utils.chat(" "))).setScore(7);
		objective.getScore(Objects.requireNonNull(Utils.chat("&6&l" + player.getName() +
				" | " + prefix))).setScore(6);
		objective.getScore(Objects.requireNonNull(Utils.chat("  "))).setScore(5);

//		coins
		objective.getScore(Objects.requireNonNull(Utils.chat("&eCoins: " + PlayerFileManager.getPlayerCoins(player)))).setScore(4);
//		crystal
		objective.getScore(Objects.requireNonNull(Utils.chat("&bCrystal: " + PlayerFileManager.getPlayerCrystals(player)))).setScore(3);
//		dark crystal
		objective.getScore(Objects.requireNonNull(Utils.chat("&5Dark Crystal: " + PlayerFileManager.getPlayerDarkCrystals(player)))).setScore(2);

		objective.getScore(Objects.requireNonNull(Utils.chat(""))).setScore(1);


		objective.getScore(Objects.requireNonNull(Utils.chat("&7&m----------------------"))).setScore(0);

	}

	public static Board getInstance() {
		return instance;
	}

}

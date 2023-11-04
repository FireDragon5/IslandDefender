package me.firedragon5.islanddefender.menu.playerStats;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerStatsMenu extends FireMenu implements Listener {


	public PlayerStatsMenu(Player player, String title, int size) {
		super(player, title, size);
	}


	public PlayerStatsMenu(){

	}


	public void setupMenu(Player player) {

		List<String> playerHead = new ArrayList<>();
		List<String> playerKillStats = new ArrayList<>();
		List<String> playerMineStats = new ArrayList<>();

//		Player first and last join
		String firstJoin = String.valueOf(player.getFirstPlayed());
		String lastJoin = String.valueOf(player.getLastSeen());

// Convert to date
		String firstJoinDate = formatDate(firstJoin);
		String lastJoinDate = formatDate(lastJoin);

// Format the time played
		String playerTime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) + "";
		String formattedTime = formatTime(playerTime);

//		Player rank
		String playerRank = PlayerFileManager.getPlayerRank(player);

		playerHead.add(Utils.chat(""));
		playerHead.add(Utils.chat("&7Rank: &b&l" + playerRank));
		playerHead.add(Utils.chat(""));

// Add the formatted information to the lists
		playerHead.add(Utils.chat("&7First join time: &a" + firstJoinDate));
		String isOnline = player.isOnline() ? "&aOnline" : "&cOffline";
		playerHead.add(Utils.chat("&7Last played: &a" + lastJoinDate));
		playerHead.add(Utils.chat("&7Status: " + isOnline));
		playerHead.add(Utils.chat("&7Time played: &a" + formattedTime));
		playerHead.add(Utils.chat(""));
		addPlayerHead(player, "&b" + player.getName() + " Stat Page", 4, playerHead);


//		 Kills
		playerKillStats.add(Utils.chat(""));
// Kills
		playerKillStats.add(Utils.chat("&7Total kills: &a" + player.getStatistic(Statistic.KILL_ENTITY, EntityType.PLAYER)));
// Deaths
		playerKillStats.add(Utils.chat("&7Total deaths: &c" + player.getStatistic(Statistic.DEATHS)));
// KDR
		if (player.getStatistic(Statistic.DEATHS) == 0) {
			playerKillStats.add(Utils.chat("&7KDR: &4" + 0));
		} else {
			int kills = player.getStatistic(Statistic.KILL_ENTITY, EntityType.PLAYER);
			playerKillStats.add(Utils.chat("&7KDR: &6" + ((double) kills / player.getStatistic(Statistic.DEATHS))));
		}
		setItem(19, Material.DIAMOND_SWORD, "&cKills", playerKillStats);


//		 Mines
		playerMineStats.add(Utils.chat(""));
//		Mine all the blocks the player has mined
		playerMineStats.add(Utils.chat("&7Total blocks mined: &e" +
				player.getStatistic(Statistic.MINE_BLOCK, Material.STONE)));
		setItem(22, Material.DIAMOND_PICKAXE, "&eMines", playerMineStats);

//		 Clan
//		Player clan if they have one
		if (PlayerFileManager.isInClan(player)) {

			String clanName = PlayerFileManager.getPlayerClanName(player);
			List<String> playerClan = new ArrayList<>();
			playerClan.add(Utils.chat(""));

			playerClan.add(Utils.chat("&7Clan Name: &b" + clanName));
			setItem(25, Material.IRON_CHESTPLATE, "&bClan", playerClan);
		}

		glass(Material.BLACK_STAINED_GLASS_PANE);

	}


	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public static void onInventoryClick(InventoryClickEvent event) {

		if (event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Player stats"))) {

			event.setCancelled(true);

			if (event.getCurrentItem() == null) {
				return;
			}


		}

	}

	private String formatDate(String timestamp) {
		// Convert the timestamp to YYYY/MM/DD format

//		Convert seconds to date format
		Date date = new Date(Long.parseLong(timestamp));
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		return sdf.format(date);
	}

	private String formatTime(String playerTime) {
		int totalSeconds = Integer.parseInt(playerTime) / 20;
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
	}


}

package me.firedragon5.islanddefender.menu.playerStats;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsMenu extends FireMenu implements Listener {


	public PlayerStatsMenu(Player player, String title, int size) {
		super(player, title, size);
	}


	public PlayerStatsMenu(){

	}


	public void setupMenu(Player player) {


		slotNumbers();

		List<String> playerHead = new ArrayList<>();
		List<String> playerKillStats = new ArrayList<>();
		List<String> playerMineStats = new ArrayList<>();

//		First join time
		playerHead.add(Utils.chat("&7First join time: &f" + player.getFirstPlayed()));
//		Lasted played
		playerHead.add(Utils.chat("&7Last played: &f" + player.getLastSeen()));

//		Total time played
		playerHead.add(Utils.chat("&7Total time played: &f" + player.getStatistic(Statistic.PLAY_ONE_MINUTE)));

		addPlayerHead(player, "&7" + player.getName() + " Stat Page", 4, playerHead);


//		Kills
		playerKillStats.add(Utils.chat("&7Total kills: &f" + player.getStatistic(Statistic.PLAYER_KILLS)));
//		Deaths
		playerKillStats.add(Utils.chat("&7Total deaths: &f" + player.getStatistic(Statistic.DEATHS)));
//		KDR
		playerKillStats.add(Utils.chat("&7KDR: &f" + player.getStatistic(Statistic.PLAYER_KILLS) / player.getStatistic(Statistic.DEATHS)));
		setItem(13, Material.DIAMOND_SWORD, "&7Kills", playerKillStats);

//		Mine
		playerMineStats.add(Utils.chat("&7Total blocks mined: &f" + player.getStatistic(Statistic.MINE_BLOCK)));
		setItem(22, Material.DIAMOND_PICKAXE, "&7Mines", playerMineStats);

//		Player clan if they have one
		if (PlayerFileManager.isInClan(player)) {

			String clanName = PlayerFileManager.getPlayerClanName(player);

			List<String> playerClan = new ArrayList<>();
			playerClan.add(Utils.chat("&7Clan Name: &f" + clanName));
			setItem(31, Material.IRON_CHESTPLATE, "&7Clan", playerClan);
		}

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

}

package me.firedragon5.islanddefender.menu.ranks;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedraong5.firesapi.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class RankMenu extends Menu implements Listener {

	public RankMenu() {

	}

	public RankMenu(Player player, String title, int size) {
		super(player, title, size);
	}


	public void setupMenu() {


//		Show all the ranks in the menu

		RankFileManager rankFileManager = RankFileManager.getFileManager();

		List<String> rankLore = new ArrayList<>();

		int i = 0;

		for (String rank : rankFileManager.getRanks()) {

			String rankName = rankFileManager.getName(rank);
			String rankPrefix = rankFileManager.getPrefix(rank);
			List<String> rankPerks = rankFileManager.getRankPerks(rank);
			int rankCost = rankFileManager.getCost(rank);
			Material rankDisplayBlock = rankFileManager.getDisplayBlock(rank);

			rankLore.add("&7Rank Name: &a" + rankName);
			rankLore.add("&7Rank Prefix: &a" + rankPrefix);
			rankLore.add("&7Rank Perks: &a" + rankPerks);
			rankLore.add("&7Rank Cost: &a" + rankCost);

			if (rankDisplayBlock == null) {
				rankDisplayBlock = Material.BEDROCK;
			}

			setItem(i, rankDisplayBlock, rankName, rankLore);

			i++;


		}


		glass(Material.BLACK_STAINED_GLASS_PANE);

	}

	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&bRanks"))) return;
		event.setCancelled(true);


	}


}

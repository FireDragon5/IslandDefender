package me.firedragon5.islanddefender.menu.ranks;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;


public class RankMenu extends FireMenu implements Listener {

	public RankMenu() {

	}

	public RankMenu(Player player, String title, int size) {
		super(player, title, size);
	}


	public void setupMenu() {


//		Show all the ranks in the menu

		RankFileManager rankFileManager = RankFileManager.getFileManager();

		Player player = getPlayer();

		String playerRank = PlayerFileManager.getPlayerRank(player);


		for (String rank : rankFileManager.getRanks()) {

			List<String> rankLore = new ArrayList<>();

			String rankName = rankFileManager.getName(rank);
			String rankPrefix = rankFileManager.getPrefix(rank);
			int rankSlot = rankFileManager.getSlot(rank);
			String rankColor = rankFileManager.getColor(rank);

			List<String> rankPerks = rankFileManager.getRankPerks(rank);
			int rankCost = rankFileManager.getCost(rank);
			Material rankDisplayBlock = rankFileManager.getDisplayBlock(rank);
			rankLore.add("");
			rankLore.add("&7Prefix: " + rankPrefix);
			rankLore.add("");
			rankLore.add("&7Perks: ");
			for (String perk : rankPerks) {
				rankLore.add(String.format("&7- %s%s", rankColor, perk));
			}
			rankLore.add("");
			rankLore.add(String.format("&7Cost: %s%s", rankColor, rankCost));

			rankLore.add("");
//			Add a glow to to the players current rank
			assert playerRank != null;
			if (playerRank.equalsIgnoreCase(rank)) {
				rankLore.add(String.format("%s&lCurrent Rank", rankColor));
			}

//			if default rank slot is  = 0 and Member rank slot is = 0 then pick the next open slot
			if (rankSlot == 0) {
				rankSlot = getNextOpenSlot();
			}

//			if the slot is more then the menu size send message to user
			if (rankSlot > rankFileManager.getMenuSize()) {

				UtilsMessage.errorMessage(player, "The rank " + rank + " has a slot of " + rankSlot +
						" which is more then the menu size of " + rankFileManager.getMenuSize() +
						" please change the slot in the config.");

//				update the the menu size to the next row
				rankFileManager.setMenuSize(rankFileManager.getMenuSize() + 9);
				rankFileManager.saveRankConfig();

//				Send a error message and tell the player that the menu size has been updated
				UtilsMessage.sendMessage(player, "&c&lError: &7The menu size has been updated to &a" + rankFileManager.getMenuSize());


			}
			try {
				setItem(rankSlot, rankDisplayBlock, rankName, rankLore);
			} catch (ArrayIndexOutOfBoundsException e) {

				player.closeInventory();

				UtilsMessage.errorMessage(player, "The rank " + rank + " has a slot of " + rankSlot +
						" which is more then the menu size of " + rankFileManager.getMenuSize() +
						" please change the slot in the config.");

//				update the the menu size to the next row
				rankFileManager.setMenuSize(rankFileManager.getMenuSize() + 9);
				rankFileManager.saveRankConfig();

//				Send a error message and tell the player that the menu size has been updated
				UtilsMessage.sendMessage(player, "&c&lError: &7The menu size has been updated to &a" + rankFileManager.getMenuSize());
				UtilsMessage.sendMessage(player, "&c&lPlease rerun the command!");

			} finally {
				rankLore.clear();
			}


		}


		glass(Material.BLACK_STAINED_GLASS_PANE);

	}

	private int getNextOpenSlot() {
		int nextOpenSlot = 0;
		for (int i = 0; i < RankFileManager.getFileManager().getMenuSize(); i++) {
			if (getItem(i) == null) {
				nextOpenSlot = i;
				break;
			}
		}
		return nextOpenSlot;
	}

	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Ranks"))) return;
		event.setCancelled(true);

		Player player = (Player) event.getWhoClicked();

		// Get the list of the ranks
		String playerRank = PlayerFileManager.getPlayerRank(player);
		RankFileManager rankFileManager = RankFileManager.getFileManager();
		String[] ranks = rankFileManager.getRanks();

		// Get the clicked slot
		int clickedSlot = event.getRawSlot();

		// Ensure the clicked slot is within the range of available ranks
		if (clickedSlot >= 0 && clickedSlot < ranks.length) {
			// Get the rank corresponding to the clicked slot

			String clickedRank = ranks[clickedSlot];
			// Check if the player doesn't already own the clicked rank
			if (!playerRank.equalsIgnoreCase(clickedRank)) {
				// Check if the clicked rank is not a higher rank
				if (!isHigherRank(playerRank, clickedRank)) {
					// The player doesn't own the rank and it's not a higher rank, so you can open the "rankbuy" menu here.
					RankPurchaseMenu rankPurchaseMenu = new RankPurchaseMenu(player, "&7Purchase Rank", 9);
					rankPurchaseMenu.setupMenu();
					rankPurchaseMenu.openMenu();
				}
			}
		}
	}

	// Create a method to check if one rank is higher than another
	private boolean isHigherRank(String rank, String otherRank) {
		// Implement your logic here to determine if one rank is higher than another.
		// For example, you can compare their positions in a list of ranks.
		// Return true if rank is higher, false otherwise.

		RankFileManager rankFileManager = RankFileManager.getFileManager();
		int rankSlot = rankFileManager.getSlot(rank);
		int otherRankSlot = rankFileManager.getSlot(otherRank);
		return rankSlot > otherRankSlot;


	}


}

package me.firedragon5.islanddefender.menu.ranks;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class RankPurchaseMenu extends FireMenu implements Listener {


	public RankPurchaseMenu(Player player, String title, int size) {
		super(player, title, size);
	}

	public RankPurchaseMenu() {

	}


	public void setupMenu() {


		setItem(1, Material.GREEN_STAINED_GLASS_PANE, "&aConfirm", List.of("&7Click to confirm your purchase!"));

		setItem(7, Material.RED_STAINED_GLASS_PANE, "&cCancel", List.of("&7Click to cancel your purchase!"));


		glass(Material.BLACK_STAINED_GLASS_PANE);

	}


	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();

		if (event.getView().getTitle().equalsIgnoreCase(UtilsMessage.onChat("&7Purchase Rank"))) {
			event.setCancelled(true);


			if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) {
				return;
			}

//			Get the player rank
			String playerRank = PlayerFileManager.getPlayerRank(player);
//			Now get the next rank form the players rank
			String nextRank = RankFileManager.getFileManager().getNextRank(playerRank);


			if (event.getSlot() == 1) {

//			Get the rank the user want to purchase
				RankFileManager rankFileManager = RankFileManager.getFileManager();

				if (nextRank == null) {
					player.sendMessage(UtilsMessage.onChat("&cThis rank does not exist!"));
					player.closeInventory();
					return;
				} else if (nextRank.equalsIgnoreCase("max")) {
					player.sendMessage(UtilsMessage.onChat("&cYou have reached the max rank!"));
					player.closeInventory();
					return;
				}


//			Check if the user has the correct amount of coins
				if (PlayerFileManager.getPlayerCoins(player) < rankFileManager.getCost(nextRank)) {
					player.sendMessage(UtilsMessage.onChat("&cYou do not have enough coins to purchase this rank!"));
					player.closeInventory();
					return;
				} else {

//			If the users pass everything, then take the money and give them the rank
					PlayerFileManager.removePlayerCoins(player, rankFileManager.getCost(nextRank));
					player.sendMessage(UtilsMessage.onChat("&7You have purchased the rank &a" + nextRank + "&7!"));
					player.closeInventory();
//			Run the permission command
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
					String command = rankFileManager.getLuckpermRunCommand()
							.replace("%player%", player.getName())
							.replace("%rank%", nextRank);
					command = command.replace("%player%", player.getName());
					Bukkit.getServer().dispatchCommand(console, command);

//					Change player config
					PlayerFileManager.setPlayerRank(player, nextRank);

					//		get the player rank
					String rank = PlayerFileManager.getPlayerRank(player);
//		get the display name of that rank
					String rankDisplayName = RankFileManager.getFileManager().getPrefix(rank);

					player.setPlayerListName(
							Utils.chat(rankDisplayName + " &f" + player.getName()));

//					Player name tag
					player.setDisplayName(Utils.chat(rankDisplayName + " &f" + player.getName()));

					return;


				}
			}

			// Cancel button
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&cCancel"))) {
				player.closeInventory();
			}
		}

	}


}




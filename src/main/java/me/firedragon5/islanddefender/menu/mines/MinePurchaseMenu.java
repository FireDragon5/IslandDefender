package me.firedragon5.islanddefender.menu.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class MinePurchaseMenu extends FireMenu implements Listener {


	private String mine;


	public MinePurchaseMenu() {

	}

	public MinePurchaseMenu(Player player, String name, int size) {
		super(player, name, size);
	}


	public void setupMenu(String mine) {

		setMine(mine);


//		Confirm button
		setItem(1, Material.GREEN_STAINED_GLASS_PANE, "&aConfirm", List.of("&7Click to confirm your purchase!"));

//		Cancel button
		setItem(7, Material.RED_STAINED_GLASS_PANE, "&cCancel", List.of("&7Click to cancel your purchase!"));


	}

	public void setMine(String mine) {
		this.mine = mine;
	}

	public String getMine() {
		return mine;
	}

	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getView().getTitle().equalsIgnoreCase(UtilsMessage.onChat("&7Purchase Mine"))) {
			event.setCancelled(true);

			if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) {
				return;
			}

			// Confirm button

			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&aConfirm"))) {

//			Get the mine the user want to purchase
				MineFileManager mineManager = MineFileManager.getFileManager();
				String mine = getMine();


//			Check if the user has the correct amount of money
				if (PlayerFileManager.getPlayerCoins(player) < mineManager.getCost(mine)) {
					player.sendMessage(UtilsMessage.onChat("&cYou do not have enough coins to purchase this mine!"));
					player.closeInventory();
					return;
				} else {

//			If the users pass everything, then take the money and give them the rank
					PlayerFileManager.removePlayerCoins(player, mineManager.getCost(mine));
					player.sendMessage(UtilsMessage.onChat("&7You have purchased the mine &a" + mine + "&7!"));
					player.closeInventory();

//					Set the new mine
					PlayerFileManager.setPlayerMine(player, mine);

				}
			}

			// Cancel button
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&cCancel"))) {
				player.closeInventory();
			}
		}
	}


}

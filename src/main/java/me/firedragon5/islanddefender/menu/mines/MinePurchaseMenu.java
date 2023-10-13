package me.firedragon5.islanddefender.menu.mines;

import me.firedraong5.firesapi.menu.Menu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class MinePurchaseMenu extends Menu implements Listener {

	public MinePurchaseMenu() {

	}

	public MinePurchaseMenu(Player player, String name, int size) {
		super(player, name, size);
	}

	public void setupMenu() {

//		Confirm button
		setItem(1, Material.GREEN_STAINED_GLASS_PANE, "&aConfirm", List.of("&7Click to confirm your purchase!"));

//		Cancel button
		setItem(7, Material.RED_STAINED_GLASS_PANE, "&cCancel", List.of("&7Click to cancel your purchase!"));


	}

	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();

		if (event.getView().getTitle().equalsIgnoreCase(UtilsMessage.onChat("&bPurchase Mine"))) {

			event.setCancelled(true);

			if (event.getCurrentItem() == null) {
				return;
			}

			if (event.getCurrentItem().getItemMeta() == null) {
				return;
			}

//			Confirm button
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&aConfirm"))) {

				//				Check if the player has enough money
//				if (PlayerFileManager.getBal(player) >= 1000) {
//
//				}
			}

//			Cancel button
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&cCancel"))) {
				player.closeInventory();
			}

		}
	}


}

package me.firedragon5.islanddefender.menu.shop;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SellMenu extends FireMenu implements Listener {

	public SellMenu() {

	}

	public SellMenu(Player player, String title, int size) {
		super(player, title, size);
	}



	public void setup() {


		SellFileManager sellFileManager = SellFileManager.getFileInstance();

		List<String> lore = new ArrayList<>();
		lore.add("&7Click to sell all your items");

		Material material = sellFileManager.getSellAllItem();

		List<Integer> slots = new ArrayList<>();


		for (int i = 45; i <= 53; i++) {
			slots.add(i);
		}

		glass(Material.BLACK_STAINED_GLASS_PANE, "", slots);

		setItem(49, material, "&aSell all", lore);


	}


	@Override
	public void openMenu() {
		super.openMenu();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Sell Menu"))) return;
		Player player = (Player) event.getWhoClicked();

		if (event.getSlot() == 49) {
			event.setCancelled(true);

			SellFileManager sellFileManager = SellFileManager.getFileInstance();

			int value = 0;

			for (int i = 0; i < 45; i++) {
				ItemStack itemStack = event.getInventory().getItem(i);
				if (itemStack != null) {
					Material material = itemStack.getType();
					int itemAmount = itemStack.getAmount();

					// Check if the material is in your YML file
					if (sellFileManager.getMaterialValue(material) != 0) {
						int itemValue = sellFileManager.getMaterialValue(material) * itemAmount;
						value += itemValue;
						event.getInventory().setItem(i, null);
					} else {
						// Item not in YML, give it back to the player
						player.getInventory().addItem(itemStack);
						event.getInventory().setItem(i, null);
					}
				}
			}

			UtilsMessage.sendMessage(player, "&7You sold all your items for &a" + value + " coins");
			PlayerFileManager.addPlayerCoins(player, value);
			player.closeInventory();
		}
	}


}

package me.firedragon5.islanddefender.menu.shop;

import me.firedragon5.islanddefender.Utils;
import me.firedraong5.firesapi.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellMenu extends Menu implements Listener {

	public SellMenu() {

	}

	public SellMenu(Player player, String title, int size) {
		super(player, title, size);
	}


	public void setup() {

		List<String> lore = new ArrayList<>();
		lore.add("&7Click to sell all your items");
//		Show the value of all the items
		lore.add("&7Value: &a" + 0);


		setItem(49, Material.EMERALD_BLOCK, "&aSell all", lore);


	}


	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() != this) return;
		Player player = (Player) event.getWhoClicked();
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getType() == Material.AIR) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Sell Menu"))) {
			event.setCancelled(true);
			if (event.getRawSlot() == 49) {
				player.closeInventory();
				player.sendMessage(Objects.requireNonNull(Utils.chat("&aYou have sold all your items")));
			}
		}
	}


}

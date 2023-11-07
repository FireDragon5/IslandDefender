package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CosmeticListener implements Listener {

	@EventHandler
	public void onCosmeticClick(InventoryClickEvent e) {

		if (e.getInventory() != null && e.getCurrentItem() != null){

			if (e.getView().getTitle().equals(Utils.chat("&7Cosmetics"))) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&7Hats"))) {
					e.setCancelled(true);
				}
			}

		}
	}

}

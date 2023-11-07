package me.firedragon5.islanddefender.menu.kits;

import me.firedragon5.islanddefender.filemanager.kits.KitsFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class KitsMenu extends FireMenu implements Listener {


	public KitsMenu() {

	}

	public KitsMenu(Player player, String name, int size) {
		super(player, name, size);
	}

	public void setupMenu() {
		KitsFileManager kitsFileManager = KitsFileManager.getFileManager();
		int slot = 10;

		for (String kit : kitsFileManager.getKits()) {
			// Set up lore for the kit
			List<String> lore = new ArrayList<>();
			lore.add("&aKit Name: &7" + kitsFileManager.getName(kit));
			lore.add("&7Items:");

			for (String item : kitsFileManager.getItems(kit)) {
				lore.add("  &a- &7" + item);
			}

			Material material = kitsFileManager.getMaterial(kit);
			setItem(slot, material, kit, lore);
			lore.clear();

			// Increase the slot by 1
			slot++;
		}

		borderGlass(Material.BLACK_STAINED_GLASS_PANE);
	}



	@Override
	public void openMenu() {
		super.openMenu();
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(UtilsMessage.onChat("&7Kits"))) return;
		event.setCancelled(true);

		Player player = (Player) event.getWhoClicked();

//		Check the item the players clicks on.
//		Check if the player has the perms to claim the kit
		if (event.getCurrentItem() != null) {
			if (event.getCurrentItem().getItemMeta() != null) {
//				Don't do anything if the player clicks on a glass pane
				if (event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
					String kitName = event.getCurrentItem().getItemMeta().getDisplayName();
					if (KitsFileManager.getFileManager().getPermission(kitName) == null ||
							player.hasPermission(KitsFileManager.getFileManager().getPermission(kitName))) {

//						Check the cooldown of the kit
						if (KitsFileManager.getFileManager().getCoolDown(kitName) > 0) {
							UtilsMessage.errorMessage(player, "You must wait " + KitsFileManager.getFileManager().getCoolDown(kitName) +
									" seconds before claiming this kit again!");
							return;
						}

						KitsFileManager.getFileManager().giveKit(player, kitName);
					} else {
						UtilsMessage.errorMessage(player, "You do not have permission to claim this kit!");
					}
				}
			}
		}


	}


}

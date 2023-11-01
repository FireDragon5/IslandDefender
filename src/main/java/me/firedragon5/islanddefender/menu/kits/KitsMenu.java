package me.firedragon5.islanddefender.menu.kits;

import me.firedragon5.islanddefender.filemanager.kits.KitsFileManger;
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

		KitsFileManger kitsFileManger = KitsFileManger.getFileManager();

		List<String> lore = new ArrayList<>();

		int i = 0;

		for (String kit : kitsFileManger.getKits()) {

//			Display name
			lore.add("&aKit Name: &7" + kitsFileManger.getName(kit));

			lore.add("");

//			Show the kits items
			lore.add("&7Items: ");
			for (String item : kitsFileManger.getItems(kit)) {
				lore.add("&a- &7" + item);
			}


			Material material = kitsFileManger.getMaterial(kit);

			setItem(i, material, kit, lore);

			lore.clear();
		}

		glass(Material.BLACK_STAINED_GLASS_PANE);

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
					if (KitsFileManger.getFileManager().getPermission(kitName) == null ||
							player.hasPermission(KitsFileManger.getFileManager().getPermission(kitName))) {

//						Check the cooldown of the kit
						if (KitsFileManger.getFileManager().getCoolDown(kitName) > 0) {
							UtilsMessage.errorMessage(player, "You must wait " + KitsFileManger.getFileManager().getCoolDown(kitName) +
									" seconds before claiming this kit again!");
							return;
						}

						KitsFileManger.getFileManager().giveKit(player, kitName);
					} else {
						UtilsMessage.errorMessage(player, "You do not have permission to claim this kit!");
					}
				}
			}
		}


	}


}

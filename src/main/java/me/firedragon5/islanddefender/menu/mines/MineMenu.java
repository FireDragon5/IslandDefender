package me.firedragon5.islanddefender.menu.mines;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.menu.Menu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MineMenu extends Menu implements Listener {

	public MineMenu() {

	}

	public MineMenu(Player player, String name, int size) {
		super(player, name, size);
	}


	public void setupMenu() {

//		Add all the mines to the menu that is in the config
		MineFileManager mineManager = MineFileManager.getFileManager();

		List<String> mineLore = new ArrayList<>();

//		When the user don't have own the mine don't show its display block show bedrock
		int i = 0;

		for (String mine : mineManager.getMineList()) {

//			rank

			String rank = mineManager.getRank(mine);
			if (rank.equalsIgnoreCase("default")) {
				rank = "None";
			}

			mineLore.add("&7Rank Needed: &a" + rank);
//			cost
			mineLore.add("&7Mine cost: &a" + mineManager.getCost(mine));

			// Check if the player has permission for this mine
			Player player = getPlayer();
			boolean hasPermission = player.hasPermission("islanddefender.mine." + mineManager.getRank(mine));

//			if the permission is equal to none this is the first mine so everyone has access to it
			if (mineManager.getPermission(mine).equalsIgnoreCase("none")) {
				hasPermission = true;
			}

			// Determine the material for the display block
			Material material = hasPermission
					? Material.getMaterial(Objects.requireNonNull(mineManager.getDisplayBlock(mine)))
					: Material.BEDROCK;

			setItem(i, material, "&a&l" + mine, mineLore);

			i++;
			mineLore.clear();

		}

		glass(Material.BLACK_STAINED_GLASS_PANE);

	}

	@Override
	public void openMenu() {
		super.openMenu();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&bMines"))) return;
		event.setCancelled(true);


//		When the user clicks on the mine
//		Check if the have access to the mine if not they ask them to buy it or rank up
//		If the have access (permission) tp them to the mine

		if (event.getCurrentItem() == null) return;

		Player player = (Player) event.getWhoClicked();


		MineFileManager mineManager = MineFileManager.getFileManager();


		String mine = Utils.stripColor(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName());

//		if the permission is equal to none this is the first mine so everyone has access to it
		if (mineManager.getPermission(mine).equalsIgnoreCase("none")) {
			UtilsMessage.sendMessage(player, "&7Teleporting to &a&l" + mine + "!");

			//			Teleport the player to the mine
			String[] spawn = mineManager.getSpawn(mine).split(",");
			Location location = new Location(player.getWorld(),
					Double.parseDouble(spawn[0]),
					Double.parseDouble(spawn[1]),
					Double.parseDouble(spawn[2]));
			player.teleport(location);

			player.closeInventory();
			return;
		}


//		Check if the user is the correct rank and have permission
		if (player.hasPermission("islanddefender.mine." + mineManager.getRank(mine))) {
			UtilsMessage.sendMessage(player, "&7Teleporting to &a&l" + mine + "!");
			player.closeInventory();

//			Teleport the player to the mine
			String[] spawn = mineManager.getSpawn(mine).split(",");
			Location location = new Location(player.getWorld(),
					Double.parseDouble(spawn[0]),
					Double.parseDouble(spawn[1]),
					Double.parseDouble(spawn[2]));
			player.teleport(location);
		} else {
			UtilsMessage.errorMessage(player, "You do not have access to this mine");

			player.closeInventory();


//			Open the purchase menu if the user is the correct rank but does not have permission

			MinePurchaseMenu minePurchaseMenu = new MinePurchaseMenu(player, "&bPurchase Mine", 9);
			minePurchaseMenu.setupMenu(mine);
			minePurchaseMenu.openMenu();


		}
	}


}

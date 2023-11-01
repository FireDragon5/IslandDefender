package me.firedragon5.islanddefender.menu.mines;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
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

public class MineMenu extends FireMenu implements Listener {

	public MineMenu() {

	}

	public MineMenu(Player player, String name, int size) {
		super(player, name, size);
	}


	public void setupMenu() {

//		Add all the mines to the menu that is in the config
		MineFileManager mineManager = MineFileManager.getFileManager();

		List<String> mineLore = new ArrayList<>();

		for (String mine : mineManager.getMineList()) {

			Player player = getPlayer();
			String mineColor = mineManager.getColor(mine);

//			rank
			String rank = mineManager.getRank(mine);

			mineLore.add("");

			mineLore.add("&7Rank Needed: &a" + rank);

			mineLore.add("");

//			cost
			mineLore.add("&7Cost: &a" + mineManager.getCost(mine));

			mineLore.add("");

//			Show current mine if the player has one
			String playerMine = PlayerFileManager.getPlayerMine(player);
			if (playerMine != null && playerMine.equalsIgnoreCase(mine)) {
				mineLore.add(String.format("%sCurrent Mine", mineColor));
			}

			mineLore.add("");

			Material material = mineManager.getDisplayBlock(mine);

			int slot = mineManager.getSlot(mine);

			setItem(slot, material, String.format("%s%s", mineColor, mine), mineLore);

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
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Mines"))) return;
		event.setCancelled(true);

		Player player = (Player) event.getWhoClicked();
		MineFileManager mineManager = MineFileManager.getFileManager();

		if (event.getCurrentItem() == null) return;

		String mineDisplayName = Utils.stripColor(Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName());

		String playerMine = PlayerFileManager.getPlayerMine(player);
		String playerRank = PlayerFileManager.getPlayerRank(player);

		// Check if the clicked mine is the player's current mine
		if (playerMine != null && playerMine.equalsIgnoreCase(mineDisplayName)) {
			teleportPlayerToMine(player, mineManager, mineDisplayName);
		} else {
			String mineRank = mineManager.getRank(mineDisplayName);
			if (playerRank != null && playerRank.equalsIgnoreCase(mineRank)) {
				teleportPlayerToMine(player, mineManager, mineDisplayName);
			} else {
				// If the player doesn't have access, open the purchase menu
				openMinePurchaseMenu(player, mineDisplayName);
			}
		}
	}

	private void teleportPlayerToMine(Player player, MineFileManager mineManager, String mineDisplayName) {
		UtilsMessage.sendMessage(player, "&7Teleporting to &a&l" + mineDisplayName + "!");
		player.closeInventory();

		String location = mineManager.getSpawn(mineDisplayName);
		String[] spawn = location.split(",");
		Location location1 = new Location(player.getWorld(),
				Double.parseDouble(spawn[0]),
				Double.parseDouble(spawn[1]),
				Double.parseDouble(spawn[2]));
		player.teleport(location1);
	}

	private void openMinePurchaseMenu(Player player, String mineDisplayName) {
		MinePurchaseMenu minePurchaseMenu = new MinePurchaseMenu(player, "&7Purchase Mine", 9);
		minePurchaseMenu.setupMenu(mineDisplayName);
		minePurchaseMenu.openMenu();
	}
}

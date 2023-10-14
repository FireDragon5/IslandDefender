package me.firedragon5.islanddefender.menu.mines;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.menu.Menu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class MinePurchaseMenu extends Menu implements Listener {


	private String mine;


	public MinePurchaseMenu() {

	}

	public MinePurchaseMenu(Player player, String name, int size) {
		super(player, name, size);
	}

	public void setupMenu(String mine) {

		setMine(mine);

		System.out.println(mine);


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

		if (event.getView().getTitle().equalsIgnoreCase(UtilsMessage.onChat("&bPurchase Mine"))) {
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
				if (IslandDefender.getEconomy().getBalance(player) < mineManager.getCost(mine)) {
					player.sendMessage(UtilsMessage.onChat("&cYou do not have enough money to purchase this mine!"));
					player.closeInventory();
					return;
				} else {

//			If the users pass everything, then take the money and give them the rank
					IslandDefender.getEconomy().withdrawPlayer(player, mineManager.getCost(mine));
//			Run the permission command
					ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

					String command = mineManager.getPermissionCommand(mine);

					System.out.println(command);

					command = command.replace("%player%", player.getName());
					player.getServer().dispatchCommand(console, command);
				}
			}

			// Cancel button
			if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(UtilsMessage.onChat("&cCancel"))) {
				player.closeInventory();
			}
		}
	}


}

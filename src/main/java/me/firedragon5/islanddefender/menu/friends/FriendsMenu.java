package me.firedragon5.islanddefender.menu.friends;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.menu.FireMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendsMenu extends FireMenu implements Listener {

	public FriendsMenu() {

	}

	public FriendsMenu(Player player, String name, int size) {
		super(player, name, size);
	}

	public void setupMenu() {
		// Get the players' friends and their heads from their UUID, directly as a list
		List<String> listOfPlayers = PlayerFileManager.getPlayerFriends(getPlayer());


		// Convert the list of UUIDs to Player objects
		List<Player> players = new ArrayList<>();
		for (String listOfPlayer : listOfPlayers) {
			Player onlinePlayer = Bukkit.getPlayer(UUID.fromString(listOfPlayer));
			if (onlinePlayer != null) {
				players.add(onlinePlayer);
			}
		}


		// Then add the players to the menu
		for (int i = 0; i < players.size(); i++) {
			addPlayerHead(players.get(i), "&a&l" + players.get(i).getName(), i);
		}
	}


	@Override
	public void openMenu() {
		super.openMenu();
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {

		if (event.getClickedInventory() == null) return;
		if (event.getCurrentItem() == null) return;
		if (event.getClickedInventory().getHolder() == null) return;
//		Check the name of the inventory
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Friends"))) return;
		event.setCancelled(true);


	}

}

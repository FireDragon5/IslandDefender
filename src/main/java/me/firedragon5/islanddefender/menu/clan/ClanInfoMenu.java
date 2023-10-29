package me.firedragon5.islanddefender.menu.clan;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class ClanInfoMenu extends Menu implements Listener {


	public ClanInfoMenu() {
	}

	public ClanInfoMenu(Player player, String title, int size) {
		super(player, title, size);
	}

	public void setupMenu(String clanName) {

//		slotNumbers();

		List<String> loreClanInfo = new ArrayList<>();
//		Clan name
		loreClanInfo.add("&7Clan name: &a" + clanName);
		loreClanInfo.add("&b---------------------------------");
//		Tag of the clan
		loreClanInfo.add("&7Tag: &a" + ClanFolderManager.getFileManager().getClanTag(clanName));
//		Show the leader, when the clan was created
		loreClanInfo.add("&7Leader: &a" + ClanFolderManager.getFileManager().getClanLeader(clanName));
//		Total members
		loreClanInfo.add("&7Members: &a" + ClanFolderManager.getFileManager().getClanMembers(clanName).size());
//		Time when clan was create
		loreClanInfo.add("&7Created: &a" + ClanFolderManager.getFileManager().getClanCreated(clanName));
		loreClanInfo.add("&b---------------------------------");
//		Show clan visibility
		loreClanInfo.add("&7Visibility: &a" + ClanFolderManager.getFileManager().getClanVisible(clanName));


		setItem(4, Material.CHEST, "&a&lClan Info", loreClanInfo);


		List<String> loreClanMembers = new ArrayList<>();
//		Show the members of the clan
		loreClanMembers.add("&7Members: &a" + ClanFolderManager.getFileManager().getClanMembers(clanName));

		setItem(19, Material.PLAYER_HEAD, "&a&lClan Members", loreClanMembers);


		List<String> loreClanStats = new ArrayList<>();
//		Show the stats of the clan
//		Power
		loreClanStats.add("&7Power: &a" + ClanFolderManager.getFileManager().getClanPower(clanName));
//		Balance
		loreClanStats.add("&7Balance: &a" + ClanFolderManager.getFileManager().getClanBalance(clanName));


		setItem(25, Material.DIAMOND_SWORD, "&a&lClan Stats", loreClanStats);

		glass(Material.BLACK_STAINED_GLASS_PANE);


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
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&7Clan Info"))) return;
		event.setCancelled(true);


	}


}

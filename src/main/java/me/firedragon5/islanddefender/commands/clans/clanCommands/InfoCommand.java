package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.menu.clan.ClanInfoMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class InfoCommand {


	//	info clan when they specify a clan
	public static void infoClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");

			return;
		}

//		if the clan does not exist
		if (!ClanFolderManager.getFileManager().clanExists(clanName)) {
			UtilsMessage.errorMessage(player, "Clan does not exist!");
			return;
		}

//		open the menu
		ClanInfoMenu menu = new ClanInfoMenu(player, "&7Clan Info", 54);
		menu.setupMenu(clanName);
		menu.openMenu();


	}

	//	info clan when they are in a clan
	public static void infoClan(Player player) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "islanddefender.clan.info");
			return;
		}

		if (Objects.requireNonNull(PlayerFileManager.getPlayerClanName(player)).equalsIgnoreCase("none")) {
			UtilsMessage.errorMessage(player, "You are not in a clan!");
			return;
		}

//		open the menu
		ClanInfoMenu menu = new ClanInfoMenu(player, "&7Clan Info", 54);

		menu.setupMenu(PlayerFileManager.getPlayerClanName(player));
		menu.openMenu();

	}


	//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("islanddefender.clan.info");
	}


}

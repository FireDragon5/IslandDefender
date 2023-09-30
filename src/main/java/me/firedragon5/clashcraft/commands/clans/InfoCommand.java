package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import me.firedragon5.clashcraft.menu.clan.ClanInfoMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class InfoCommand {



	//	info clan when they specify a clan
	public static void infoClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "clashcraft.clan.info");
			return;
		}

//		if the clan does not exist
		if (!ClanFolderManager.getFileManager().clanExists(clanName)) {
			UtilsMessage.errorMessage(player, "Clan does not exist!");
			return;
		}

//		open the menu
		ClanInfoMenu menu = new ClanInfoMenu(player, "&a&lClan Info" , 54);
		menu.setupMenu(clanName);
		menu.openMenu();



	}

//	info clan when they are in a clan
	public static void infoClan(Player player){

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "clashcraft.clan.info");
			return;
		}

		if (Objects.requireNonNull(PlayerFileManager.getPlayerClanName(player)).equalsIgnoreCase("none")) {
			UtilsMessage.errorMessage(player, "You are not in a clan!");
			return;
		}

//		open the menu
		ClanInfoMenu menu = new ClanInfoMenu(player, "&a&lClan Info", 54);

		menu.setupMenu(PlayerFileManager.getPlayerClanName(player));
		menu.openMenu();

	}


	//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("clashcraft.clan.info");
	}




}

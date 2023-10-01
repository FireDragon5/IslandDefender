package me.firedragon5.islanddefender.commands.clans;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class ListCommand {

	public static void listClans(Player player) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "islanddefender.clan.list");
			return;
		}

//		[Clans] :
//		1. Clan1
//		2. Clan2
//		etc

		int i = 1;
		UtilsMessage.sendMessage(player, "&a[Clans] :");
		for (String clan : ClanFolderManager.getFileManager().getClanList()) {
			UtilsMessage.sendMessage(player, "&6&l" + i + ". &a" + clan);
		}

	}


	//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("islanddefender.clan.list");
	}


}

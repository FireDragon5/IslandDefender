package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class ListCommand {

	public static void listClans(Player player) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "clashcraft.clan.list");
			return;
		}

		UtilsMessage.sendMessage(player, "Clans: " + ClanFolderManager.getFileManager().getClanList());


	}



//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("clashcraft.clan.list");
	}


}

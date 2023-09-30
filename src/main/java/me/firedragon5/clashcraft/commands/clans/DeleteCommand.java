package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DeleteCommand {


	public static void deleteClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			return;
		}

		if (!isLeader(player, clanName)) {
			return;
		}

		ClanFolderManager.getFileManager().deleteClan(player);

	}


	//	Has perms
	private static boolean hasPermission(Player player) {
		return player.hasPermission("clashcraft.clan.delete");
	}


	//	Is leader
	private static boolean isLeader(Player player, String clanName) {
		return Objects.equals(ClanFolderManager.getFileManager().getClanLeader(clanName), player.getName());
	}


}

package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class DisbandCommand {


	public static void disbandClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			return;
		}

		if (!isLeader(player, clanName)) {
			return;
		}

		ClanFolderManager.getFileManager().deleteClan(player);

		UtilsMessage.sendMessage(player, "&aYou have disbanded your clan");


	}


	//	Has perms
	private static boolean hasPermission(Player player) {
		return player.hasPermission("islanddefender.clan.delete");
	}


	//	Is leader
	private static boolean isLeader(Player player, String clanName) {
		return Objects.equals(ClanFolderManager.getFileManager().getClanLeader(clanName), player.getName());
	}


}

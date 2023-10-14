package me.firedragon5.islanddefender.commands.clans;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LeaveCommand {

//	leave clan

	public static void leaveClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");

			return;
		}


//		If the player is not in a clan, then return
		if (Objects.requireNonNull(PlayerFileManager.getPlayerClanName(player)).equalsIgnoreCase("none")) {
			UtilsMessage.errorMessage(player, "You are not in a clan!");
			return;
		}


		ClanFolderManager.getFileManager().joinClan(clanName, player);

		PlayerFileManager.setPlayerClanName(player, clanName);

	}


	//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("islanddefender.clan.join");
	}


}

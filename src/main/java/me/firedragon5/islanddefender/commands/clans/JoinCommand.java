package me.firedragon5.islanddefender.commands.clans;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class JoinCommand {


//	Join clan

	public static void joinClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "islanddefender.clan.join");
			return;
		}


//		If the player is in a clan, then return
		if (!Objects.requireNonNull(PlayerFileManager.getPlayerClanName(player)).equalsIgnoreCase("none")) {
			UtilsMessage.errorMessage(player, "You are already in a clan!");
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

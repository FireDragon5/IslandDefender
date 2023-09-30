package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LeaveCommand {

//	leave clan

	public static void leaveClan(Player player, String clanName) {

		if (!hasPermission(player)) {
			UtilsMessage.noPermissionMessage(player, "clashcraft.clan.leave");
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
		return player.hasPermission("clashcraft.clan.join");
	}




}

package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CreateCommand {

	public static void createClan(Player player, String clanName, String clanTag) {

		if (!hasPermission(player)) {
			UtilsMessage.errorMessage(player, "&cYou do not have permission to use this command!");
			return;
		}

		//			If the player is in a clan, then return
		if (!Objects.equals(PlayerFileManager.getPlayerClanName(player), "none")) {
			UtilsMessage.errorMessage(player, "&cYou are already in a clan!");
			return;
		}


		ClanFolderManager.getFileManager().addClan(player ,clanName, clanTag);

	}


//	has permission
	private static boolean hasPermission(Player player) {
		return player.hasPermission("clashcraft.clan.create");
	}





}

package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import org.bukkit.entity.Player;

public class KickCommand {


	public static void kickPlayer(Player player, Player target) {

		ClanFolderManager clanManager = ClanFolderManager.getFileManager();

		clanManager.kickPlayer(player, target);

	}
}

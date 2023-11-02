package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class HelpCommand {


	public static void helpClan(Player player) {

//		/clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (player.hasPermission("islanddefender.clan")) {
			UtilsMessage.sendMessage(player, "&c/clan join|leave|info|list|help");
		} else if (player.hasPermission("islanddefender.clan.create")) {
			UtilsMessage.sendMessage(player, "&c/clan create|disband|info|list|help");
		}


	}
}

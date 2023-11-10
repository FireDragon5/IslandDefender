package me.firedragon5.islanddefender.commands.staff.admin.handles;

import org.bukkit.entity.Player;

public class HandleClansCommands {
	public static void handleClanCommands(Player player, String[] strings) {

////		/clans create <clanName>
//		if (strings[1].equalsIgnoreCase("create")) {
//			HandleClanCreateCommand.handleClanCreateCommand(player, strings);
//		}
//
////		/clans delete <clanName>
//		else if (strings[1].equalsIgnoreCase("delete")) {
//			HandleClanDeleteCommand.handleClanDeleteCommand(player, strings);
//		}
//
////		/clans info <clanName>
//		else if (strings[1].equalsIgnoreCase("info")) {
//			HandleClanInfoCommand.handleClanInfoCommand(player, strings);
//		}

//		/clans chat <clanName>
		if (strings[1].equalsIgnoreCase("chat")) {
			HandleClanChatCommand.handleClanChatCommand(player, strings);
		}

	}
}

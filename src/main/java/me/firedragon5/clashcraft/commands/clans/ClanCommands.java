package me.firedragon5.clashcraft.commands.clans;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClanCommands implements CommandExecutor, TabCompleter {


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


		Player player = (Player) sender;


//	 /clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (args.length == 0) {
			player.sendMessage("&c/clans create|join|leave|invite|kick|promote|demote|disband|info|list|help");
			return true;
		}

//		Permissions for clan commands
		if (args[0].equalsIgnoreCase("create")) {

			CreateCommand.createClan(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("join")) {

			JoinCommand.joinClan(player, args[1]);

		}else if (args[0].equalsIgnoreCase("leave")) {

			LeaveCommand.leaveClan(player, args[1]);
		}else if (args[0].equalsIgnoreCase("invite")) {

//			InviteCommand.invitePlayer(player, args[1], args[2]);

		}else if (args[0].equalsIgnoreCase("kick")) {

//			KickCommand.kickPlayer(player, args[1], args[2]);

		}else if (args[0].equalsIgnoreCase("list")) {

			ListCommand.listClans(player);
		}


		return false;
	}


	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			// Add all available commands that match the typed text
			for (String commandName : Arrays.asList("info", "list", "help", "join", "leave", "create", "invite", "kick",
					"promote", "demote", "disband", "delete", "setleader", "setadmin", "setmember")) {
				if (commandName.startsWith(strings[0].toLowerCase()) && commandSender.hasPermission("clashcraft.clan."
						+ commandName)) {
					tabComplete.add(commandName);
				}
			}
		} else if (strings.length == 2) {
			String commandName = strings[0].toLowerCase();
			switch (commandName) {
				case "create":
				case "join":
				case "invite":
				case "delete":
				case "info":
				case "disband":
					tabComplete.add("<clanname>");
					break;

				case "kick":
				case "setadmin":
				case "setmember":
				case "setleader":
				case "demote":
				case "promote":
					tabComplete.add("<playername>");
					break;


			}
		} else if (strings.length == 3 && strings[0].equalsIgnoreCase("create")) {
			// Add suggestions specific to the "create" command with 3 arguments
			tabComplete.add("<clantag>");
		}

		return tabComplete;
	}

}

package me.firedragon5.islanddefender.commands.clans;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
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

			me.firedragon5.islanddefender.commands.clans.CreateCommand.createClan(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("join")) {

			me.firedragon5.islanddefender.commands.clans.JoinCommand.joinClan(player, args[1]);

		} else if (args[0].equalsIgnoreCase("leave")) {

			me.firedragon5.islanddefender.commands.clans.LeaveCommand.leaveClan(player, args[1]);
		} else if (args[0].equalsIgnoreCase("invite")) {

//			InviteCommand.invitePlayer(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("kick")) {

//			KickCommand.kickPlayer(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("list")) {

			me.firedragon5.islanddefender.commands.clans.ListCommand.listClans(player);
		} else if (args[0].equalsIgnoreCase("info")) {

//			if the use is a clan then only info is needs else they need to specify a clan
			if (args.length == 1) {
				me.firedragon5.islanddefender.commands.clans.InfoCommand.infoClan(player);
			} else {
				me.firedragon5.islanddefender.commands.clans.InfoCommand.infoClan(player, args[1]);

			}


		} else if (args[0].equalsIgnoreCase("visibility")) {

			me.firedragon5.islanddefender.commands.clans.VisibilityCommand.setVisibility(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("disband")) {

//			//	Make the player click a message when the player clicks the message then disband the clan
//			UtilsMessage.clickableMessage(player, "&cAre you sure you want to disband your clan? &aClick here" +
//							" to disband your clan",
//					"/clan disband " + args[1]);

//			only disband the clan if the player clicks the message


			me.firedragon5.islanddefender.commands.clans.DisbandCommand.disbandClan(player, args[1]);

		}

		return false;
	}


	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();


		if (strings.length == 1) {
			// Add all available commands that match the typed text
			for (String commandName : Arrays.asList("info", "list", "help", "join", "leave", "create", "invite", "kick",
					"promote", "demote", "disband", "delete", "setleader", "setadmin", "setmember")) {
				if (commandName.startsWith(strings[0].toLowerCase()) && commandSender.hasPermission("islanddefender.clan."
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
				case "disband":
					tabComplete.add("<clanname>");
					break;

				case "info":
//					/clan info <clanName>(List of all the clans)
					for (String clanName : ClanFolderManager.getFileManager().getClanList()) {
						if (clanName.startsWith(strings[1].toLowerCase())) {
							tabComplete.add(clanName);
						}
					}
					break;

				case "visibility":
					tabComplete.add("<public|private>");
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

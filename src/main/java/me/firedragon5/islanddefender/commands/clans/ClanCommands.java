package me.firedragon5.islanddefender.commands.clans;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClanCommands extends FireCommand {


	public ClanCommands() {
		super("clan", new String[]{"clans", "c"},
				"Clan commands",
				"islanddefender.clan");

	}


	@Override
	public void execute(CommandSender sender, String[] args) {

		checkConsole();

		Player player = (Player) sender;

//	 /clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (args.length == 0) {
			UtilsMessage.sendMessage(player, "&c/clans create|join|leave|invite|kick|promote|demote|disband|info|list|help");
			return;
		}


//		Permissions for clan commands
		if (args[0].equalsIgnoreCase("create")) {

//			if all the arguments are not there, then send the player a message
			if (args.length != 3) {
				UtilsMessage.sendMessage(player, "&c/clan create <clanName> <clanTag>");
				return;
			}

			CreateCommand.createClan(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("join")) {

			JoinCommand.joinClan(player, args[1]);

		} else if (args[0].equalsIgnoreCase("leave")) {

			LeaveCommand.leaveClan(player, args[1]);
		} else if (args[0].equalsIgnoreCase("invite")) {

//			InviteCommand.invitePlayer(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("kick")) {

//			KickCommand.kickPlayer(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("list")) {

			ListCommand.listClans(player);
		} else if (args[0].equalsIgnoreCase("info")) {

//			if the use is a clan then only info is needs else they need to specify a clan
			if (args.length == 1) {
				InfoCommand.infoClan(player);
			} else {
				InfoCommand.infoClan(player, args[1]);

			}


		} else if (args[0].equalsIgnoreCase("visibility")) {

			VisibilityCommand.setVisibility(player, args[1], args[2]);

		} else if (args[0].equalsIgnoreCase("disband")) {

//			//	Make the player click a message when the player clicks the message then disband the clan
//			UtilsMessage.clickableMessage(player, "&cAre you sure you want to disband your clan? &aClick here" +
//							" to disband your clan",
//					"/clan disband " + args[1]);

//			only disband the clan if the player clicks the message


			DisbandCommand.disbandClan(player, args[1]);

		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
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

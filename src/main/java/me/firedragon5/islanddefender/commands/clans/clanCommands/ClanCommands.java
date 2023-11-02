package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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

			LeaveCommand.leaveClan(player);


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

		Player player = (Player) commandSender;

		// Check if the player has the basic clan permission
		if (player.hasPermission("islanddefender.clan")) {
			switch (strings.length) {
				case 1:
					tabComplete.add("join");

//					if player is in clan
					if (PlayerFileManager.isInClan(player)) {
						tabComplete.add("leave");
					}

					tabComplete.add("info");
					tabComplete.add("list");
					tabComplete.add("help");
					return tabComplete;
				case 2:
					if (strings[0].equalsIgnoreCase("join") || strings[0].equalsIgnoreCase("info")) {
						tabComplete.addAll(ClanFolderManager.getFileManager().getClanList());
						return tabComplete;
					}
			}
		}

		// Check if the player has the permission to create and disband clans
		if (player.hasPermission("islanddefender.clan.create")) {
			if (strings.length == 1) {
				tabComplete.add("create");
				tabComplete.add("disband");
			}
		}

		// Check if the player is a leader in the clan
		if (ClanFolderManager.getFileManager().isLeader(player)) {
			switch (strings.length) {
				case 1:
					tabComplete.add("invite");
					tabComplete.add("kick");
					tabComplete.add("promote");
					tabComplete.add("demote");
					return tabComplete;
				case 2:
					if (strings[0].equalsIgnoreCase("invite") || strings[0].equalsIgnoreCase("kick") ||
							strings[0].equalsIgnoreCase("promote") || strings[0].equalsIgnoreCase("demote")) {
						for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							tabComplete.add(onlinePlayer.getName());
						}
					}
					return tabComplete;
			}
		}

		return tabComplete;
	}

}

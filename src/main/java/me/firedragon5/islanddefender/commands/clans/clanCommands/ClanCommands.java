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
import java.util.Objects;


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

			if (player.hasPermission("islanddefender.clan")) {
				UtilsMessage.sendMessage(player, "&c/clan join|leave|info|list|help");
			} else if (player.hasPermission("islanddefender.clan.create")) {
				UtilsMessage.sendMessage(player, "&c/clan create|disband|info|list|help");
//				if the player is the leader of the clan
			} else if (ClanFolderManager.getFileManager().isLeader(player)) {
				UtilsMessage.sendMessage(player, "&c/clan invite|kick|promote|demote|info|list|help");
			}
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

			Player target = Bukkit.getPlayer(args[1]);


			InviteCommand.invitePlayer(player, target);

		} else if (args[0].equalsIgnoreCase("kick")) {

			Player target = Bukkit.getPlayer(args[1]);


			KickCommand.kickPlayer(player, target);

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
			if (args.length == 1) {
				// Player typed "/clan disband" without "yes" or "no"
				DisbandCommand.disbandClan(player);
			} else if (args.length == 2) {
				String confirmationArg = args[1].toLowerCase();
				if (confirmationArg.equals("yes")) {
					// Player confirmed to disband the clan
					DisbandCommand.confirmDisband(player, true);
				} else if (confirmationArg.equals("no")) {
					// Player canceled the disband action
					DisbandCommand.confirmDisband(player, false);
				} else {
					// Invalid confirmation, show usage or error message
					// You can implement this part as needed

					UtilsMessage.errorMessage(player, "&cInvalid confirmation. Please type &a/clan disband yes &cor &a/clan disband no");

//					Send a message to all the clan members that the clan has been disbanded
					for (Player clanMember : Bukkit.getOnlinePlayers()) {

						if (PlayerFileManager.isInClan(clanMember)) return;
						if (Objects.requireNonNull(PlayerFileManager.getPlayerClanName(clanMember))
								.equalsIgnoreCase(PlayerFileManager.getPlayerClanName(player))) {
							UtilsMessage.offlineMessage(clanMember, "&cThe clan has been disbanded");
						}


					}

				}
			}
		} else if (args[0].equalsIgnoreCase("help")) {

			HelpCommand.helpClan(player);

		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();

		Player player = (Player) commandSender;

		// Check if the player has the basic clan permission
		if (player.hasPermission("islanddefender.clan") || player.isOp()) {
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
		if (player.hasPermission("islanddefender.clan.create") || player.isOp()) {
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

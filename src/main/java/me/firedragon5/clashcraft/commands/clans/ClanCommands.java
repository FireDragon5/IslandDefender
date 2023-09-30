package me.firedragon5.clashcraft.commands.clans;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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


	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();

//		Commands all the player has: /clan info|list|help|join|leave|create(Needs permission)
//		Commands only the leader has: /clan invite|kick|promote|demote|disband
//		Commands only the admin has: /clan delete|setleader|setadmin|setmember

		if (strings.length == 1) {
			tabComplete.add("info");
			tabComplete.add("list");
			tabComplete.add("help");
			tabComplete.add("join");
			tabComplete.add("leave");
			tabComplete.add("create");
			if (commandSender.hasPermission("clashcraft.clan.create")) {
				tabComplete.add("create");
			}
			if (commandSender.hasPermission("clashcraft.clan.join")) {
				tabComplete.add("join");
			}
			if (commandSender.hasPermission("clashcraft.clan.leave")) {
				tabComplete.add("leave");
			}
			if (commandSender.hasPermission("clashcraft.clan.invite")) {
				tabComplete.add("invite");
			}
			if (commandSender.hasPermission("clashcraft.clan.kick")) {
				tabComplete.add("kick");
			}
			if (commandSender.hasPermission("clashcraft.clan.promote")) {
				tabComplete.add("promote");
			}
			if (commandSender.hasPermission("clashcraft.clan.demote")) {
				tabComplete.add("demote");
			}
			if (commandSender.hasPermission("clashcraft.clan.disband")) {
				tabComplete.add("disband");
			}
			if (commandSender.hasPermission("clashcraft.clan.delete")) {
				tabComplete.add("delete");
			}
			if (commandSender.hasPermission("clashcraft.clan.setleader")) {
				tabComplete.add("setleader");
			}
			if (commandSender.hasPermission("clashcraft.clan.setadmin")) {
				tabComplete.add("setadmin");
			}
			if (commandSender.hasPermission("clashcraft.clan.setmember")) {
				tabComplete.add("setmember");
			}
			return tabComplete;
		}
		return tabComplete;
	}
}

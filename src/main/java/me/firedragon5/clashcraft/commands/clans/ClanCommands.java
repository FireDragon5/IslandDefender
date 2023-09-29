package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
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
			if (sender.hasPermission("clashcraft.clan.create")) {

				ClanFolderManager.getFileManager().addClan(args[1]);

			} else {
				player.sendMessage( "&cYou do not have permission to use this command!");
			}
			return true;
		} else if (args[0].equalsIgnoreCase("join")) {
			if (sender.hasPermission("clashcraft.clan.join")) {

				ClanFolderManager.getFileManager().joinClan(args[1], player);

				PlayerFileManager.setPlayerClanName(player, args[1]);

			}else {
				player.sendMessage("&cYou do not have permission to use this command!");
			}


		}else if (args[0].equalsIgnoreCase("leave")) {
			if (sender.hasPermission("clashcraft.clan.leave")) {

				ClanFolderManager.getFileManager().leaveClan(player);

			} else {
				player.sendMessage( "&cYou do not have permission to use this command!");
			}
		}


		return false;
	}


	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List <String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			tabComplete.add("create");
			tabComplete.add("join");
			tabComplete.add("leave");
			tabComplete.add("invite");
			tabComplete.add("kick");
			tabComplete.add("promote");
			tabComplete.add("demote");
			tabComplete.add("disband");
			tabComplete.add("info");
			tabComplete.add("list");
			tabComplete.add("help");
		}



		return tabComplete;
	}
}

package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanCommands implements CommandExecutor {


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


		Player player = (Player) sender;


//	 /clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (args.length == 0) {
			UtilsMessage.sendMessage(player, "&c/clan create|join|leave|invite|kick|promote|demote|disband|info|list|help");
			return true;
		}

//		Permissions for clan commands
		if (args[0].equalsIgnoreCase("create")) {
			if (sender.hasPermission("clashcraft.clan.create")) {

				ClanFolderManager.getFileManager().addClan(args[1]);


			} else {
				UtilsMessage.sendMessage(player, "&cYou do not have permission to use this command!");
			}
			return true;
		} else if (args[0].equalsIgnoreCase("join")) {
			if (sender.hasPermission("clashcraft.clan.join")) {

				ClanFolderManager.getFileManager().joinClan(args[1], player);

				PlayerFileManager.setPlayerClanName(player, args[1]);

			}else {
				UtilsMessage.sendMessage(player, "&cYou do not have permission to use this command!");
			}


		}else if (args[0].equalsIgnoreCase("leave")) {
			if (sender.hasPermission("clashcraft.clan.leave")) {

				ClanFolderManager.getFileManager().leaveClan(player);

			} else {
				UtilsMessage.sendMessage(player, "&cYou do not have permission to use this command!");
			}
		}


		return true;
	}
}

package me.firedragon5.clashcraft.commands.clans;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClanCommands implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

//	 /clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (args.length == 0) {
			UtilsMessage.sendMessage(sender, "&c/clan create|join|leave|invite|kick|promote|demote|disband|info|list|help");
			return true;
		}

//		Permissions for clan commands
		if (args[0].equalsIgnoreCase("create")) {
			if (sender.hasPermission("clashcraft.clan.create")) {

			} else {
				UtilsMessage.sendMessage(sender, "&cYou do not have permission to use this command!");
			}
			return true;
		}











		return false;
	}
}

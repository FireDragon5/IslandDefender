package me.firedragon5.clashcraft.commands.clans;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClanCommands implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

//	 /clan create|join|leave|invite|kick|promote|demote|disband|info|list|help
		if (strings.length == 0) {
			commandSender.sendMessage("§c/clan create|join|leave|invite|kick|promote|demote|disband|info|list|help");
			return true;
		}










		return false;
	}
}

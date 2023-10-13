package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StaffCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


//		If the sender is not a player, return
		if (!(sender instanceof Player player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {
			if (player.hasPermission("islanddefender.staff")) {
				UtilsMessage.sendMessage(player, "&bStaff Chat Members:");

//					Show all the players online with this permission
				for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
					if (onlinePlayer.hasPermission("islanddefender.staffchat")) {
						UtilsMessage.sendMessage(player, "&a- &7" + onlinePlayer.getName());
					}
				}
			} else {
				UtilsMessage.noPermissionMessage(player, "islanddefender.staffchat.list");
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("help")) {
			UtilsMessage.sendMessage(player, "&bStaff Chat &7Commands");
			UtilsMessage.sendMessage(player, "&b/sc <message> &7- Send a message to the staff chat");
			UtilsMessage.sendMessage(player, "&b/s list &7- List all the staff chat members");
			UtilsMessage.sendMessage(player, "&b/s help &7- Show this message");

			return true;
		}

//		reload
		if (args[0].equalsIgnoreCase("reload")) {
			if (player.hasPermission("islanddefender.staff")) {
				UtilsMessage.sendMessage(player, "&aReloading the config...");
				ConfigManger.getFileManager().reloadConfig();
				UtilsMessage.sendMessage(player, "&aReloaded the config!");
			} else {
				UtilsMessage.noPermissionMessage(player, "islanddefender.staff");
			}
			return true;
		}


		return false;
	}


	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();

//		Dont show the commands if the player is not a staff member
		if (commandSender instanceof Player player) {
			if (!player.hasPermission("islanddefender.staffchat")) {
				return tabComplete;
			}
		}

		if (commandSender instanceof Player player) {
			if (strings.length == 1) {

				if (player.hasPermission("islanddefender.staffchat")) {
					tabComplete.add("help");
					tabComplete.add("list");
					tabComplete.add("reload");
				}

			}
		}

		return tabComplete;

	}


}

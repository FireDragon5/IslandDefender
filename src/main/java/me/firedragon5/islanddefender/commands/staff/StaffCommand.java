package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffCommand extends FireCommand {


	public StaffCommand() {
		super("staff",
				new String[]{"s"},
				"IslandDefender Staff Commands",
				"islanddefender.staff");


	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		Player player = (Player) sender;
		checkConsole();


		if (args.length == 0) {
			UtilsMessage.sendMessage(player, "&bStaff Chat &7Commands");
			UtilsMessage.sendMessage(player, "&b/sc <message> &7- Send a message to the staff chat");
			UtilsMessage.sendMessage(player, "&b/s list &7- List all the staff chat members");
			UtilsMessage.sendMessage(player, "&b/s help &7- Show this message");
			UtilsMessage.sendMessage(player, "&b/s reload &7- Reload the config");

			return;

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
				UtilsMessage.errorMessage(player, "You don't have permission to use this command!");

			}

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
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
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
					tabComplete.add("list");
					tabComplete.add("reload");
				}

			}
		}

		return tabComplete;

	}
}

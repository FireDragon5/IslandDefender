package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand extends FireCommand {

	public AdminCommand() {
		super("admin", new String[]{"a"}, "Admin chat commands", "islanddefender.admin");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		checkConsole();
		Player player = (Player) commandSender;

		ConfigManger configManager = ConfigManger.getFileManager();

		// Admin chat command
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
			return;
		}

		if (strings.length == 0) {
			UtilsMessage.errorMessage(player, "&a&lAdmin Chat Commands:");
			UtilsMessage.sendMessage(player, "&a/admin reload - Reload the config.");
		} else if (strings[0].equalsIgnoreCase("reload")) {
			if (player.hasPermission("islanddefender.admin")) {
				UtilsMessage.sendMessage(player, "&aReloading the config...");
				configManager.reloadConfig();
				UtilsMessage.sendMessage(player, "&aReloaded the config!");
			} else {
				UtilsMessage.errorMessage(player, "&cYou don't have permission to use this command!");
			}
		} else {
			// Combine the arguments into a single message
			String message = String.join(" ", strings);
			UtilsMessage.sendMessage(player, configManager.getAdminChatFormat()
					.replace("%player%", player.getName())
					.replace("%message%", message));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();
		if (strings.length == 1) {
			tabComplete.add("reload");
		}
		return tabComplete;
	}
}

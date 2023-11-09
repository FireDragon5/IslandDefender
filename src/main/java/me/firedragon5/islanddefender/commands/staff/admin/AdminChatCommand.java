package me.firedragon5.islanddefender.commands.staff.admin;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminChatCommand extends FireCommand {

	public AdminChatCommand() {
		super("adminchat", new String[]{"ac"}, "Admin chat and balance commands", "islanddefender.admin");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		checkConsole();

		Player player = (Player) commandSender;
		ConfigManger configManager = ConfigManger.getFileManager();

		// Combine the arguments into a single message for admin chat
		String message = String.join(" ", strings);
		for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
			if (onlinePlayer.hasPermission("islanddefender.admin")) {
				UtilsMessage.sendMessage(onlinePlayer, configManager.getAdminChatFormat()
						.replace("%player%", player.getName())
						.replace("%message%", message));
			}
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

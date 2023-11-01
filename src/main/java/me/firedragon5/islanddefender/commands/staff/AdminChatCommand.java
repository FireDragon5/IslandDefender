package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminChatCommand extends FireCommand {


	public AdminChatCommand() {
		super("adminchat", new String[]{"ac"},
				"Admin chat commands",
				"islanddefender.admin");


	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();
		Player player = (Player) commandSender;

		ConfigManger configManager = ConfigManger.getFileManager();

//		Admin chat command
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
			return;
		}

		if (strings.length == 0) {
			UtilsMessage.errorMessage(player, "&a&lAdmin Chat Commands");
		} else {
			StringBuilder message = new StringBuilder();
			for (String arg : strings) {
				message.append(arg).append(" ");
			}
			UtilsMessage.sendMessage(player, configManager.getAdminChatFormat()
					.replace("%player%", player.getName())
					.replace("%message%", message.toString()));
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class StaffChatCommand extends FireCommand {

	public StaffChatCommand() {
		super("staffchat", new String[]{"sc"},
				"Staff chat commands",
				"islanddefender.staff");


	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		checkConsole();

		ConfigManger configManager = ConfigManger.getFileManager();

//		/sc <message>
		if (sender instanceof Player player) {

			if (!player.hasPermission("islanddefender.staff")) {
				UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
				return;
			}

			if (args.length == 0) {
				UtilsMessage.errorMessage(player, "&a&lStaff Chat Commands");
			} else {
				StringBuilder message = new StringBuilder();
				for (String arg : args) {
					message.append(arg).append(" ");
				}
				UtilsMessage.sendMessage(player, configManager.getStaffChatFormat()
						.replace("%player%", player.getName())
						.replace("%message%", message.toString()));
			}

		} else {
			sender.sendMessage(Objects.requireNonNull(Utils.chat("&cYou must be a player to run this command!")));
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffCommand extends FireCommand {

	public StaffCommand() {
		super("staffcommands", new String[]{"sc"},
				"Staff chat and staff list commands",
				"islanddefender.staff");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		checkConsole();

		if (sender instanceof Player player) {
			ConfigManger configManager = ConfigManger.getFileManager();

			if (args.length == 0) {
				UtilsMessage.sendMessage(player, "&bStaff Commands:");
				UtilsMessage.sendMessage(player, "&b/sc <message> &7- Send a message to the staff chat");
				UtilsMessage.sendMessage(player, "&b/staff list &7- List all the staff chat members");
				UtilsMessage.sendMessage(player, "&b/staff help &7- Show this message");
			} else {
				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("islanddefender.staff")) {
						UtilsMessage.sendMessage(player, "&bStaff Chat Members:");
						for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
							if (onlinePlayer.hasPermission("islanddefender.staffchat")) {
								UtilsMessage.sendMessage(player, "&a- &7" + onlinePlayer.getName());
							}
						}
					} else {
						UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
					}
				} else {
					// Combine the arguments into a single message
					String message = String.join(" ", args);
					UtilsMessage.sendMessage(player, configManager.getStaffChatFormat()
							.replace("%player%", player.getName())
							.replace("%message%", message));
				}
			}
		} else {
			sender.sendMessage(Objects.requireNonNull(Utils.chat("&cYou must be a player to run this command!")));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();
		if (strings.length == 1) {
			tabComplete.add("list");
		}
		return tabComplete;
	}
}

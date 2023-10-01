package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.Utils;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StaffChatCommand implements CommandExecutor {


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

//		/sc <message>
		if (sender instanceof Player player) {

			if (!player.hasPermission("islanddefender.staff")) {
				UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
				return true;
			}

			if (args.length == 0) {
				UtilsMessage.errorMessage(player, "&a&lStaff Chat Commands");
				return true;
			} else {
				StringBuilder message = new StringBuilder();
				for (String arg : args) {
					message.append(arg).append(" ");
				}
				UtilsMessage.sendMessage(player, "&a&lStaff Chat &8&l< "
						+ player.getName() + " &8&l> &7" + message);
			}

		} else {
			sender.sendMessage(Objects.requireNonNull(Utils.chat("&cYou must be a player to run this command!")));
		}

		return false;
	}
}


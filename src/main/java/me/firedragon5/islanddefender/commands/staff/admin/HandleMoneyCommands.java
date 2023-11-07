package me.firedragon5.islanddefender.commands.staff.admin;

import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class HandleMoneyCommands {


	public static void handleMoneyCommands(Player player, String[] strings) {
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
			return;
		}

		if (strings.length == 1) {
			UtilsMessage.errorMessage(player, "&a&lAdmin Money Commands:");
			UtilsMessage.sendMessage(player, "&a/admin money balance <player> - Check player's balance.");
			UtilsMessage.sendMessage(player, "&a/admin money give <player> <amount> - Give money to a player.");
			UtilsMessage.sendMessage(player, "&a/admin money remove <player> <amount> - Remove money from a player.");
		} else if (strings.length >= 3) {
			String operation = strings[1].toLowerCase();
			if (operation.equals("balance")) {
				CheckPlayerBalance.checkPlayerBalance(player, strings);
			} else if (operation.equals("give") || operation.equals("remove")) {
				PerformMoneyTransaction.performMoneyTransaction(player, strings, operation);
			}
		}
	}

}

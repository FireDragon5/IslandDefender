package me.firedragon5.islanddefender.commands.staff.admin.handles;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HandleMoneyTransaction {

	public static void performMoneyTransaction(Player player, String[] strings, String operation) {
		if (strings.length < 4) {
			UtilsMessage.errorMessage(player, "&cUsage: /admin money " + operation + " <player> <amount>");
			return;
		}

		String playerName = strings[2];
		Player target = Bukkit.getPlayer(playerName);

		if (target == null) {
			UtilsMessage.sendMessage(player, "&cThat player does not exist or is offline");
			return;
		}

		int amount;
		try {
			amount = Integer.parseInt(strings[3]);
		} catch (NumberFormatException e) {
			UtilsMessage.errorMessage(player, "&cInvalid amount. Please use a number.");
			return;
		}

		if (operation.equals("give")) {
			PlayerFileManager.addPlayerCoins(target, amount);

			UtilsMessage.sendMessage(player, "&aYou have given &e" + target.getName() + " &a" + amount + " coins");
			UtilsMessage.sendMessage(target, "&aYou have been given &e" + amount + " coins");
		} else if (operation.equals("remove")) {
			if (amount > PlayerFileManager.getPlayerCoins(target)) {
				UtilsMessage.sendMessage(player, "&c" + target.getName() + " does not have enough coins");
				return;
			}

			PlayerFileManager.removePlayerCoins(target, amount);

			UtilsMessage.sendMessage(player, "&aYou have removed &e" + amount + " coins &afrom &e" + target.getName());
			UtilsMessage.sendMessage(target, "&aYou have had &e" + amount + " coins &aremoved");
		}
	}

}

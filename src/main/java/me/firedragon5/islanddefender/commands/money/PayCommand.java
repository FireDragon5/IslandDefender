package me.firedragon5.islanddefender.commands.money;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import me.firedraong5.firesapi.utils.UtilsTools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PayCommand {

	public static void payPlayer(Player player, String target, String amount) {

		Player targetPlayer = Bukkit.getPlayer(target);


		if (targetPlayer == null) {
			UtilsMessage.errorMessage(player, "&cThat player is not online");
			return;
		}

		if (targetPlayer == player) {
			UtilsMessage.errorMessage(player, "You can not pay yourself");
			return;
		}

		if (!UtilsTools.isInt(amount)) {
			UtilsMessage.errorMessage(player, "That is not a number");
			return;
		}

		int amountInt = Integer.parseInt(amount);

		if (amountInt <= 0) {
			UtilsMessage.errorMessage(player, "You can not pay that amount");
			return;
		}

		if (amountInt > ConfigManger.getFileManager().getMaxCoinPayAmount()) {
			UtilsMessage.sendMessage(player, "&cYou can not pay that amount");
			return;
		}

		if (amountInt > PlayerFileManager.getPlayerCoins(player)) {
			UtilsMessage.errorMessage(player, "You do not have enough coins");
			return;
		}

		PlayerFileManager.removePlayerCoins(player, amountInt);
		PlayerFileManager.addPlayerCoins(targetPlayer, amountInt);

		UtilsMessage.sendMessage(player, "&aYou have paid &e" + targetPlayer.getName() + " &a" + amountInt + " coins");
		UtilsMessage.sendMessage(targetPlayer, "&aYou have been paid &e" + amountInt + " coins &aby &e" + player.getName());

	}


}

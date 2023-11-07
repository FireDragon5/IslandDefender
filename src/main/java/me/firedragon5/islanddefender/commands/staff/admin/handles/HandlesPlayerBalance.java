package me.firedragon5.islanddefender.commands.staff.admin.handles;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HandlesPlayerBalance {


	public static void checkPlayerBalance(Player player, String[] strings) {
		String playerName = strings[2];
		Player target = Bukkit.getOfflinePlayer(playerName).getPlayer();

		if (target == null || target.equals("")) {
			UtilsMessage.sendMessage(player, "&cThat player does not exist or is offline");
			return;
		}

		int coins = PlayerFileManager.getPlayerCoins(target);
		int crystals = PlayerFileManager.getPlayerCrystals(target);
		int darkCrystals = PlayerFileManager.getPlayerDarkCrystals(target);

		UtilsMessage.sendMessage(player, "&a================== " + target.getName() + " ==================");
		UtilsMessage.sendMessage(player, "&aCoins: &e" + coins);
		UtilsMessage.sendMessage(player, "&aCrystals: &e" + crystals);
		UtilsMessage.sendMessage(player, "&aDark Crystal: &e" + darkCrystals);
		UtilsMessage.sendMessage(player, "&a====================================================");
	}


}

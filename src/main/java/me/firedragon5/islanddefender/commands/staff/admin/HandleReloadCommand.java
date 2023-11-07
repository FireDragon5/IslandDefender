package me.firedragon5.islanddefender.commands.staff.admin;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.kits.KitsFileManager;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class HandleReloadCommand {

	public static void handleReloadCommand(Player player, String reloadType){
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "&cYou are not a staff member!");
			return;
		}


//		config
//				mines
//				ranks
//				kits
//				sells
//				shop
//		all

		ConfigManger configManager = ConfigManger.getFileManager();
		KitsFileManager kitsFileManager = KitsFileManager.getFileManager();
		MineFileManager mineFileManager = MineFileManager.getFileManager();
		SellFileManager sellFileManager = SellFileManager.getFileInstance();
		RankFileManager rankFileManager = RankFileManager.getFileManager();


		if (reloadType.equalsIgnoreCase("config")) {
			configManager.reloadConfig();
			UtilsMessage.sendMessage(player, "&aConfig reloaded!");
		} else if (reloadType.equalsIgnoreCase("mines")) {
			mineFileManager.reloadMineConfig();
			UtilsMessage.sendMessage(player, "&aMines reloaded!");
		} else if (reloadType.equalsIgnoreCase("ranks")) {
			rankFileManager.reloadRankConfig();
			UtilsMessage.sendMessage(player, "&aRanks reloaded!");
		} else if (reloadType.equalsIgnoreCase("kits")) {
			kitsFileManager.reloadKitsConfig();
			UtilsMessage.sendMessage(player, "&aKits reloaded!");
		} else if (reloadType.equalsIgnoreCase("sells")) {
			sellFileManager.reloadSellConfig();
			UtilsMessage.sendMessage(player, "&aSells reloaded!");
		} else if (reloadType.equalsIgnoreCase("shop")) {
			UtilsMessage.sendMessage(player, "&aShop reloaded!");
		} else if (reloadType.equalsIgnoreCase("all")) {
			configManager.reloadConfig();
			mineFileManager.reloadMineConfig();
			rankFileManager.reloadRankConfig();
			kitsFileManager.reloadKitsConfig();
			sellFileManager.reloadSellConfig();
			
			UtilsMessage.sendMessage(player, "&aAll configs reloaded!");
		} else {
			UtilsMessage.errorMessage(player, "&cInvalid reload type!");
		}

	}

}

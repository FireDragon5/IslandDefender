package me.firedragon5.islanddefender.commands.ranks;

import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


//		Check commandSender is a player
		if (!(sender instanceof Player player)) {
			UtilsMessage.sendMessage(sender, "&cYou must be a player to use this command");
			return false;
		}


		int rankMenuSize = RankFileManager.getFileManager().getMenuSize();

//		/rank this will open the rank menu
		if (args.length == 0) {
			RankMenu rankMenu = new RankMenu(player, "&7Ranks", rankMenuSize);
			rankMenu.setupMenu();
			rankMenu.openMenu();
			return true;
		}

//		/rank reload this will reload the ranks.yml file

		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.sendMessage(player, "&cYou do not have permission to use this command");
			return false;
		} else {

			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					UtilsMessage.sendMessage(player, "&aRank config reloaded");
					RankFileManager.getFileManager().reloadRankConfig();
					return true;
				}
			}
		}


		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

		List<String> tabComplete = new ArrayList<>();

//		rank reload needs to be a permission
		if (sender.hasPermission("islanddefender.admin")) {
			if (args.length == 1) {
				tabComplete.add("reload");
			}
		}

		return tabComplete;
	}
}

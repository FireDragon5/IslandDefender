package me.firedragon5.islanddefender.commands.ranks;

import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


//		Check commandSender is a player
		if (!(sender instanceof Player player)) {
			UtilsMessage.sendMessage(sender, "&cYou must be a player to use this command");
			return false;
		}


//		/rank this will open the rank menu
		if (args.length == 0) {
			RankMenu rankMenu = new RankMenu(player, "&bRanks", 27);
			rankMenu.setupMenu();
			rankMenu.openMenu();
			return true;
		}


		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


		return null;
	}
}

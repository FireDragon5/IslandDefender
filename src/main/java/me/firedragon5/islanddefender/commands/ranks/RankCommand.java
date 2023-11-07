package me.firedragon5.islanddefender.commands.ranks;

import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import me.firedragon5.islanddefender.menu.ranks.RankMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RankCommand extends FireCommand {


	public RankCommand() {
		super("rank", new String[]{"ranks", "r"},
				"Rank commands",
				"islanddefender.ranks");

	}


	@Override
	public void execute(CommandSender sender, String[] args) {

		Player player = (Player) sender;

		checkConsole();

		int rankMenuSize = RankFileManager.getFileManager().getMenuSize();

//		/rank this will open the rank menu
		if (args.length == 0) {
			RankMenu rankMenu = new RankMenu(player, "&7Ranks", rankMenuSize);
			rankMenu.setupMenu();
			rankMenu.openMenu();
			return;
		}

//		/rank reload this will reload the ranks.yml file

		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.sendMessage(player, "&cYou do not have permission to use this command");
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return null;
	}
}

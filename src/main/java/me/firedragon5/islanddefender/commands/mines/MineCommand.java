package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
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

public class MineCommand implements CommandExecutor, TabCompleter {


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

		if (!(sender instanceof Player player)) {
			sender.sendMessage("You must be a player to use this command!");
			return true;
		}

//      All players:
//		/mine (this will open the gui)
		if (args.length == 0) {
			MineMenu mineMenu = new MineMenu(player, "&bMines", 54);
			mineMenu.setupMenu();
			mineMenu.openMenu();
			return true;
		}


		//		Admins:
//		/mine create <name>
//		/mine delete <name>
//		/mine reload
		if (player.hasPermission("islanddefender.mine.admin")) {

			if (args[0].equalsIgnoreCase("create")) {
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.createMine(player, args[1]);
			} else if (args[0].equalsIgnoreCase("delete")) {
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.deleteMine(args[1]);
			} else if (args[0].equalsIgnoreCase("reload")) {

				UtilsMessage.sendMessage(player, "&aReloading the mines config!");
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.reloadMineConfig();
			}

		} else {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
		}


		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();

//		All users perm : islanddefender.mine
//		Admins perm : islanddefender.mine.admin

//		/mine create <name>
//		/mine delete <name>

		if (commandSender instanceof Player player) {
			if (player.hasPermission("islanddefender.mine.admin")) {
				if (strings.length == 1) {
					tabComplete.add("create");
					tabComplete.add("delete");
					tabComplete.add("reload");
				} else if (strings.length == 2) {
					tabComplete.add("<name>");
				}
			}
		}


		return tabComplete;
	}
}

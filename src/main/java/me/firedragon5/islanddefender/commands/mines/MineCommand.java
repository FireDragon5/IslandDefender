package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
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
			if (player.hasPermission("islanddefender.mine")) {

				MineMenu mineMenu = new MineMenu(player, "&bMines", 54);
				mineMenu.setupMenu();
				mineMenu.openMenu();


			}
			return true;
		}


		//		Admins:
//		/mine create <name>
//		/mine delete <name>
		if (args[0].equalsIgnoreCase("create")) {
			if (player.hasPermission("islanddefender.mine.admin")) {

				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.createMine(player, args[1]);

			}
		} else if (args[0].equalsIgnoreCase("delete")) {
			if (player.hasPermission("islanddefender.mine.admin")) {

				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.deleteMine(args[1]);

			}
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
				} else if (strings.length == 2) {
					tabComplete.add("<name>");
				}
			}
		}


		return tabComplete;
	}
}

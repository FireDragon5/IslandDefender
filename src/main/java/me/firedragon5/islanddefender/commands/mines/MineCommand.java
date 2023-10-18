package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MineCommand extends FireCommand {

	public MineCommand() {
		super("mine", new String[]{"tiers", "tier", "mines"},
				"Open the mines menu", "islanddefender.mine");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		Player player = (Player) sender;
		checkConsole();

		int menuSize = MineFileManager.getFileManager().getMenuSize();

//      All players:
//		/mine (this will open the gui)
		if (args.length == 0) {
			MineMenu mineMenu = new MineMenu(player, "&7Mines", menuSize);
			mineMenu.setupMenu();
			mineMenu.openMenu();
			return;
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

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

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

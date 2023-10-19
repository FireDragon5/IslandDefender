package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedragon5.islanddefender.menu.mines.MineMenu;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

package me.firedragon5.islanddefender.commands.kits;

import me.firedragon5.islanddefender.menu.kits.KitsMenu;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KitCommand extends FireCommand {

	public KitCommand() {
		super("kit", new String[]{"kits", "k"},
				"Kits commands",
				"islanddefender.kits");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();

//		kit opens the menu
		Player player = (Player) commandSender;

		if (strings.length == 0) {
			KitsMenu menu = new KitsMenu(player, "&7Kits", 54);
			menu.setupMenu();
			menu.openMenu();
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		return null;
	}
}

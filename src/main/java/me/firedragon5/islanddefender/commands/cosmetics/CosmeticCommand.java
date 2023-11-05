package me.firedragon5.islanddefender.commands.cosmetics;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.menu.cosmetic.CosmeticMenu;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CosmeticCommand extends FireCommand {

	public CosmeticCommand() {
		super("cosmetic", new String[]{"c"},
				"Cosmetic commands",
				"islanddefender.cosmetic");

	}
	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();

		Player player = (Player) commandSender;

		CosmeticMenu cosmeticMenu = new CosmeticMenu(player, Utils.chat("&7Cosmetics"), 27);



	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

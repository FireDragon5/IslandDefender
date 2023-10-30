package me.firedragon5.islanddefender.commands.shop;

import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ShopCommands extends FireCommand {

	public ShopCommands() {
		super("shop", new String[]{"shop"},
				"Shop commands",
				"islanddefender.shop");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {


//		/shop this will open a shop where the player can buy items


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

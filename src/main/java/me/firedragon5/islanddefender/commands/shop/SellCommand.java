package me.firedragon5.islanddefender.commands.shop;

import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedragon5.islanddefender.menu.shop.SellMenu;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SellCommand extends FireCommand {


	public SellCommand() {
		super("sell", new String[]{"sell"},
				"Sell commands",
				"islanddefender.sell");
	}


	@Override
	public void execute(CommandSender commandSender, String[] args) {

		checkConsole();

		Player player = (Player) commandSender;


		if (args.length == 0) {
			// /sell will open a gui for the player to sell items
			SellMenu sellMenu = new SellMenu(player, "&7Sell Menu", SellFileManager.getFileInstance().getMenuSize());
			sellMenu.setup();
			sellMenu.openMenu();
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

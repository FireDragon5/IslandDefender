package me.firedragon5.islanddefender.commands.shop;

import me.firedragon5.islanddefender.filemanager.shop.SellFileManager;
import me.firedragon5.islanddefender.menu.shop.SellMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
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
			SellMenu sellMenu = new SellMenu(player, "&7Sell Menu", SellFileManager.getInstance().getMenuSize());
			sellMenu.setup();
			sellMenu.openMenu();
		} else if (args[0].equalsIgnoreCase("reload")) {

//			if player does not have permission
			if (!player.hasPermission("islanddefender.admin")) {
				UtilsMessage.sendMessage(player, "&cYou do not have permission to do that");
				return;
			}

			// /sell reload will reload the config
			SellFileManager.getInstance().reloadSellConfig();
			UtilsMessage.sendMessage(player, "&aSell config reloaded");
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

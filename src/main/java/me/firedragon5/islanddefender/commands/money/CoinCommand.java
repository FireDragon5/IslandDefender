package me.firedragon5.islanddefender.commands.money;

import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoinCommand extends FireCommand {


	public CoinCommand() {
		super("coin", new String[]{"coins", "c", "money", "coin"},
				"Coin commands",
				"islanddefender.coin");
	}


	@Override
	public void execute(CommandSender commandSender, String[] args) {


		checkConsole();

		Player player = (Player) commandSender;


//		/coin help
		if (args[0].equalsIgnoreCase("help")) {
			UtilsMessage.sendMessage(player, "&c/coin pay <player> <amount>");
			return;
		}


//		/coin pay <player> <amount>
		if (args[0].equalsIgnoreCase("pay")) {

			if (args.length != 3) {
				UtilsMessage.sendMessage(player, "&c/coin pay <player> <amount>");
				return;
			}

//			If any args are null
			if (args[1] == null || args[2] == null) {
				UtilsMessage.errorMessage(player, "/coin pay <player> <amount>");
				return;
			}


			PayCommand.payPlayer(player, args[1], args[2]);

		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			tabComplete.add("help");
			tabComplete.add("pay");

		}


		if (strings.length == 2) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (commandSender.getName().equalsIgnoreCase(player.getName())) {
					continue;
				}
				tabComplete.add(player.getName());
			}
		}

//		amount
		if (strings.length == 3) {
			tabComplete.add("<amount>");
		}

		return tabComplete;
	}
}

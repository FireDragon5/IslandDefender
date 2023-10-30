package me.firedragon5.islanddefender.commands.money;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BalanceCommand extends FireCommand {


	public BalanceCommand() {
		super("balance", new String[]{"bal", "money", "coins"},
				"Check your balance",
				"islanddefender.balance");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();


		Player player = (Player) commandSender;

		int coins = PlayerFileManager.getPlayerCoins(player);
		int crystals = PlayerFileManager.getPlayerCrystals(player);
		int darkCrystals = PlayerFileManager.getPlayerDarkCrystals(player);

		UtilsMessage.sendMessage(player, "&aCoins: &e" + coins);
		UtilsMessage.sendMessage(player, "&aCrystals: &e" + crystals);
		UtilsMessage.sendMessage(player, "&aDark Crystal: &e" + darkCrystals);


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

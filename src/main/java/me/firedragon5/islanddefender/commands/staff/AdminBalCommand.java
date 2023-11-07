package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminBalCommand extends FireCommand {

	public AdminBalCommand() {
		super("adminbal", new String[]{"ab"},"Admin command for Balance"
				,"islanddefender.admin" );
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {

//		/adminbal balance <player>
//		/adminbal pay <player> <amount>
//		/adminbal remove <player> <amount>

		Player player = (Player) commandSender;



//		get the player even if they are offline
		Player target = Bukkit.getOfflinePlayer(strings[2]).getPlayer();

		if (strings[1].equalsIgnoreCase("balance")) {

//			if target is null then they didn't exist
			if (target == null) {
				UtilsMessage.sendMessage(player, "&cThat player does not exist");
				return;
			}

			int coins = PlayerFileManager.getPlayerCoins(target);
			int crystals = PlayerFileManager.getPlayerCrystals(target);
			int darkCrystals = PlayerFileManager.getPlayerDarkCrystals(target);

			UtilsMessage.sendMessage(player, "================== " + target.getName() + " ==================");
			UtilsMessage.sendMessage(player, "&aCoins: &e" + coins);
			UtilsMessage.sendMessage(player, "&aCrystals: &e" + crystals);
			UtilsMessage.sendMessage(player, "&aDark Crystal: &e" + darkCrystals);
			UtilsMessage.sendMessage(player, "====================================================");
		}

		if (strings[1].equalsIgnoreCase("pay")) {

//			if target is null then they didn't exist
			if (target == null) {
				UtilsMessage.sendMessage(player, "&cThat player does not exist");
				return;
			}

			int amount = Integer.parseInt(strings[3]);

			PlayerFileManager.addPlayerCoins(target, amount);

			UtilsMessage.sendMessage(player, "&aYou have given &e" + target.getName() + " &a" + amount + " coins");
			UtilsMessage.sendMessage(target, "&aYou have been given &e" + amount + " coins");
		}


		if (strings[1].equalsIgnoreCase("remove")) {

//			if target is null then they didn't exist
			if (target == null) {
				UtilsMessage.sendMessage(player, "&cThat player does not exist");
				return;
			}

			int amount = Integer.parseInt(strings[3]);

//			if the amount is more the the players balance
			if (amount > PlayerFileManager.getPlayerCoins(target)) {
				UtilsMessage.sendMessage(player, "&c" + target.getName() + " does not have enough coins");
				return;
			}

			PlayerFileManager.removePlayerCoins(target, amount);

			UtilsMessage.sendMessage(player, "&aYou have removed &e" + amount + " coins &afrom &e" + target.getName());
			UtilsMessage.sendMessage(target, "&aYou have had &e" + amount + " coins &aremoved");

		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();

		switch (strings.length) {
			case 1:
				tabComplete.add("balance");
				tabComplete.add("pay");
				tabComplete.add("remove");
				break;
			case 2:
				for (Player player : Bukkit.getOnlinePlayers()) {
					tabComplete.add(player.getName());
				}
				break;
			case 3:
				tabComplete.add("<amount>");
				break;
		}


		return tabComplete;
	}
}

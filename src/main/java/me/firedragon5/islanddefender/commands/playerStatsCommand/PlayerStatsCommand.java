package me.firedragon5.islanddefender.commands.playerStatsCommand;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.menu.playerStats.PlayerStatsMenu;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsCommand extends FireCommand {


	public PlayerStatsCommand() {
		super("playerstats", new String[]{"ps", "playerstats", "stats"},
				"View your stats or another players stats",
				"islanddefender.playerstats");
	}


	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		checkConsole();

		Player player = (Player) commandSender;

		PlayerStatsMenu playerStatsMenu =
				new PlayerStatsMenu(player, Utils.chat("&7Player stats"), 54);

		if (strings.length == 0){
			playerStatsMenu.setupMenu(player);
			playerStatsMenu.openMenu();

		}else if (strings.length == 1) {
			Player target = Bukkit.getOfflinePlayer(strings[0]).getPlayer();
			playerStatsMenu.setupMenu(target);
			playerStatsMenu.openMenu();
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			for (Player player : Bukkit.getOnlinePlayers()){

				if (commandSender.getName().equals(player.getName())){
					continue;
				}

				tabComplete.add(player.getName());
			}
		}



		return tabComplete;
	}
}

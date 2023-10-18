package me.firedragon5.islanddefender.commands.island;


import me.firedragon5.islanddefender.filemanager.island.IslandFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class IslandCommand extends FireCommand {

	public IslandCommand() {
		super("island", new String[]{"is"}, "Island command", "island.command");
	}


	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();


		Player player = (Player) commandSender;

//			If the player does not have an island
		if (IslandFileManager.hasIsland(player)) {
			IslandFileManager.addIsland(player);
		} else {
			IslandFileManager.teleportPlayerToIsland(player);
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}
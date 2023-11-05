package me.firedragon5.islanddefender.commands.island;


import me.firedragon5.islanddefender.filemanager.island.IslandFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class IslandCommand extends FireCommand {


	public IslandCommand() {
		super("island", new String[]{"is"}, "Island command", "islanddefender.island");
	}


	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		checkConsole();


		Player player = (Player) commandSender;

		if (strings.length == 0) {
			// Check if the player already has an island
			if (!IslandFileManager.hasIsland(player)) {
				// If the player doesn't have an island, create one for them
				IslandFileManager.addIsland(player);
			}

			// Teleport the player to their island
			IslandFileManager.teleportPlayerToIsland(player);
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}
package me.firedragon5.islanddefender.commands.hub;

import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HubCommand extends FireCommand {

	public HubCommand() {
		super("hub", new String[]{},
				"Teleport to the hub world", "islanddefender.hub");

	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		Player player = (Player) commandSender;
		checkConsole();


		// Teleport the player to the hub world
		player.teleport(player.getServer().getWorld("hub").getSpawnLocation());


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

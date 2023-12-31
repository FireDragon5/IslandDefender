package me.firedragon5.islanddefender.commands.hub;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
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

		ConfigManger configManager = ConfigManger.getFileManager();
		
		player.teleport(configManager.getHubWorld());


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

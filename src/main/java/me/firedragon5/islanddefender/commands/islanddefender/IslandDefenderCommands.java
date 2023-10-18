package me.firedragon5.islanddefender.commands.islanddefender;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IslandDefenderCommands extends FireCommand {

	public IslandDefenderCommands() {
		super("islanddefender", new String[]{"islanddefender", "isdef"},
				"IslandDefender commands", "islanddefender.admin");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		Player player = (Player) commandSender;

		checkConsole();

//		no permission
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
			return;
		}

//		/islanddefender reload
		if (strings.length == 1) {
			if (strings[0].equalsIgnoreCase("reload")) {

				ConfigManger configManager = ConfigManger.getFileManager();
				UtilsMessage.sendMessage(player, "&aReloading the config!");

				configManager.reloadConfig();
				configManager.checkConfig();

			}
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();


		if (strings.length == 1) {
			tabComplete.add("reload");
		}


		return tabComplete;
	}
}

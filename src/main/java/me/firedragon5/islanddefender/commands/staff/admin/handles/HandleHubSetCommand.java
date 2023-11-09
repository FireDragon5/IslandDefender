package me.firedragon5.islanddefender.commands.staff.admin.handles;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class HandleHubSetCommand {


	public static void handleHubSetCommand(Player player, String[] strings) {

//		set the hub
		if (strings.length == 2) {
			if (strings[1].equalsIgnoreCase("set")) {

				ConfigManger configManager = ConfigManger.getFileManager();

				configManager.setHubWorld(player.getLocation());
				configManager.saveConfig();

				UtilsMessage.sendMessage(player, "&aYou have set the hub location to your current location.");

			}
		}

	}
}

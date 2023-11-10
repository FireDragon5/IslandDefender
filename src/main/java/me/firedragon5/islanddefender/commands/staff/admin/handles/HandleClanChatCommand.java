package me.firedragon5.islanddefender.commands.staff.admin.handles;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import org.bukkit.entity.Player;

public class HandleClanChatCommand {


	public static void handleClanChatCommand(Player player, String[] strings) {

		ClanFolderManager clanFolderManager = new ClanFolderManager();

//		When a admin runs this command they can see the clan chat of the clan they specify
//		/clans chat <clanName>
		if (strings.length < 3) {
			player.sendMessage("Usage: /clans chat <clanName>");
			return;
		}

		String clanName = strings[2];

//		if the clan does not exist
		if (!clanFolderManager.clanExists(clanName)) {
			player.sendMessage("That clan does not exist");
			return;
		}

		






	}
}

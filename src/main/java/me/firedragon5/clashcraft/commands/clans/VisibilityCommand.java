package me.firedragon5.clashcraft.commands.clans;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import org.bukkit.entity.Player;

import java.util.Objects;

public class VisibilityCommand {


	public static void setVisibility(Player player, String clanName, String visibility) {

		if (!isLeader(player, clanName)) {
			return;
		}

		if (!Objects.equals(visibility, "public") && !Objects.equals(visibility, "private")) {
			return;
		}

		ClanFolderManager.getFileManager().setClanVisible(player, visibility);

	}


	//	Check if the player is leader of the clan
	private static boolean isLeader(Player player, String clanName) {
		return Objects.equals(ClanFolderManager.getFileManager().getClanLeader(clanName), player.getName());
	}


}

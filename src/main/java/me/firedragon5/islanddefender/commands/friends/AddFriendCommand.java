package me.firedragon5.islanddefender.commands.friends;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;

public class AddFriendCommand {

	public static void addFriend(Player player, Player targetPlayer) {


//		Check if the player is online
		if (!targetPlayer.isOnline()) {
			UtilsMessage.errorMessage(player, "&cThe player is not online!");
			return;
		}

//		Check if the player is already your friend
		if (PlayerFileManager.isFriend(player, targetPlayer)) {
			UtilsMessage.errorMessage(player, "&cThe player is already your friend!");
			return;
		}

//		Check if the player is ignoring you
		if (PlayerFileManager.isIgnoring(targetPlayer, player)) {
			UtilsMessage.errorMessage(player, "&cCan't add this player as a friend!");
		}

//		Send the player a friend request
		PlayerFileManager.addPlayerFriends(player, targetPlayer);

//		Send the player a message
		UtilsMessage.sendMessage(player, "&aYou have sent a friend request to &e" + targetPlayer.getName());
		
//		Send the target player a message
		UtilsMessage.sendMessage(targetPlayer, "&aYou have received a friend request from &e" + player.getName());
		UtilsMessage.sendMessage(targetPlayer, "&aType &e/friend accept " + player.getName() + " &ato accept the friend request");
		UtilsMessage.sendMessage(targetPlayer, "&aType &e/friend deny " + player.getName() + " &ato deny the friend request");
	}

}

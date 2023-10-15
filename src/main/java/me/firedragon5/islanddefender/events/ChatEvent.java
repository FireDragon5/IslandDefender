package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

public class ChatEvent implements Listener {

	@EventHandler
	public static void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();

		ConfigManger configManager = ConfigManger.getFileManager();


		// Get the player clan tag
		String clanTag = ClanFolderManager.getPlayerClanTag(player);

		String rank = PlayerFileManager.getPlayerRank(player);

		// Format the message: [Tag] PlayerName: Message if the player is in a clan
		String formattedMessage;
		if (!Objects.equals(clanTag, "none")) {

			// Get the chat format from the config
			String chatFormat = configManager.getConfig().getString("chatFormat");

			// Replace the placeholders
			assert chatFormat != null;
			chatFormat = chatFormat.replace("%clan%", clanTag);
			chatFormat = chatFormat.replace("%player%", player.getName());
			chatFormat = chatFormat.replace("%message%", message);
			if (rank != null) {
				chatFormat = chatFormat.replace("%rank%", rank);
			}

			// Format the message
			formattedMessage = chatFormat;

		} else {
			formattedMessage = message;
		}

		// Send the message to all players
		event.setFormat(UtilsMessage.onChat(formattedMessage));

	}

}

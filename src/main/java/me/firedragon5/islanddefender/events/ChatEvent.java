package me.firedragon5.islanddefender.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class ChatEvent implements Listener {

	@EventHandler
	public static void onChat(AsyncChatEvent event) {
		Player player = event.getPlayer();
		Component message = event.message();

		// Get the player clan tag
		String clanTag = ClanFolderManager.getPlayerClanTag(player);

		// If the player is not in a clan, show the chat as normal
		Component formattedMessage;
		if (Objects.equals(clanTag, "none")) {
			formattedMessage = message;
		} else {
			// Format the message: [Tag] PlayerName: Message
			formattedMessage = Component.text("[" + clanTag + "] " + player.getName() + ": ").append(message);
		}

		// Set the message to the formatted message
		event.message(formattedMessage);
	}
}

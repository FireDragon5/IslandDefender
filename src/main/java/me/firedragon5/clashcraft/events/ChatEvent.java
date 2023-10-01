package me.firedragon5.clashcraft.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatEvent implements Listener {


	@EventHandler
	public static void onChat(AsyncChatEvent event) {

		Player player = event.getPlayer();
		Component message = event.message();

//		Get the player clan tag
		String clanTag = ClanFolderManager.getPlayerClanTag(player);

//		Format the message: [Tag] PlayerName: Message
		String formattedMessage = String.format("[%s] %s: %s", clanTag, player.getName(), message);

//		Set the message to the formatted message
		event.message(Component.text(formattedMessage));

	}


}

package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
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
		String clanTag = ClanFolderManager.getPlayerClanTag(player);
		String rank = PlayerFileManager.getPlayerRank(player);

		String chatFormat = configManager.getChatFormat();


//		chatFormat: '%staffRank% %rank% %clan% &7%player%&7: &f%message%'
//		[Helper] [Default] [Clan] [Player]: [Message] - Normal message
//		if player is not a staff member
//		 [Default] [Clan] [Player]: [Message] - there is a space in front of the message
//		if the player is not in a clan
//		[Helper] [Default]   [Player]: [Message] - there is a space in between the rank and player

//		If the player that send a message not a staff member remove the staffRank
		if (!player.hasPermission("islanddefender.staff")) {
			chatFormat = chatFormat.replace("%staffRank%", "");
		} else {
			LuckPerms luckPerms = LuckPermsProvider.get();
			String staffRank = Objects.requireNonNull(luckPerms.getUserManager()
					.getUser(player.getUniqueId())).getPrimaryGroup();

			// Get the prefix of the rank
			String prefix = Objects.requireNonNull(luckPerms.getGroupManager()
							.getGroup(staffRank))
					.getCachedData()
					.getMetaData()
					.getPrefix();

			chatFormat = chatFormat.replace("%staffRank%", prefix);
		}
//		if the player is not in a clan remove the clan tag
		if (Objects.equals(clanTag, "none")) {

			chatFormat = chatFormat.replace("%clan%", "");

		} else {
			chatFormat = chatFormat.replace("%clan%", clanTag);
		}
//		Replace the rank
//		if rank is null replace with Default
		if (rank == null) {
			rank = "Default";
		}

//		 Get the prefix of the rank
		String prefix = Objects.requireNonNull(LuckPermsProvider.get()
						.getGroupManager()
						.getGroup(rank))
				.getCachedData()
				.getMetaData()
				.getPrefix();

		chatFormat = chatFormat.replace("%rank%", prefix);

//		Replace the player name
		chatFormat = chatFormat.replace("%player%", player.getName());

//		Replace the message
		chatFormat = chatFormat.replace("%message%", message);


		event.setFormat(UtilsMessage.onChat(chatFormat));

	}
}

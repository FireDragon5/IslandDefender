package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.manager.NameTagManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinEvent implements Listener {


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

//		First time join add the Default luckperms rank
		if (!player.hasPlayedBefore()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
		}

		NameTagManager.setNameTags(player);
		NameTagManager.newTag(player);


		UtilsMessage.checkPendingMessages();

		LuckPerms luckPerms = LuckPermsProvider.get();
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();


		player.setPlayerListHeader(Utils.chat(ConfigManger.getFileManager().getTablistHeader()));
		player.setPlayerListFooter(Utils.chat(ConfigManger.getFileManager().getTablistFooter()));


		// Set the join message
		event.setJoinMessage(Utils.chat("&a&l+ " + prefix + "&a" + player.getName()));

	}


}

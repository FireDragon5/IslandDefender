package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.manager.NameTagManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class QuitEvent implements Listener {

	@EventHandler
	public static void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		NameTagManager.removeTag(player);


		LuckPerms luckPerms = LuckPermsProvider.get();
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();

		event.setQuitMessage(Utils.chat("&c&l- " + prefix + "&c" + player.getName()));
	}


}

package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.instance.Cosmetic;
import me.firedragon5.islanddefender.manager.NameTagManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class QuitEvent implements Listener {


	private static IslandDefender plugin;

	public QuitEvent(IslandDefender plugin) {
		QuitEvent.plugin = plugin;
	}

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


//		Remove player from a vanished list
		plugin.vanished.remove(player.getUniqueId());

//		Remove player from pending friend requests
		IslandDefender.pendingFriendRequests.remove(player);

//		Remove player from active cosmetics
		if (plugin.getActiveCosmetics().containsKey(player.getUniqueId())) {
			for (Cosmetic cosmetic : plugin.getActiveCosmetics().get(player.getUniqueId())) {
				cosmetic.disable();
			}
			plugin.getActiveCosmetics().remove(player.getUniqueId());
		}


		event.setQuitMessage(Utils.chat("&c&l- " + prefix + "&c" + player.getName()));
	}


}

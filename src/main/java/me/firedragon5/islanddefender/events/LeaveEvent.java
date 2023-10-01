package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

	@EventHandler
	public static void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		event.setQuitMessage(Utils.chat("&c&l- &c" + player.getName()));
	}


}

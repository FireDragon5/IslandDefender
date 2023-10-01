package me.firedragon5.islanddefender;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

public class Utils implements Listener {


	public static @Nullable String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}

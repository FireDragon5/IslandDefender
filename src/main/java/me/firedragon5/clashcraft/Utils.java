package me.firedragon5.clashcraft;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public class Utils implements Listener {


	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
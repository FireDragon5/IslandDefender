package me.firedragon5.islanddefender.instance;

import me.firedragon5.islanddefender.IslandDefender;
import org.bukkit.entity.Player;

public abstract class Cosmetic {

	protected IslandDefender plugin;

	protected Player player;

	public Cosmetic(IslandDefender plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}

	public abstract void enable();

	public abstract void disable();




}

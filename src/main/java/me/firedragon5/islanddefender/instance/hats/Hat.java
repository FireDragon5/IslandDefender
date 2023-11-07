package me.firedragon5.islanddefender.instance.hats;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedragon5.islanddefender.instance.Cosmetic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Hat extends Cosmetic {


	public Hat(IslandDefender plugin, Player player) {
		super(plugin, player);
	}

	@Override
	public void enable() {

		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta sm = (SkullMeta) is.getItemMeta();

		sm.setOwningPlayer(player);

		is.setItemMeta(sm);

		player.getInventory().setHelmet(is);


	}

	@Override
	public void disable() {

	}
}

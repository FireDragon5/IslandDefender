package me.firedragon5.islanddefender.menu.cosmetic;

import me.firedraong5.firesapi.menu.FireMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CosmeticMenu extends FireMenu {

	public CosmeticMenu(){

	}


	public CosmeticMenu(Player player, String name, int size) {
		super(player, name, size);
	}

	public void setupMenu() {


		List<String> hatLore = new ArrayList<>();
		hatLore.add("&7Click to open hats menu");

		setItem(0, Material.DIAMOND_HELMET, "&7Hats", hatLore);




	}

	@Override
	public void openMenu() {
		super.openMenu();
	}
}

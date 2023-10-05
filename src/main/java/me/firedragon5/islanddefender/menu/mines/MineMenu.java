package me.firedragon5.islanddefender.menu.mines;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MineMenu extends Menu implements Listener {

	public MineMenu() {

	}

	public MineMenu(Player player, String name, int size) {
		super(player, name, size);
	}


	public void setupMenu() {

//		Add all the mines to the menu that is in the config
		MineFileManager mineManager = MineFileManager.getFileManager();

		List<String> mineLore = new ArrayList<>();

		for (String mine : mineManager.getMineList()) {
//		Mine name
			mineLore.add("&7Mine name: &a" + mineManager.getName(mine));
//			rank
			mineLore.add("&7Rank: &a" + mineManager.getRank(mine));
//			Blocks
			mineLore.add("&7Blocks: &a" + mineManager.getBlocks(mine));
//			cost
			mineLore.add("&7Cost: &a" + mineManager.getCost(mine));

//			String to material
			Material material = Material.getMaterial(Objects.requireNonNull(mineManager.getDisplayBlock(mine)));

			setItem(0, material, "&a&l" + mine, mineLore);


		}

		glass(Material.BLACK_STAINED_GLASS_PANE);

	}

	@Override
	public void openMenu() {
		super.openMenu();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equalsIgnoreCase(Utils.chat("&bMines"))) return;
		event.setCancelled(true);
	}


}

package me.firedragon5.islanddefender.menu.Invsee;

import me.firedragon5.islanddefender.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InvSeeMenu implements Listener {

	private static Player target;




	public static Inventory getInventory(Player target){

		Inventory inv = Bukkit.createInventory(null, 54,
				Objects.requireNonNull(Utils.chat("&7" + target.getName() + "'s Inventory")));

		ItemStack[] armour = target.getInventory().getArmorContents();
		ItemStack[] invContent = target.getInventory().getStorageContents();

		List<ItemStack> contents = new ArrayList<>(Arrays.asList(invContent));

		for (int i = 0; i < 9; i++){
			contents.add(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}

		Collections.addAll(contents, armour);

		ItemStack[] cont = contents.toArray(new ItemStack[0]);

		inv.setContents(cont);
		return inv;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&7" + getTarget() + "'s Inventory"))){
			System.out.println("Invsee menu clicked");
//			cancel the event if the user clicks on a black stained glass pane
			if (e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)){
				e.setCancelled(true);
			}
		}
	}


	public Player getTarget() {
		return target;
	}

	public static void setTarget(Player target) {
		InvSeeMenu.target = target;
	}
}

//package me.firedragon5.islanddefender.items;
//
//import me.firedraong5.firesapi.itemCreation.CustomItemCreator;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MineRegionTool extends CustomItemCreator {
//
//	String itemName;
//
//	public void giveTool(Player player, String mineName) {
//
//		List<String> lore = new ArrayList<>();
//		lore.add("&7Right click to set the first location");
//		lore.add("&7Left click to set the second location");
//
//
//		ItemStack item = createItem(Material.STICK, 1, mineName, lore);
//
//
//		// Get the item name from the created item
//		itemName = getItemName(item);
//
//		addToInventory(player, item);
//
//
//	}
//
////	get the item name
//
//
//
//	public static @NotNull String getItemName(@NotNull ItemStack item) {
//		return item.getItemMeta().getDisplayName();
//	}
//
//
//}

package me.firedragon5.islanddefender.manager;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SchematicManager {


	public static void pasteSchematic(Player player, Location location, String schematicFilePath) {
		try {
			WorldEdit worldEdit = WorldEdit.getInstance();
			LocalSession session = worldEdit.getSessionManager().findByName(player.getName());





		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

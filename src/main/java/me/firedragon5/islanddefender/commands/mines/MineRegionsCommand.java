package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineRegionsCommand extends FireCommand {

	public MineRegionsCommand() {
		super("mineregions", new String[]{"mr"},
				"Show all the mine regions",
				"islanddefender.mine.regions");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		Player player = (Player) sender;
		checkConsole();


		if (player.hasPermission("islanddefender.mine.admin")) {
//		/mineregions create <name>

			if (args[0].equalsIgnoreCase("create")) {
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.createMine(player, args[1]);
//		/mineregions delete <name>

			} else if (args[0].equalsIgnoreCase("delete")) {
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.deleteMine(args[1]);
//		/mineregions reload

			} else if (args[0].equalsIgnoreCase("reload")) {
				UtilsMessage.sendMessage(player, "&aReloading the mines config!");
				MineFileManager mineManager = MineFileManager.getFileManager();
				mineManager.reloadMineConfig();
			}


//			/mineregions fill name
//			This will fill the mine with the blocks
			else if (args[0].equalsIgnoreCase("fill")) {
				MineFileManager mineManager = MineFileManager.getFileManager();

				Cuboid cuboid = new Cuboid(
						new Location(player.getWorld(),
								mineManager.getPitLocationsX(args[1], "top-block"),
								mineManager.getPitLocationsY(args[1], "top-block"),
								mineManager.getPitLocationsZ(args[1], "top-block")),
						new Location(player.getWorld(),
								mineManager.getPitLocationsX(args[1], "bottom-block"),
								mineManager.getPitLocationsY(args[1], "bottom-block"),
								mineManager.getPitLocationsZ(args[1], "bottom-block"))
				);

				List<String> blockList = mineManager.getBlockListAsList(args[1]);

				int totalBlocks = cuboid.getBlocks().size();

				// Create a list to store the materials based on their percentages
				List<Material> materials = new ArrayList<>();
				for (String blockName : blockList) {
					int percentage = mineManager.getPercentage(args[1], blockName);
					Material material = Material.matchMaterial(blockName);
					if (material != null) {
						int numBlocks = totalBlocks * percentage / 100;
						for (int i = 0; i < numBlocks; i++) {
							materials.add(material);
						}
					}
				}

				Collections.shuffle(materials); // Shuffle the list of materials

				// Iterate through the shuffled list and set the block types
				for (int i = 0; i < totalBlocks; i++) {
					Block block = cuboid.getBlocks().get(i);
					int materialIndex = i % materials.size(); // Wrap around with modulus
					Material material = materials.get(materialIndex);
					block.setType(material);
				}

				UtilsMessage.sendMessage(player, "&aFilled the mine with the blocks!");
			}


		} else {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
		}


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();

//		All users perm : islanddefender.mine
//		Admins perm : islanddefender.mine.admin

//		/mine create <name>
//		/mine delete <name>
//		/mine fill <name>

		if (commandSender instanceof Player player) {
			if (player.hasPermission("islanddefender.mine.admin")) {
				if (strings.length == 1) {
					tabComplete.add("create");
					tabComplete.add("delete");
					tabComplete.add("reload");
					tabComplete.add("fill");
				} else if (strings.length == 2) {
					tabComplete.add("<name>");
				}
			}
		}


		return tabComplete;

	}
}

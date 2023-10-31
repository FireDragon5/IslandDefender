package me.firedragon5.islanddefender.commands.mines;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MineRegionsCommand extends FireCommand {

	public MineRegionsCommand() {
		super("mineregions", new String[]{"mr"},
				"Show all the mine regions",
				"islanddefender.mine.admin");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {


		if (args.length < 1) {
			UtilsMessage.sendMessage(sender, "&cPlease use /mineregions <create/delete/reload/fill> [name]");
			return;
		}

		Player player = (sender instanceof Player) ? (Player) sender : null;

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
					new Location(Bukkit.getWorld("world"),
							mineManager.getPitLocationsX(args[1], "top-block"),
							mineManager.getPitLocationsY(args[1], "top-block"),
							mineManager.getPitLocationsZ(args[1], "top-block")),
					new Location(Bukkit.getWorld("world"),
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

			if (player != null) {
				UtilsMessage.sendMessage(player, "&aFilled the mine with the blocks!");
			}
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

//		Make it display like this
//		mine1
//		mine2

		MineFileManager mineManager = MineFileManager.getFileManager();
		List<String> mineList = mineManager.getMineList();


		if (commandSender instanceof Player player) {
			if (strings.length == 1) {

				for (String commandName : Arrays.asList("create", "delete", "reload", "fill")) {
					if (commandName.startsWith(strings[0].toLowerCase()) && player.hasPermission("islanddefender.mine.admin")) {
						tabComplete.add(commandName);
					}
				}
			} else if (strings.length == 2) {
				String commandName = strings[0].toLowerCase();
				switch (commandName) {
					case "delete":
					case "fill":
						tabComplete.add(mineList.toString().replace("[", "").replace("]", ""));
						break;

					case "create":
						tabComplete.add("<name>");
						break;

					case "reload":
						break;

				}
			}
		}


		return tabComplete;

	}


}

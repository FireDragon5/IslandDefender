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

				Material blocks = mineManager.getBlockList(args[1]);
				int percentage = mineManager.getPercentage(args[1]);


//				Fill the mine with the blocks but some blocks needs to more than others
//				For example, stone needs to be 90% of the blocks and iron ore needs to be 10% of the blocks
//				We can do this by getting the percentage of the blocks and then multiplying it by the amount of blocks
//				We can get the percentage by doing 100 / the amount of blocks


				for (Block block : cuboid.getBlocks()) {
					if (Math.random() * 100 < percentage) {
						block.setType(blocks);
					} else {
						block.setType(Material.STONE);
					}
				}

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

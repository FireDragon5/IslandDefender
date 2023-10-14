package me.firedragon5.islanddefender.commands.islanddefender;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IslandDefenderCommands implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


		Player player = (Player) commandSender;

//		no permission
		if (!player.hasPermission("islanddefender.admin")) {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
			return true;
		}

//		/islanddefender reload
		if (strings.length == 1) {
			if (strings[0].equalsIgnoreCase("reload")) {

				ConfigManger configManager = ConfigManger.getFileManager();
				UtilsMessage.sendMessage(player, "&aReloading the config!");

				configManager.reloadConfig();
				configManager.checkConfig();

				return true;
			}
		}


		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

		List<String> tabComplete = new ArrayList<>();


		if (strings.length == 1) {
			tabComplete.add("reload");
		}
		

		return tabComplete;
	}
}

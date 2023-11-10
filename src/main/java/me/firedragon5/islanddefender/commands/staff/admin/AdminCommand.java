package me.firedragon5.islanddefender.commands.staff.admin;

import me.firedragon5.islanddefender.commands.staff.admin.handles.*;
import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand extends FireCommand {

	public AdminCommand() {
		super("admin", new String[]{"a"}, "Admin chat and balance commands", "islanddefender.admin");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {
		checkConsole();
		Player player = (Player) commandSender;


		if (strings[0].equalsIgnoreCase("money")) {
			HandleMoneyCommands.handleMoneyCommands(player, strings);

		} else if (strings[0].equalsIgnoreCase("reload")) {

			String reloadType = strings[1];

			HandleReloadCommand.handleReloadCommand(player, reloadType);
		} else if (strings[0].equalsIgnoreCase("clans")) {
			HandleClansCommands.handleClanCommands(player, strings);
		}
//			mines
		else if (strings[0].equalsIgnoreCase("mines")) {
			HandleMinesCommand.handleMinesCommand(player, strings);
		}
//			hub
		else if (strings[0].equalsIgnoreCase("hub")) {
//				set the hub
			HandleHubSetCommand.handleHubSetCommand(player, strings);
		}

	}


	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();
		
		if (strings.length == 1) {
			tabComplete.add("money");
			tabComplete.add("reload");
			tabComplete.add("clans");
			tabComplete.add("mines");
			tabComplete.add("hub");
		}


		switch (strings[0]) {
			case "money":
				if (strings.length == 2 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("balance");
					tabComplete.add("give");
					tabComplete.add("remove");
				} else if (strings.length == 3 && commandSender.hasPermission("islanddefender.admin")) {
					for (Player player : Bukkit.getOnlinePlayers()) {

//						Don't show the command sener in the tab complete
						if (player.getName().equals(commandSender.getName())) {
							continue;
						}

						tabComplete.add(player.getName());
					}
				} else if (strings.length == 4 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("10");
					tabComplete.add("100");
					tabComplete.add("1000");
				}
				break;
			case "reload":
				if (strings.length == 2 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("config");
					tabComplete.add("mines");
					tabComplete.add("ranks");
					tabComplete.add("kits");
					tabComplete.add("sells");
					tabComplete.add("shop");
					tabComplete.add("all");

				}
				break;

			case "clans":
				if (strings.length == 2 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("create");
					tabComplete.add("delete");
					tabComplete.add("info");
					tabComplete.add("chat");

				} else if (strings.length == 3 && commandSender.hasPermission("islanddefender.admin")) {
					ClanFolderManager clanFolderManager = new ClanFolderManager();
					tabComplete.addAll(clanFolderManager.getClanList());
				}



				break;

			case "mines":
				if (strings.length == 2 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("create");
					tabComplete.add("delete");
				}
				break;

			case "hub":
				if (strings.length == 2 && commandSender.hasPermission("islanddefender.admin")) {
					tabComplete.add("set");
				}
				break;
		}

		return tabComplete;
	}

}

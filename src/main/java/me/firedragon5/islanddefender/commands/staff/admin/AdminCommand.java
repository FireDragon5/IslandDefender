package me.firedragon5.islanddefender.commands.staff.admin;

import me.firedragon5.islanddefender.commands.staff.admin.handles.HandleClansCommands;
import me.firedragon5.islanddefender.commands.staff.admin.handles.HandleMinesCommand;
import me.firedragon5.islanddefender.commands.staff.admin.handles.HandleMoneyCommands;
import me.firedragon5.islanddefender.commands.staff.admin.handles.HandleReloadCommand;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
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

		ConfigManger configManager = ConfigManger.getFileManager();

		if (strings.length >= 1) {
			if (strings[0].equalsIgnoreCase("money")) {
				HandleMoneyCommands.handleMoneyCommands(player, strings);
			} else if (strings[0].equalsIgnoreCase("reload")) {

				String reloadType = strings[1];

				HandleReloadCommand.handleReloadCommand(player, reloadType);
			}else if (strings[0].equalsIgnoreCase("clans")){
				HandleClansCommands.handleClansCommands(player, strings);
			}
//			mines
			else if (strings[0].equalsIgnoreCase("mines")) {
				HandleMinesCommand.handleMinesCommand(player, strings);
			}


			else {
				// Combine the arguments into a single message for admin chat
				String message = String.join(" ", strings);
				for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
					if (onlinePlayer.hasPermission("islanddefender.admin")) {
						UtilsMessage.sendMessage(onlinePlayer, configManager.getAdminChatFormat()
								.replace("%player%", player.getName())
								.replace("%message%", message));
					}
				}
			}
		}
	}



	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			tabComplete.add("money");
			tabComplete.add("reload");
		} else if (strings.length == 2 && strings[0].equalsIgnoreCase("money")) {
			if (commandSender.hasPermission("islanddefender.admin")) {
				tabComplete.add("balance");
				tabComplete.add("give");
				tabComplete.add("remove");
			}
		} else if (strings.length == 3 && strings[0].equalsIgnoreCase("money")) {
			if (commandSender.hasPermission("islanddefender.admin")) {
				tabComplete.add("<amount>");
			}
		} else if (strings.length == 4 && strings[0].equalsIgnoreCase("money")) {
			if (commandSender.hasPermission("islanddefender.admin")) {
				tabComplete.add("<player>");
			}
		}
//		reload
		else if (strings.length == 2 && strings[0].equalsIgnoreCase("reload")) {
			if (commandSender.hasPermission("islanddefender.admin")) {
				tabComplete.add("config");
				tabComplete.add("mines");
				tabComplete.add("ranks");
				tabComplete.add("kits");
				tabComplete.add("sells");
				tabComplete.add("shop");
			}
		} else if (strings.length == 3 && strings[0].equalsIgnoreCase("reload")) {
			if (commandSender.hasPermission("islanddefender.admin")) {
				tabComplete.add("all");
			}
		}


		return tabComplete;
	}
}

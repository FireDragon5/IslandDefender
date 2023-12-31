package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffCommand extends FireCommand {

	public StaffCommand() {
		super("staffcommands", new String[]{"sc", "staff"},
				"Staff chat and staff list commands",
				"islanddefender.staff");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		checkConsole();

		if (sender instanceof Player player) {
			ConfigManger configManager = ConfigManger.getFileManager();


			LuckPerms luckPerms = LuckPermsProvider.get();
			String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
			String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
					.getCachedData()
					.getMetaData()
					.getPrefix();

				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("islanddefender.staff")) {
						UtilsMessage.sendMessage(player, "");
						UtilsMessage.sendMessage(player, "&bStaff Chat Members:");
						UtilsMessage.sendMessage(player, "&8=============================");
						for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
							if (onlinePlayer.hasPermission("islanddefender.staff")) {
								UtilsMessage.sendMessage(player, "&a- " + prefix + "&7" + onlinePlayer.getName());
							}
						}
						UtilsMessage.sendMessage(player, "&8=============================");
						UtilsMessage.sendMessage(player, "");

					} else {
						UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
					}
				} else {
					// Combine the arguments into a single message
					String message = String.join(" ", args);
					for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
						if (onlinePlayer.hasPermission("islanddefender.staff")) {
							UtilsMessage.sendMessage(onlinePlayer, configManager.getStaffChatFormat()
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
			tabComplete.add("list");
		}
		return tabComplete;
	}








}

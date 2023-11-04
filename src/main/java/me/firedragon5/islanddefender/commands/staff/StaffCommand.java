package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.menu.Invsee.InvSeeMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
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

			if (args.length == 0) {
				UtilsMessage.sendMessage(player, "&bStaff Commands:");
				UtilsMessage.sendMessage(player, "&b/sc <message> &7- Send a message to the staff chat");
				UtilsMessage.sendMessage(player, "&b/staff list &7- List all the staff chat members");
				UtilsMessage.sendMessage(player, "&b/staff help &7- Show this message");
			} else {
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
				} else if (args[0].equalsIgnoreCase("muteChat")) {
//				This will mute the global chat so no player will be able to send a message exsept for staff memebers
					if (player.hasPermission("islanddefender.staff")) {
						if (configManager.isChatMuted()) {
							configManager.setChatMuted(false);
							UtilsMessage.broadcastMessage("&aThe chat has been unmuted!");
						} else {
							configManager.setChatMuted(true);
							UtilsMessage.broadcastMessage("&cThe chat has been muted!");
						}
					} else {
						UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
					}

				}else if (args[0].equalsIgnoreCase("invsee")) {

					Player target = Bukkit.getPlayer(args[1]);

					if (player.hasPermission("islanddefender.staff")) {
						if (args.length != 2) {
							UtilsMessage.errorMessage(player, "Usage: /staff invsee <player>");
							return;
						}



						if (target == null) {
							UtilsMessage.errorMessage(player, "That player is not online!");
							return;
						}


						InvSeeMenu.setTarget(target);
						player.openInventory(InvSeeMenu.getInventory(target));
					}

				}


				else {
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
		} else {
			sender.sendMessage(Objects.requireNonNull(Utils.chat("&cYou must be a player to run this command!")));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();
		switch (strings.length) {
			case 1:
				tabComplete.add("list");
				tabComplete.add("help");
				tabComplete.add("muteChat");
				tabComplete.add("invsee");
				break;
			case 2:
				if (strings[0].equalsIgnoreCase("invsee")) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						tabComplete.add(player.getName());
					}
				}
				break;
		}
		return tabComplete;
	}








}

package me.firedragon5.islanddefender.commands.clans.clanCommands;

import me.firedragon5.islanddefender.filemanager.clans.ClanFolderManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DisbandCommand {

	// HashMap for confirmation
	public static HashMap<Player, Boolean> confirmation = new HashMap<>();

	public static void disbandClan(Player player) {

		if (!hasPermission(player)) {
			return;
		}

		if (!ClanFolderManager.getFileManager().isLeader(player)) {
			return;
		}

		// Confirmation
		if (!confirmation.containsKey(player)) {
			// Create a clickable confirmation message
			TextComponent confirmationMessage = new TextComponent(ChatColor.RED + "Are you sure you want to disband your clan? ");
			TextComponent yes = new TextComponent(ChatColor.GREEN + "Yes");
			yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan disband yes"));
			TextComponent separator = new TextComponent(ChatColor.RED + " | ");
			TextComponent no = new TextComponent(ChatColor.RED + "No");
			no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan disband no"));

			// Add components together
			confirmationMessage.addExtra(yes);
			confirmationMessage.addExtra(separator);
			confirmationMessage.addExtra(no);

			// Send the confirmation message
			player.sendMessage(confirmationMessage);

			confirmation.put(player, false);
			return;
		}

		ClanFolderManager.getFileManager().deleteClan(player);
		UtilsMessage.sendMessage(player, "&aYou have disbanded your clan");
	}

	// Has perms
	private static boolean hasPermission(Player player) {
		return player.hasPermission("islanddefender.clan.create");
	}

	public static void confirmDisband(Player player, boolean confirm) {
		if (!hasPermission(player)) {
			return;
		}

		if (!ClanFolderManager.getFileManager().isLeader(player)) {
			return;
		}

		if (confirmation.containsKey(player)) {
			if (confirm) {
				// Player confirmed, disband the clan
				ClanFolderManager.getFileManager().deleteClan(player);
				UtilsMessage.sendMessage(player, "&aYou have disbanded your clan");
			} else {
				// Player canceled the action
				UtilsMessage.sendMessage(player, "&cClan disband canceled.");
			}
			confirmation.remove(player);
		} else {
			UtilsMessage.sendMessage(player, "&cYou haven't initiated the disband process.");
		}
	}


}

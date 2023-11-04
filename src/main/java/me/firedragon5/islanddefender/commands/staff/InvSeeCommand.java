package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.menu.Invsee.InvSeeMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InvSeeCommand extends FireCommand {

	public InvSeeCommand() {
		super("invsee", new String[]{"invsee"},
				"Open a players inventory",
				"islanddefender.staff");
	}

	@Override
	public void execute(CommandSender commandSender, String[] args) {
		Player player = (Player) commandSender;
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

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		List<String> tabComplete = new ArrayList<>();
		switch (strings.length) {
			case 1:
				tabComplete.add("invsee");
				break;
			case 2:
				if (strings[0].equalsIgnoreCase("invsee")) {
					for (Player player : Bukkit.getOnlinePlayers()) {

						if (commandSender.getName().equals(player.getName())) continue;

						tabComplete.add(player.getName());
					}
				}
				break;
		}
		return tabComplete;
	}
}

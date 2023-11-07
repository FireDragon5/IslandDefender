package me.firedragon5.islanddefender.commands.staff;

import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdminClanCommands extends FireCommand {

	public AdminClanCommands() {
		super("adminclan", new String[]{"aclans", "ac"},
				"Clan commands",
				"islanddefender.admin");

	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		checkConsole();

		Player player = (Player) sender;

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}


}

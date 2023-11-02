package me.firedragon5.islanddefender.commands.trade;

import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TradeCommand extends FireCommand {

	public TradeCommand() {

		super("trade", new String[]{"t"},
				"Trade commands",
				"islanddefender.trade");

	}


	@Override
	public void execute(CommandSender commandSender, String[] strings) {

//		trade <player>
//		trade accept|decline|cancel


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

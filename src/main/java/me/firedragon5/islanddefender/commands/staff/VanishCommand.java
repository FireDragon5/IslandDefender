package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VanishCommand extends FireCommand {


	public VanishCommand() {
		super("vanish", new String[] {"v"}, "Vanish from other players", "islanddefender.staff");
	}



	@Override
	public void execute(CommandSender commandSender, String[] args) {


		Player target = null;

		if (args.length == 0){
			checkConsole();
			target = (Player) commandSender;

		}else {
			target = Bukkit.getPlayer(args[0]);

			if (target == null){
				UtilsMessage.sendMessage(commandSender, "&cThat player is not online!");
				return;
			}
		}

		boolean vanished = IslandDefender.getInstance().vanished.contains(target.getUniqueId());

		for (Player player : Bukkit.getOnlinePlayers()) {
				if (target.equals(player)) {
					continue;
				}
//				if the player is in the vanished set then hide them
				if (vanished)
					player.showPlayer(target);
				else
					player.hidePlayer(target);





//				if you have the staff perm you can see them
				if (player.hasPermission("islanddefender.staff")) {
					player.showPlayer(target);
//					Let the other staff know if the player is vanished or not
					UtilsMessage.sendMessage(player, "&c"
							+ target.getName()
							+ " &7has been " + (vanished ? "unvanished" : "vanished") + "&7.");
				}

		}


		UtilsMessage.sendMessage(target, "&c"
				+ target.getName()
				+ " &7has been " + (vanished ? "unvanished" : "vanished") + "&7.");


		if (vanished)
			IslandDefender.getInstance().vanished.remove(target.getUniqueId());
		else
			IslandDefender.getInstance().vanished.add(target.getUniqueId());



	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

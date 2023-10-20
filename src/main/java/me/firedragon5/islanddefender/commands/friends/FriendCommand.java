package me.firedragon5.islanddefender.commands.friends;

import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.command.FireCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class FriendCommand extends FireCommand {

	public FriendCommand() {
		super("friend", new String[]{"friends", "f"},
				"Friend commands",
				"islanddefender.friend");
	}


	private PlayerFileManager playerFile;

	@Override
	public void execute(CommandSender commandSender, String[] args) {


		checkConsole();

		Player player = (Player) commandSender;

//		target player
		Player targetPlayer = Bukkit.getPlayer(args[1]);


//		/friends  (opens the friends menu)
		if (args.length == 0) {
//			FriendMenu.openFriendMenu(player);
		}

//		/fiends add <name>
		if (args[0].equalsIgnoreCase("add")) {
			assert targetPlayer != null;
			AddFriendCommand.addFriend(player, targetPlayer);
		}


//		/friends remove <name>
//		/friends ignore <name> (this will stop the player from sending you friend requests)
//		/friends ignore list (this will list all the players you are ignoring)
//		/friends ignore remove <name> (this will remove the player from your ignore list)
//		/friends accept (this will accept the friend request)
//		/friends deny (this will deny the friend request)
//


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();

		if (strings.length == 1) {
			for (String s : new String[]{"add", "remove", "ignore", "accept", "deny"}) {
				if (s.startsWith(strings[0].toLowerCase())) {
					tabComplete.add(s);
				}
			}
		} else if (strings.length == 2) {
			String commandName = strings[0].toLowerCase();
			switch (commandName) {
				case "add":
				case "remove":
				case "ignore":
					tabComplete.add("<name>");
					break;
			}
		} else if (strings.length == 3) {
			String commandName = strings[0].toLowerCase();
			if (commandName.equals("ignore")) {
				if (strings[1].equalsIgnoreCase("remove")) {
					tabComplete.add("<name>");
				}
			}
		}

		return tabComplete;
	}
}

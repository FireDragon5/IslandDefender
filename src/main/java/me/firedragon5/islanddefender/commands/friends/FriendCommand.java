package me.firedragon5.islanddefender.commands.friends;

import me.firedragon5.islanddefender.IslandDefender;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.menu.friends.FriendsMenu;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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

//		/friends  (opens the friends menu)
		if (args.length == 0) {
			FriendsMenu friendsMenu = new FriendsMenu(player, "&7Friends", 54);
			friendsMenu.setupMenu();
			friendsMenu.openMenu();

			return;
			//		/fiends add <name>
		} else if (args[0].equalsIgnoreCase("add")) {

			// check if the player name was specified
			if (args.length < 2) {
				UtilsMessage.errorMessage(player, "Please specify a player!");
				return;
			}
			Player targetPlayer = Bukkit.getPlayer(args[1]);


//			Add it to the hashmap
			IslandDefender.pendingFriendRequests.put(player, targetPlayer);

			if (targetPlayer == null) {
				UtilsMessage.errorMessage(player, "That player is not online or does not exist!");
				return;
			} else //			Player can't add themselves as a friend
				if (targetPlayer.getName().equals(player.getName())) {
					UtilsMessage.errorMessage(player, "You can't add yourself as a friend! :)");
					return;
				}

			AddFriendCommand.addFriend(player, targetPlayer);
		}

//		/friends accept <name> (this will accept the friend request)
		if (args[0].equalsIgnoreCase("accept")) {

//			Index 1 out of bounds for length 1
			if (args.length < 2) {
				UtilsMessage.errorMessage(player, "Please specify a player!");
				return;
			}

			Player targetPlayer = Bukkit.getPlayer(args[1]);


//			Check if the player is in the hashmap
			if (!IslandDefender.pendingFriendRequests.containsKey(player)) {
				UtilsMessage.errorMessage(player, "You do not have any pending friend requests!");
				return;
			}

			if (targetPlayer == null) {
				UtilsMessage.errorMessage(player, "That player is not online or does not exist!");
				return;
			}

			PlayerFileManager.addPlayerFriends(player, targetPlayer);

//			remove the player from the hashmap
			IslandDefender.pendingFriendRequests.remove(player);
		}

//		/friends remove <name>


//		/friends ignore <name> (this will stop the player from sending you friend requests)
//		/friends ignore list (this will list all the players you are ignoring)
//		/friends ignore remove <name> (this will remove the player from your ignore list)


//		/friends deny (this will deny the friend request)
//


	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {

		List<String> tabComplete = new ArrayList<>();
		List<String> players = new ArrayList<>();

//		Get the players friends
		List<String> friends = PlayerFileManager.getPlayerFriends((Player) commandSender);


//		Get the list of all players online and add them to the tab complete
		for (Player player : Bukkit.getOnlinePlayers()) {
//			if the player is not the command sender or already a friend
			if (!player.getName().equals(commandSender.getName())
					&& !friends.contains(player.getUniqueId().toString()))
				players.add(player.getName());
		}


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

				case "ignore":
					for (String player : players) {
						if (player.startsWith(strings[1].toLowerCase())) {
							tabComplete.add(player);
						}
					}
					break;
				case "remove":
					for (String player : friends) {
						if (player.startsWith(strings[1].toLowerCase())) {

//							converting the UUID to a player name

							Player UUIDToName = Bukkit.getPlayer(UUID.fromString(player));

							tabComplete.add(UUIDToName.getName());
						}
					}
					break;
			}
		} else if (strings.length == 3) {
			String commandName = strings[0].toLowerCase();
			if (commandName.equals("ignore")) {
				if (strings[1].equalsIgnoreCase("remove")) {

					for (String player : friends) {
						if (player.startsWith(strings[2].toLowerCase())) {

//							converting the UUID to a player name

							Player UUIDToName = Bukkit.getPlayer(UUID.fromString(player));

							tabComplete.add(UUIDToName.getName());
						}


					}
				}
			}

		}
		return tabComplete;
	}
}

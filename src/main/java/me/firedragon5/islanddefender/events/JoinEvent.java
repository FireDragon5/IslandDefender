package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();


//		When the player joins the server add them to the folderS
		PlayerFileManager.addPlayer(player);

//		Check if the player has all the correct stuff in their yml file
		PlayerFileManager.checkPlayer(player);


		event.setJoinMessage(Utils.chat("&a&l+ &a" + player.getName()));

	}


}

package me.firedragon5.clashcraft.events;

import me.firedragon5.clashcraft.Utils;
import me.firedragon5.clashcraft.filemanager.player.PlayerFileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {


	@EventHandler
	public void onJoin(PlayerJoinEvent event){

		Player player = event.getPlayer();
		

//		When the player joins the server add them to the folder
		PlayerFileManager.addPlayer(player);

//		Check if the player has all the correct stuff in their yml file
		PlayerFileManager.checkPlayer(player);

//		Set the join message
		event.setJoinMessage(Utils.chat("&a" + player.getName() + " has joined the server!"));

	}

}

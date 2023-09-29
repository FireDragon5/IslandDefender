package me.firedragon5.clashcraft.events;

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

//		Set the join message
		event.setJoinMessage("Welcome to the server " + player.getName());

	}

}

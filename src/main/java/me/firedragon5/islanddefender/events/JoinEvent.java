package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedragon5.islanddefender.filemanager.ranks.RankFileManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class JoinEvent implements Listener {


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

//		First time join add the Default luckperms rank
		if (!player.hasPlayedBefore()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
		}


		String rankDisplayName = RankFileManager.getFileManager().getPrefix(PlayerFileManager.getPlayerRank(player));

		player.playerListName(Component.text(Objects.requireNonNull(Utils.chat(rankDisplayName + " &f" + player.getName()))));

		// Set the player's display name
		player.displayName(Component.text(Objects.requireNonNull(Utils.chat(rankDisplayName + " &f" + player.getName()))));

		// Set the join message
		event.setJoinMessage(Utils.chat("&a&l+ &a" + player.getName()));

	}


}

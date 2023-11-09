package me.firedragon5.islanddefender.events;

import me.firedragon5.islanddefender.Utils;
import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedragon5.islanddefender.filemanager.player.PlayerFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
			player.teleport(Bukkit.getWorld("world").getSpawnLocation());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set default");
		}

		//		Check if player has yml file
		if (!PlayerFileManager.hasPlayer(player)){
			PlayerFileManager.addPlayer(player);
		}

//		if player op game mode creative
		if (player.isOp()) {
			player.setGameMode(GameMode.CREATIVE);
		} else {
			player.setGameMode(GameMode.SURVIVAL);
		}

		UtilsMessage.checkPendingMessages();

//		When player spawn spawn them in the world "world"
		player.teleport(ConfigManger.getFileManager().getHubWorld());

		player.setPlayerListHeader(Utils.chat(ConfigManger.getFileManager().getTablistHeader()));
		player.setPlayerListFooter(Utils.chat(ConfigManger.getFileManager().getTablistFooter()));



		LuckPerms luckPerms = LuckPermsProvider.get();
		String rankDisplayName = Objects.requireNonNull(luckPerms.getUserManager().getUser(player.getUniqueId())).getPrimaryGroup();
		String prefix = Objects.requireNonNull(luckPerms.getGroupManager().getGroup(rankDisplayName))
				.getCachedData()
				.getMetaData()
				.getPrefix();

		// Set the join message
		event.setJoinMessage(Utils.chat("&a&l+ " + prefix + "&a" + player.getName()));

	}


}

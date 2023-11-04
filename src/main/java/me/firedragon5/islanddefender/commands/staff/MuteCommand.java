package me.firedragon5.islanddefender.commands.staff;

import me.firedragon5.islanddefender.filemanager.config.ConfigManger;
import me.firedraong5.firesapi.command.FireCommand;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MuteCommand extends FireCommand {

	public MuteCommand() {
		super("mutechat", new String[]{"mutec", "mc"},
				"Mute a player",
				"islanddefender.staff");
	}

	@Override
	public void execute(CommandSender commandSender, String[] strings) {

		Player player = (Player) commandSender;
		ConfigManger configManager = ConfigManger.getFileManager();

		//				This will mute the global chat so no player will be able to send a message exsept for staff memebers
		if (player.hasPermission("islanddefender.staff")) {
			if (configManager.isChatMuted()) {
				configManager.setChatMuted(false);
				UtilsMessage.broadcastMessage("&aThe chat has been unmuted!");
			} else {
				configManager.setChatMuted(true);
				UtilsMessage.broadcastMessage("&cThe chat has been muted!");
			}
		} else {
			UtilsMessage.errorMessage(player, "You don't have permission to use this command!");
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, String[] strings) {
		return null;
	}
}

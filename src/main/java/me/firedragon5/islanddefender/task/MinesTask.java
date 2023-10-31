package me.firedragon5.islanddefender.task;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;
import org.bukkit.scheduler.BukkitRunnable;

public class MinesTask extends BukkitRunnable {


	//	Instance
	private static MinesTask instance;

	@Override
	public void run() {

//		This will reset the mines

		MineFileManager mineManager = MineFileManager.getFileManager();

		mineManager.getResetCommand();

//		Broadcast the reset message
		UtilsMessage.broadcastMessage("&aThe mines have been reset!");


	}

	public static MinesTask getInstance() {
		return instance;
	}

}

package me.firedragon5.islanddefender.task;

import me.firedragon5.islanddefender.filemanager.mines.MineFileManager;
import me.firedraong5.firesapi.utils.UtilsMessage;

import java.util.logging.Logger;

public class MinesTask implements Runnable {

	//	Instance
	private static final MinesTask instance = new MinesTask();

	@Override
	public void run() {

//		This will reset the mines

		MineFileManager mineManager = MineFileManager.getFileManager();

		mineManager.getResetCommand();

//		Broadcast the reset message
		UtilsMessage.broadcastMessage("&aThe mines have been reset!");
		Logger.getLogger("MinesTask").info("The mines have been reset!");


	}

	public static MinesTask getInstance() {
		return instance;
	}

}

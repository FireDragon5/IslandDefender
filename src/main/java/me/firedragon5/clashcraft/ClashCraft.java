package me.firedragon5.clashcraft;

import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClashCraft extends JavaPlugin {

    ClanFolderManager clanManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        clanManager = ClanFolderManager.getFileManager();
        clanManager.setup();
        clanManager.loadClanConfig();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        clanManager.saveClanConfig();

    }
}

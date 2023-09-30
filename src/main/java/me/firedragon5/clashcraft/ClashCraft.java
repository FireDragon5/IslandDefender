package me.firedragon5.clashcraft;


import me.firedragon5.clashcraft.commands.clans.ClanCommands;
import me.firedragon5.clashcraft.events.JoinEvent;
import me.firedragon5.clashcraft.filemanager.clans.ClanFolderManager;
import me.firedragon5.clashcraft.menu.clan.ClanInfoMenu;
import org.bukkit.plugin.java.JavaPlugin;


public final class ClashCraft extends JavaPlugin {

    ClanFolderManager clanManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        clanManager = ClanFolderManager.getFileManager();
        clanManager.setup();
        clanManager.loadClanConfig();
        clanManager.checkClanConfig();


//        register Events
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new Utils(), this);
        getServer().getPluginManager().registerEvents(new ClanInfoMenu(), this);

//        register Commands
        getCommand("clan").setExecutor(new ClanCommands());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        clanManager.saveClanConfig();

    }
}

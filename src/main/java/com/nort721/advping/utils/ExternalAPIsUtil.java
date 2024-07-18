package com.nort721.advping.utils;

import com.nort721.advping.AdvPing;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class ExternalAPIsUtil implements Listener {

    public static boolean isViaVersion;

    public ExternalAPIsUtil(AdvPing plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        isViaVersion = PluginUtil.exists("ViaVersion");
    }

    @EventHandler
    public void pluginDisableEvent(PluginDisableEvent event) {
        switch (event.getPlugin().getName()) {
            case "ViaVersion":
                isViaVersion = false;
                break;
        }
    }

}

package com.nort721.advping.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@UtilityClass
public class PluginUtil {
    public static boolean exists(String name) {
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            if (p.isEnabled() && p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static String getPluginVersion(String name) {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.isEnabled() && plugin.getName().equalsIgnoreCase(name)) {
                return plugin.getDescription().getVersion();
            }
        }
        return "";
    }

    public static String getProtocolLibVersion() {
        return getPluginVersion("ProtocolLib");
    }
}

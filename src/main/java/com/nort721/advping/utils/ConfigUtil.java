package com.nort721.advping.utils;

import com.nort721.advping.AdvPing;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ConfigUtil {

    @Setter
    //@ConfigValue(path = "Messages.No permissions message", colorChar = '&')
    private String noPermissionsMessage;

    //@ConfigValue(path = "prefix", colorChar = '&')
    public static String PREFIX;

    private String pingCooldownMessage;

    public ConfigUtil() {
        PREFIX = getMessageFromConfig("prefix");
        noPermissionsMessage = getMessageFromConfig("noPermissionsMessage");
        pingCooldownMessage = getMessageFromConfig("pingCooldownMessage");
    }

    public static boolean translateStringToBool(String str) {
        return (str.equalsIgnoreCase("Enabled") || str.equalsIgnoreCase("Enable"));
    }

    public static String getMessageFromConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(AdvPing.getInstance().getConfig().getString(path, "configuration reading error")));
    }

    public static String getStringFromConfig(String path) {
        return AdvPing.getInstance().getConfig().getString(path, "none");
    }

    public static int getIntFromConfig(String path) {
        return AdvPing.getInstance().getConfig().getInt(path, 0);
    }

    public static double getDoubleFromConfig(String path) {
        return AdvPing.getInstance().getConfig().getDouble(path, 0);
    }

    public static boolean getBooleanFromConfig(String path) {
        return AdvPing.getInstance().getConfig().getBoolean(path, false);
    }

    public static List<String> getStringListFromConfig(String path) {
        return AdvPing.getInstance().getConfig().getStringList(path);
    }

    public static ArrayList<Integer> getIntegerListFromConfig(String path) {
        return (ArrayList<Integer>) AdvPing.getInstance().getConfig().getIntegerList(path);
    }
}

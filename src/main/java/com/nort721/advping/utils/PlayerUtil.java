package com.nort721.advping.utils;

import com.nort721.advping.data.Profile;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class PlayerUtil {

    /**
     * Checks if the player is suspected to be using ping spoof
     *
     * @param profile the players connection data
     * @return True if they are suspected, otherwise False
     */
    public static boolean isPingSpoofSuspicion(Profile profile) {
        return profile.getKeepAlivePing() - 200 > profile.getTransactionPing();
    }

    public static int getBukkitPing(Player player) {
        if (player == null || !player.isOnline()) return -1;
        if (ServerVersion.getServerVersion().isOrAbove(ServerVersion.V1_17))
            return player.getPing();
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

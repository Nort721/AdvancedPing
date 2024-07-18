package com.nort721.advping.api;

import com.nort721.advping.data.Profile;
import com.nort721.advping.data.ProfileManager;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class AdvancedPingAPI {
    public static long GetPing(Player player) throws AdvancedPingException {
        Profile profile = ProfileManager.getProfile(player.getUniqueId());
        if (profile == null)
            throw new AdvancedPingException("Can't find player's profile");
        return (int) profile.getKeepAlivePing();
    }

    public static long GetTransactionPing(Player player) throws AdvancedPingException {
        Profile profile = ProfileManager.getProfile(player.getUniqueId());
        if (profile == null)
            throw new AdvancedPingException("Can't find player's profile");
        return (int) profile.getTransactionPing();
    }

    public static boolean IsAcceptingTransactions(Player player) throws AdvancedPingException {
        Profile profile = ProfileManager.getProfile(player.getUniqueId());
        if (profile == null)
            throw new AdvancedPingException("Can't find player's profile");
        return profile.isTransactionTimeOut();
    }
}

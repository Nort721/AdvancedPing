package com.nort721.advping.data;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    @Getter private static final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();

    public static void addProfile(UUID uuid, Profile profile) {
        profiles.put(uuid, profile);
    }

    public static void addProfile(Player player) {
        profiles.put(player.getUniqueId(), new Profile(player));
    }

    public static void removeProfile(UUID uuid) {
        profiles.remove(uuid);
    }

    public static Profile getProfile(UUID uuid) { return profiles.get(uuid); }

    public static boolean hasProfile(UUID uuid) {
        return profiles.containsKey(uuid);
    }
}

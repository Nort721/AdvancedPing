package com.nort721.advping.listeners;

import com.nort721.advping.AdvPing;
import com.nort721.advping.data.Profile;
import com.nort721.advping.data.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class BukkitListener implements Listener {

    public BukkitListener(AdvPing plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (ProfileManager.hasProfile(player.getUniqueId()))
            ProfileManager.removeProfile(player.getUniqueId());

        ProfileManager.addProfile(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (ProfileManager.hasProfile(player.getUniqueId())) {

            Profile profile = ProfileManager.getProfile(player.getUniqueId());

            profile.getProcessorsManager().getProcessors().forEach(processor -> processor.getBukkitTasks().forEach(BukkitTask::cancel));

            ProfileManager.removeProfile(player.getUniqueId());
        }
    }
}

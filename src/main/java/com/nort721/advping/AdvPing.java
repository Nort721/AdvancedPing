package com.nort721.advping;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.nort721.advping.commands.PingCommand;
import com.nort721.advping.data.ProfileManager;
import com.nort721.advping.listeners.BukkitListener;
import com.nort721.advping.listeners.PacketListener;
import com.nort721.advping.utils.ConfigUtil;
import com.nort721.advping.utils.ExternalAPIsUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class AdvPing extends JavaPlugin {

    private ConfigUtil configUtil;
    private final List<Listener> bukkitListeners = new ArrayList<>();
    private final List<PacketAdapter> packetAdapters = new ArrayList<>();

    public static AdvPing getInstance() {
        return getPlugin(AdvPing.class);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        configUtil = new ConfigUtil();

        bukkitListeners.add(new BukkitListener(this));
        bukkitListeners.add(new ExternalAPIsUtil(this));

        packetAdapters.add(new PacketListener());

        Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCommand());

        // support for reloads
        for (Player player : Bukkit.getOnlinePlayers())
            ProfileManager.addProfile(player);

        sendConsoleMessage("has been enabled");
    }

    @Override
    public void onDisable() {
        bukkitListeners.forEach(HandlerList::unregisterAll);
        packetAdapters.forEach(ProtocolLibrary.getProtocolManager()::removePacketListener);
        sendConsoleMessage("has been disabled");
    }

    public void sendConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + getDescription().getName() + "] " + ChatColor.RESET + msg);
    }
}

package com.nort721.advping.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.nort721.advping.AdvPing;
import com.nort721.advping.data.Profile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@Getter
public abstract class Processor {

    protected final AdvPing plugin;
    private final Queue<BukkitTask> bukkitTasks = new LinkedList<>();
    private final Set<PacketType> packetTypes = new HashSet<>();
    protected final Profile profile;
    protected final Player player;
    @Setter
    protected long currentTime;

    public Processor(Profile profile) {
        plugin = AdvPing.getInstance();
        this.profile = profile;
        player = profile.getBukkitPlayer();
    }

    protected void registerPacketTypes(Collection<PacketType> types) {
        packetTypes.addAll(types);
    }

    protected void registerPacketType(PacketType packetType) {
        packetTypes.add(packetType);
    }

    protected void addBukkitTask(BukkitTask bukkitTask) {
        bukkitTasks.add(bukkitTask);
    }

    public void handlePacketReceiving(PacketEvent packet) {
        if (!packetTypes.contains(packet.getPacketType())) return;
        onPacketReceiving(packet);
    }

    public void handlePacketSending(PacketEvent packet) {
        if (!packetTypes.contains(packet.getPacketType())) return;
        onPacketSending(packet);
    }

    public void onPacketReceiving(PacketEvent packet) {
    }

    public void onPacketSending(PacketEvent packet) {
    }
}

package com.nort721.advping.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.server.TemporaryPlayer;
import com.nort721.advping.AdvPing;
import com.nort721.advping.data.Profile;
import com.nort721.advping.data.ProfileManager;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PacketListener extends PacketAdapter {

    public PacketListener() {
        super(AdvPing.getInstance(), ListenerPriority.LOWEST, getSupportedPacketTypes());
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        if (player instanceof TemporaryPlayer) return;

        if (!ProfileManager.hasProfile(player.getUniqueId())) return;

        Profile profile = ProfileManager.getProfile(player.getUniqueId());

        long currentTime = System.currentTimeMillis();

        profile.getProcessorsManager().getProcessors().forEach(processor -> {
            processor.setCurrentTime(currentTime);
            processor.handlePacketReceiving(event);
        });
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();

        if (player instanceof TemporaryPlayer) return;

        if (!ProfileManager.hasProfile(player.getUniqueId())) return;

        Profile profile = ProfileManager.getProfile(player.getUniqueId());

        long currentTime = System.currentTimeMillis();

        profile.getProcessorsManager().getProcessors().forEach(processor -> {
            processor.setCurrentTime(currentTime);
            processor.handlePacketSending(event);
        });
    }

    /**
     * @return - All supported Packet Types for the server's corresponding version
     * (Earlier and later versions may contain packets that have not been implemented to the server's version)
     * <p>
     * I also hate how it has to be static. Thanks ProtocolLib!
     */
    private static Iterable<PacketType> getSupportedPacketTypes() {
        final Iterator<PacketType> packetTypeIterator = PacketType.values().iterator();

        List<PacketType> packets = StreamSupport.stream(Spliterators.spliteratorUnknownSize(packetTypeIterator, Spliterator.ORDERED), false)
                .filter(PacketType::isSupported)
                .collect(Collectors.toList());

        // fix for red server ping in server menu
        packets.remove(PacketType.Status.Server.PONG);

        // No need to run this in parallel. Will only be called once upon server start.
        return packets;
    }
}

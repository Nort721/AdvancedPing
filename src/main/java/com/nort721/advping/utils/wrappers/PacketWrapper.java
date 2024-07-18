package com.nort721.advping.utils.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Preconditions;
import com.nort721.advping.utils.Packets;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

@Getter
public abstract class PacketWrapper {
    protected PacketEvent packetEvent;
    protected PacketContainer packetData;

    /**
     * Constructs a new strongly typed wrapper for the given packet.
     *
     * @param packetEvent - the packet event.
     * @param type        - the packet type.
     */
    protected PacketWrapper(PacketEvent packetEvent, PacketType type) {
        Preconditions.checkNotNull(packetEvent, "Packet event cannot be NULL.");
        /* This is here because we are using the flying wrapper for types of Position and Look as well */
        if (!Packets.FLYING.contains(type) && !Packets.TRANSACTION_CLIENT.contains(type) && !Packets.TRANSACTION_SERVER.contains(type)) {
            Preconditions.checkArgument(packetEvent.getPacketType() == type, packetEvent.getPacketType() + " is not a packet of type " + type);
        }
        this.packetEvent = packetEvent;
        this.packetData = packetEvent.getPacket();
    }

    /**
     * Constructs a new strongly typed wrapper for the given packet.
     *
     * @param type - the packet type.
     */
    protected PacketWrapper(PacketContainer packetContainer, PacketType type) {
        Preconditions.checkNotNull(packetContainer, "Packet container cannot be NULL.");
        /* This is here because we are using the flying wrapper for types of Position and Look as well */
        if (!Packets.FLYING.contains(type) && !Packets.TRANSACTION_CLIENT.contains(type) && !Packets.TRANSACTION_SERVER.contains(type)) {
            Preconditions.checkArgument(packetContainer.getType() == type, packetContainer.getType() + " is not a packet of type " + type);
        }
        this.packetData = packetContainer;
    }

    public PacketType getPacketType() {
        return packetEvent.getPacketType();
    }

    /**
     * Send this packet to the target
     *
     * @param target - the target
     * @throws RuntimeException If the packet cannot be sent.
     */
    public void sendPacket(Player target) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(target, packetEvent.getPacket());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send this packet to all online players.
     */
    public void broadcastPacket() {
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(packetEvent.getPacket());
    }
}


package com.nort721.advping.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

@UtilityClass
public class PacketUtil {

    /*
    public static void sendTransactionPacket(Player target, int id) {
        PacketContainer packet;
        if (ServerVersion.getServerVersion().isOrAbove(ServerVersion.V1_17)) {
            packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PING);
            packet.getIntegers().write(0, id);
        } else {
            packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TRANSACTION);
            packet.getIntegers().write(0, 0);
            packet.getShorts().write(0, (short) id);
            packet.getSpecificModifier(boolean.class).write(0, false);
        }
        ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
    }
    */

    public static void sendTransactionPacket(Player target, int id) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.TRANSACTION);
        packet.getIntegers().write(0, 0);
        packet.getShorts().write(0, (short) id);
        packet.getSpecificModifier(boolean.class).write(0, false);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(target, packet);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.nort721.advping.utils.wrappers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

public class WrapperPlayServerTransaction extends PacketWrapper {
    public static final PacketType TYPE = PacketType.Play.Server.TRANSACTION;
    private final int UID;

    public WrapperPlayServerTransaction(PacketEvent packetEvent) {
        super(packetEvent, TYPE);
        UID = packetData.getShorts().read(0);
    }

    /**
     * Retrieve Action number.
     * <p>
     * Notes: every action that is to be accepted has a unique number. This
     * field corresponds to that number.
     *
     * @return The current Action number
     */
    public int getUID() {
        return UID;
    }

}

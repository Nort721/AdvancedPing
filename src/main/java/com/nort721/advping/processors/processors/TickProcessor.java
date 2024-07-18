package com.nort721.advping.processors.processors;

import com.comphenix.protocol.events.PacketEvent;
import com.nort721.advping.utils.Packets;
import com.nort721.advping.data.Profile;
import com.nort721.advping.data.TickHolder;
import com.nort721.advping.processors.Processor;

public class TickProcessor extends Processor {

    public TickProcessor(Profile profile) {
        super(profile);
        registerPacketTypes(Packets.FLYING);
    }

    @Override
    public void onPacketReceiving(PacketEvent packet) {
        profile.setTotalTicks(profile.getTotalTicks() + 1);
        TickHolder.tickHolders.forEach(tickHolder -> tickHolder.setAction(tickHolder.getAction() + 1));
    }

}

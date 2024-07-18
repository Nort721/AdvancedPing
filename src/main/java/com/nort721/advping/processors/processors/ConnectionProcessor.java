package com.nort721.advping.processors.processors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.nort721.advping.utils.Packets;
import com.nort721.advping.data.Profile;
import com.nort721.advping.processors.Processor;
import com.nort721.advping.utils.ServerVersion;
import com.nort721.advping.utils.wrappers.WrapperPlayClientTransaction;
import com.nort721.advping.utils.wrappers.WrapperPlayServerTransaction;

import java.util.Arrays;

public class ConnectionProcessor extends Processor {

    private static final int TRANSACTION_TIMEOUT = 20 * 15; // 15 seconds

    public ConnectionProcessor(Profile profile) {
        super(profile);
        registerPacketTypes(Packets.FLYING);
        registerPacketTypes(Arrays.asList(
                PacketType.Play.Client.KEEP_ALIVE,
                PacketType.Play.Server.KEEP_ALIVE));
        registerPacketTypes(Packets.TRANSACTION_CLIENT);
        registerPacketTypes(Packets.TRANSACTION_SERVER);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {

        if (Packets.TRANSACTION_CLIENT.contains(event.getPacketType())) {
            WrapperPlayClientTransaction transactionPacket = new WrapperPlayClientTransaction(event);

            int uid = transactionPacket.getUID();

            if (profile.getExpectedTransactions().containsKey(uid)) {

                profile.setTransactionTimeOut(false);

                long transactionPing = currentTime - profile.getExpectedTransactions().remove(uid);

                /* from NetHandlerPlayServer.class, function processKeepAlive, line 1193 */
                /* same calculation that the client does, I assume its to make the ping value smoother */
                long transactionPingAvg = (profile.getTransactionPing() * 3 + transactionPing) / 4;

                profile.setTransactionPing(transactionPingAvg);

                // if the transaction that was received has a registered task to it, execute the task
                if (profile.getTransactionsTasks().containsKey(uid)) {

                    //System.out.println("executing task id: " + uid);

                    // removing the scheduled task and executing it
                    profile.getTransactionsTasks().remove(uid).accept(uid);
                }

            }

        } else if (event.getPacketType() == PacketType.Play.Client.KEEP_ALIVE) {

            long keepAliveId = ServerVersion.getServerVersion().isOrAbove(ServerVersion.V1_12) ? event.getPacket().getLongs().read(0) : (long) event.getPacket().getIntegers().read(0);

            if (profile.getExpectedKeepAlives().containsKey(keepAliveId)) {

                long keepAlivePing = currentTime - profile.getExpectedKeepAlives().remove(keepAliveId);

                /* from NetHandlerPlayServer.class, function processKeepAlive, line 1193 */
                /* same calculation that the client does, I assume its to make the ping value smoother */
                long keepAlivePingAvg = (profile.getKeepAlivePing() * 3 + keepAlivePing) / 4;

                profile.setKeepAlivePing(keepAlivePingAvg);

                /* client doesn't response to transactions, some clients does this to mess up transaction ping calculations of anticheats */
                if (profile.getExpectedTransactions().size() > TRANSACTION_TIMEOUT) {

                    /* to prevent memory leaking */
                    profile.getExpectedTransactions().clear();

                    profile.setTransactionTimeOut(true);

                }

            }

        }

    }

    @Override
    public void onPacketSending(PacketEvent event) {

        if (Packets.TRANSACTION_SERVER.contains(event.getPacketType())) {
            WrapperPlayServerTransaction transactionPacket = new WrapperPlayServerTransaction(event);

            int uid = transactionPacket.getUID();

            if (!profile.getExpectedTransactions().containsKey(uid))
                profile.getExpectedTransactions().put(uid, currentTime);

        } else if (event.getPacketType() == PacketType.Play.Server.KEEP_ALIVE) {

            long keepAliveId = (long) event.getPacket().getIntegers().read(0);

            profile.getExpectedKeepAlives().put(keepAliveId, currentTime);

        }

    }

}

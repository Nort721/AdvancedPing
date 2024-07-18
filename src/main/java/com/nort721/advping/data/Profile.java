package com.nort721.advping.data;

import com.comphenix.protocol.ProtocolLibrary;
import com.nort721.advping.processors.ProcessorsManager;
import com.nort721.advping.utils.ClientVersion;
import com.nort721.advping.utils.ExternalAPIsUtil;
import com.nort721.advping.utils.PacketUtil;
import com.viaversion.viaversion.api.Via;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@Getter
@Setter
public class Profile {

    // general
    private final Player bukkitPlayer;
    private ClientVersion clientVersion;
    private final ProcessorsManager processorsManager;

    // connection tracking
    private long keepAlivePing;
    private long lastKeepAlivePing;
    private long transactionPing;
    private long lastTransactionPing;
    private boolean transactionTimeOut = true;
    private final HashMap<Long, Long> expectedKeepAlives = new HashMap<>();
    private final HashMap<Integer, Long> expectedTransactions = new HashMap<>();
    private final Map<Integer, Consumer<Integer>> transactionsTasks = new HashMap<>();

    // timing
    private int totalTicks;
    private final TickHolder lastPacketDrop = new TickHolder();
    private final TickHolder lastPingCommand = new TickHolder();

    public Profile(Player player) {
        registerClientVersion(player);
        bukkitPlayer = player;
        processorsManager = new ProcessorsManager(this);
    }

    private void registerClientVersion(Player player) {
        if (ExternalAPIsUtil.isViaVersion) {
            clientVersion = ClientVersion.fetchClientVersion(Via.getAPI().getPlayerVersion(player.getUniqueId()));
        } else { // no protocol hack plugin, will get client version on our own
            clientVersion = ClientVersion.fetchClientVersion(ProtocolLibrary.getProtocolManager().getProtocolVersion(player));
        }
    }

    /**
     * Sends a transaction with {@param callback} bound to it which will be called upon the client
     * sending the same transaction uid back
     *
     * @param callback The callback
     */
    public void registerTransactionTask(Consumer<Integer> callback) {
        if (transactionsTasks.size() < 10) {
            int id = ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, 0);
            transactionsTasks.put(id, callback);
            PacketUtil.sendTransactionPacket(bukkitPlayer, id);
        }
    }
}

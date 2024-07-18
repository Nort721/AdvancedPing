package com.nort721.advping.commands;

import com.nort721.advping.AdvPing;
import com.nort721.advping.data.Profile;
import com.nort721.advping.data.ProfileManager;
import com.nort721.advping.utils.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ConfigUtil.getMessageFromConfig("prefix") + "command doesn't support console sender.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ap.command.ping") && !player.hasPermission("ap.admin")) {
            player.sendMessage(AdvPing.getInstance().getConfigUtil().getNoPermissionsMessage());
            return true;
        }

        if (!ProfileManager.hasProfile(player.getUniqueId())) return true;

        Profile profile = ProfileManager.getProfile(player.getUniqueId());

        if (!profile.getLastPingCommand().hasPassed(60)) {
            player.sendMessage(AdvPing.getInstance().getConfigUtil().getPingCooldownMessage());
            return true;
        }

        profile.getLastPingCommand().setAction(0);

        profile.registerTransactionTask((uid) -> {

            player.sendMessage(ConfigUtil.getMessageFromConfig("prefix") + ChatColor.GRAY + "Your Ping: " + ChatColor.BLUE + profile.getKeepAlivePing()
                    + ChatColor.GRAY + "\n" + "Your Secure Ping: " + ChatColor.BLUE + profile.getTransactionPing());

        });

        return true;
    }
}

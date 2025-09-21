package com.thomas.staff.listeners;

import com.thomas.staff.StaffFeaturesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {

    private final StaffFeaturesPlugin plugin;

    public StaffChatListener(StaffFeaturesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (plugin.isStaffChatEnabled() && player.hasPermission("stafffeatures.staffchat")) {
            event.setCancelled(true);

            // Grab format from config
            String format = plugin.getConfig().getString(
                    "messages.staffChatFormat",
                    "[StaffChat] {prefix}{player} >> {message}"
            );

            // TODO: Integrate with Vault
            String prefix = ""; 
            if (player.getDisplayName() != null) {
                prefix = player.getDisplayName().replace(player.getName(), ""); // crude way
            }

            format = format.replace("{prefix}", prefix)
                           .replace("{player}", player.getName())
                           .replace("{message}", event.getMessage());

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("stafffeatures.staffchat")) {
                    online.sendMessage(plugin.getPrefix() + " " + format);
                }
            }
        }
    }
}

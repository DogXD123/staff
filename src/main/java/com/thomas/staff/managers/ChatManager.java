package com.thomas.staff.managers;

import com.thomas.staff.StaffFeaturesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    private final StaffFeaturesPlugin plugin;
    private boolean chatMuted = false;

    public ChatManager(StaffFeaturesPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }

    public void toggleChatMute() {
        chatMuted = !chatMuted;
        String status = chatMuted ? "§cmuted" : "§aunmuted";
        Bukkit.broadcastMessage(plugin.getPrefix() + " Chat has been " + status + "!");
    }

    public void clearChat() {
        for (int i = 0; i < 100; i++) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("");
            }
        }
        Bukkit.broadcastMessage(plugin.getPrefix() + " Chat has been cleared!");
    }

    public StaffFeaturesPlugin getPlugin() {
        return plugin;
    }
}

package com.thomas.staff.listeners;

import com.thomas.staff.StaffFeaturesPlugin;
import com.thomas.staff.managers.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManagerListener implements Listener {

    private final ChatManager chatManager;
    private final StaffFeaturesPlugin plugin;

    public ChatManagerListener(ChatManager chatManager, StaffFeaturesPlugin plugin) {
        this.chatManager = chatManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (chatManager.isChatMuted() && !player.hasPermission("stafffeatures.chat.bypass")) {
            event.setCancelled(true);
            String mutedMessage = plugin.getConfig().getString("messages.chatMuted", "Chat is currently muted!");
            player.sendMessage("§c" + mutedMessage);
        }
    }
}

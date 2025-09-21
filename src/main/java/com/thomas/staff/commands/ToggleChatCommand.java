package com.thomas.staff.commands;

import com.thomas.staff.StaffFeaturesPlugin;
import com.thomas.staff.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleChatCommand implements CommandExecutor {

    private final ChatManager chatManager;
    private final StaffFeaturesPlugin plugin;

    public ToggleChatCommand(ChatManager chatManager, StaffFeaturesPlugin plugin) {
        this.plugin = plugin;
        this.chatManager = chatManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffeatures.chat.toggle")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }
        chatManager.toggleChatMute();
        return true;
    }
}

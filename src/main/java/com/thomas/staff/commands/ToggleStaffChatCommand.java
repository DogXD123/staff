package com.thomas.staff.commands;

import com.thomas.staff.StaffFeaturesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleStaffChatCommand implements CommandExecutor {

    private final StaffFeaturesPlugin plugin;

    public ToggleStaffChatCommand(StaffFeaturesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffeatures.toggle.staffchat")) {
            sender.sendMessage(plugin.getNoPermissionMessage());
            return true;
        }

        plugin.toggleStaffChat();
        String msg = plugin.getConfig().getString("messages.helpMessage.togglestaffchat")
                .replace("{status}", plugin.isStaffChatEnabled() ? "§aON" : "§cOFF");
        sender.sendMessage(msg);
        return true;
    }
}

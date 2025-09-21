package com.thomas.staff.commands;

import com.thomas.staff.StaffFeaturesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleVanishCommand implements CommandExecutor {

    private final StaffFeaturesPlugin plugin;

    public ToggleVanishCommand(StaffFeaturesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffeatures.toggle.vanish")) {
            sender.sendMessage(plugin.getNoPermissionMessage());
            return true;
        }

        plugin.toggleVanish();
        String msg = plugin.getConfig().getString("messages.helpMessage.togglevanish")
                .replace("{status}", plugin.isVanished() ? "§aON" : "§cOFF");
        sender.sendMessage(msg);
        return true;
    }
}

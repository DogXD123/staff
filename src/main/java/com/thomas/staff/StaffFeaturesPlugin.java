package com.thomas.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffFeaturesPlugin extends JavaPlugin {

    private boolean vanished = false;
    private boolean staffChat = true;
    private boolean someFeature = false;

    @Override
    public void onEnable() {
        getLogger().info("StaffFeatures enabled!");
        saveDefaultConfig();

        // Start repeating task to update action bars every 2 seconds
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("stafffeatures.use")) {
                    String format = getConfig().getString("messages.actionBarFormat");
                    String message = format
                            .replace("{vanish}", vanished ? "§aON" : "§cOFF")
                            .replace("{staffchat}", staffChat ? "§aON" : "§cOFF")
                            .replace("{feature}", someFeature ? "§aON" : "§cOFF");

                    player.sendActionBar(Component.text(message));
                }
            }
        }, 0L, 40L);
    }

    @Override
    public void onDisable() {
        getLogger().info("StaffFeatures disabled!");
    }

    private void sendNoPermission(CommandSender sender) {
        String prefix = getConfig().getString("messages.prefix");
        String msg = getConfig().getString("messages.noPermission").replace("{prefix}", prefix);
        sender.sendMessage(msg);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("togglevanish")) {
            if (!sender.hasPermission("stafffeatures.toggle.vanish")) {
                sendNoPermission(sender);
                return true;
            }
            vanished = !vanished;
            String msg = getConfig().getString("messages.helpMessage.togglevanish")
                    .replace("{status}", vanished ? "§aON" : "§cOFF");
            sender.sendMessage(msg);
            return true;
        }

        if (command.getName().equalsIgnoreCase("togglestaffchat")) {
            if (!sender.hasPermission("stafffeatures.toggle.staffchat")) {
                sendNoPermission(sender);
                return true;
            }
            staffChat = !staffChat;
            String msg = getConfig().getString("messages.helpMessage.togglestaffchat")
                    .replace("{status}", staffChat ? "§aON" : "§cOFF");
            sender.sendMessage(msg);
            return true;
        }

        if (command.getName().equalsIgnoreCase("togglefeature")) {
            if (!sender.hasPermission("stafffeatures.toggle.feature")) {
                sendNoPermission(sender);
                return true;
            }
            someFeature = !someFeature;
            String msg = getConfig().getString("messages.helpMessage.togglefeature")
                    .replace("{status}", someFeature ? "§aON" : "§cOFF");
            sender.sendMessage(msg);
            return true;
        }

        return false;
    }
}

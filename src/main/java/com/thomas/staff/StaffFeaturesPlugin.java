package com.thomas.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffFeaturesPlugin extends JavaPlugin {

    private boolean vanished = false;
    private boolean staffChat = true;
    private boolean someFeature = false;

    @Override
    public void onEnable() {
        getLogger().info("StaffFeatures enabled!");

        // /togglevanish command
        getCommand("togglevanish").setExecutor((sender, cmd, label, args) -> {
            vanished = !vanished;
            sender.sendMessage("§eVanished: " + (vanished ? "§aON" : "§cOFF"));
            return true;
        });

        // /togglestaffchat command
        getCommand("togglestaffchat").setExecutor((sender, cmd, label, args) -> {
            staffChat = !staffChat;
            sender.sendMessage("§bStaff Chat: " + (staffChat ? "§aON" : "§cOFF"));
            return true;
        });

        // /togglefeature command
        getCommand("togglefeature").setExecutor((sender, cmd, label, args) -> {
            someFeature = !someFeature;
            sender.sendMessage("§aFeature: " + (someFeature ? "§aON" : "§cOFF"));
            return true;
        });

        // Repeating task to update action bars
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("stafffeatures.use")) {
                    String message = String.format(
                            "§eVanished: %s §7| §bStaff Chat: %s §7| §aFeature: %s",
                            vanished ? "§aON" : "§cOFF",
                            staffChat ? "§aON" : "§cOFF",
                            someFeature ? "§aON" : "§cOFF"
                    );
                    player.sendActionBar(Component.text(message));
                }
            }
        }, 0L, 40L);
    }

    @Override
    public void onDisable() {
        getLogger().info("StaffFeatures disabled!");
    }
}

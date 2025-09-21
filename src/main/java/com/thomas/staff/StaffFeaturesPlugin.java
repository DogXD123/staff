package com.thomas.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffFeaturesPlugin extends JavaPlugin implements Listener {

    private boolean vanished = false;
    private boolean staffChatEnabled = true;

    @Override
    public void onEnable() {
        getLogger().info("StaffFeatures enabled!");
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);

        // Start repeating task to update action bars every 2 seconds
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("stafffeatures.use")) {
                    String format = getConfig().getString("messages.actionBarFormat");
                    String message = format
                            .replace("{vanish}", vanished ? "§aON" : "§cOFF")
                            .replace("{staffchat}", staffChatEnabled ? "§aON" : "§cOFF");

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
            staffChatEnabled = !staffChatEnabled;
            String msg = getConfig().getString("messages.helpMessage.togglestaffchat")
                    .replace("{status}", staffChatEnabled ? "§aON" : "§cOFF");
            sender.sendMessage(msg);
            return true;
        }

        return false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (staffChatEnabled && player.hasPermission("stafffeatures.staffchat")) {
            event.setCancelled(true); // cancel normal chat

            String prefix = getConfig().getString("messages.prefix");
            String format = "§b[StaffChat] §e" + player.getName() + ": §f" + event.getMessage();

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("stafffeatures.staffchat")) {
                    online.sendMessage(prefix + " " + format);
                }
            }
        }
    }
}

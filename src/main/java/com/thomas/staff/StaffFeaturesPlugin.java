package com.thomas.staff;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.thomas.staff.commands.ToggleVanishCommand;
import com.thomas.staff.commands.ToggleStaffChatCommand;
import com.thomas.staff.listeners.StaffChatListener;

public class StaffFeaturesPlugin extends JavaPlugin {

    private boolean vanished = false;
    private boolean staffChatEnabled = true;

    // -----------------------------
    // Getters & Toggle Methods
    // -----------------------------
    public boolean isVanished() {
        return vanished;
    }

    public void toggleVanish() {
        vanished = !vanished;
    }

    public boolean isStaffChatEnabled() {
        return staffChatEnabled;
    }

    public void toggleStaffChat() {
        staffChatEnabled = !staffChatEnabled;
    }

    // -----------------------------
    // Plugin Lifecycle
    // -----------------------------
    @Override
    public void onEnable() {
        getLogger().info("StaffFeatures enabled!");
        saveDefaultConfig();

        registerCommands();
        registerListeners();
        startActionBarUpdater();
    }

    @Override
    public void onDisable() {
        getLogger().info("StaffFeatures disabled!");
    }

    // -----------------------------
    // Config Helpers
    // -----------------------------
    public String getPrefix() {
        return getConfig().getString("messages.prefix", "§7[StaffFeatures]");
    }

    public String getNoPermissionMessage() {
        return getConfig()
                .getString("messages.noPermission", "§cYou don't have permission!")
                .replace("{prefix}", getPrefix());
    }

    // -----------------------------
    // Modular Methods
    // -----------------------------
    private void registerCommands() {
        getCommand("togglevanish").setExecutor(new ToggleVanishCommand(this));
        getCommand("togglestaffchat").setExecutor(new ToggleStaffChatCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new StaffChatListener(this), this);
    }

    private void startActionBarUpdater() {
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            String format = getConfig().getString("messages.actionBarFormat", "Vanish: {vanish} | StaffChat: {staffchat}");
            for (var player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("stafffeatures.use")) {
                    String message = format
                            .replace("{vanish}", vanished ? "§aON" : "§cOFF")
                            .replace("{staffchat}", staffChatEnabled ? "§aON" : "§cOFF");

                    player.sendActionBar(Component.text(message));
                }
            }
        }, 0L, 40L); // 2 seconds interval (40 ticks)
    }
}

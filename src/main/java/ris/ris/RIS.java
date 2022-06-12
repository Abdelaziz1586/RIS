package ris.ris;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ris.events.DeathEvent;

import java.util.HashMap;
import java.util.Objects;

public final class RIS extends JavaPlugin {

    private final HashMap<String, String> Permissions = new HashMap<>();
    private final HashMap<String, String> Messages = new HashMap<>();

    private final HashMap<Player, Location> SavedLocations = new HashMap<>();
    private final HashMap<Player, GameMode> SavedGameModes = new HashMap<>();

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
        Objects.requireNonNull(getCommand("back")).setExecutor(new Commands(this));
        Objects.requireNonNull(getCommand("ris")).setExecutor(new Commands(this));

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reload();

        getServer().getConsoleSender().sendMessage("§eRespawn On Death §ahas been enabled");

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§eRespawn On Death §chas been disabled");

    }

    public void setPermission(String key, String perm) {
        this.Permissions.remove(key);
        this.Permissions.put(key, perm);

    }

    public String getPermission(String key) {
        if (this.Permissions.containsKey(key))
            return this.Permissions.get(key);
        return null;

    }

    public void saveLocation(Player p) {
        this.SavedLocations.remove(p);
        this.SavedLocations.put(p, p.getLocation());

    }

    public Location getSavedLocation(Player p) {
        if (this.SavedLocations.containsKey(p))
            return this.SavedLocations.get(p);
        return null;

    }

    public void saveGameMode(Player p) {
        this.SavedGameModes.remove(p);
        this.SavedGameModes.put(p, p.getGameMode());

    }

    public GameMode getSavedGameMode(Player p) {
        if (this.SavedGameModes.containsKey(p))
            return this.SavedGameModes.get(p);
        return null;

    }

    public void setMessage(String key, String value) {
        this.Messages.remove(key);
        this.Messages.put(key, value);

    }

    public String getMessage(String key) {
        if (this.Messages.containsKey(key))
            return this.Messages.get(key);
        return null;

    }

    public void reload() {
        reloadConfig();

        setPermission("admin", getConfig().getString("permissions.admin"));
        setPermission("command", getConfig().getString("permissions.command"));

        setMessage("success", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("messages.success"))));
        setMessage("fail", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("messages.fail"))));
        setMessage("permission", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("messages.missing-permission"))));
        setMessage("death", ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("messages.death"))));

    }


}

package ris.ris;

import org.jetbrains.annotations.NotNull;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Commands implements CommandExecutor {

    private final RIS main;

    public Commands(RIS ris) {
        this.main = ris;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("back")) {
            if (s instanceof Player) {
                Player p = (Player) s;
                if (s.hasPermission(Objects.requireNonNull(main.getPermission("command")))) {
                    Location loc = main.getSavedLocation(p);
                    GameMode gameMode = main.getSavedGameMode(p);
                    if (p.getGameMode() == GameMode.SPECTATOR) {
                        if (loc != null && gameMode != null) {
                            p.teleport(loc);
                            p.setGameMode(gameMode);
                        } else {
                            p.setGameMode(GameMode.SURVIVAL);
                        }
                        p.sendMessage(Objects.requireNonNull(main.getMessage("success")));
                    } else {
                        p.sendMessage(Objects.requireNonNull(main.getMessage("fail")));
                    }
                } else {
                    p.sendMessage(Objects.requireNonNull(main.getMessage("permission")));
                }
            } else {
                s.sendMessage("§cThis command is only for players.");
            }
        } else if (cmd.getName().equalsIgnoreCase("ris")) {
            if (s.hasPermission(Objects.requireNonNull(main.getPermission("admin")))) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        main.reload();
                        s.sendMessage("§aReloaded the config.");
                    } else {
                        s.sendMessage("§c/ris (reload)");
                    }
                } else {
                    s.sendMessage("§c/ris (reload)");
                }
            } else {
                s.sendMessage(Objects.requireNonNull(main.getMessage("permission")));
            }
        }

        return true;
    }
}

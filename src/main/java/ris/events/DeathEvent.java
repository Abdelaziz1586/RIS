package ris.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ris.ris.RIS;

public class DeathEvent implements Listener {

    private final RIS main;

    public DeathEvent(RIS ris) {
        this.main = ris;

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player v = e.getEntity();
        Player a = v.getKiller();
        main.saveLocation(v);
        main.saveGameMode(v);
        v.setGameMode(GameMode.SPECTATOR);
        String message = main.getMessage("death");
        if (message != null && a != null)
            e.setDeathMessage(message.replace("%attacker%", a.getDisplayName()).replace("%victim%", v.getDisplayName()));

    }

}

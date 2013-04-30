package edgruberman.bukkit.bukkit_3604;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBedEnter(final PlayerBedEnterEvent enter) {
        this.logBlock("Enter Bed", enter.getPlayer(), enter.getBed().getLocation());
    }

    @EventHandler
    public void onBedLeave(final PlayerBedLeaveEvent leave) {
        this.logBlock("Leave Bed", leave.getPlayer(), leave.getBed().getLocation());
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent interaction) {
        if (interaction.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (interaction.getItem().getTypeId() != Material.BED.getId()) return;

        final Location bed = interaction.getPlayer().getBedSpawnLocation();
        this.logBlock("Bed Spawn", interaction.getPlayer(), bed);
    }

    private void logBlock(final String note, final Player player, final Location loc) {
        if (loc == null) {
            this.getLogger().log(Level.INFO, "{0}; player: {1}; null", new Object[] { note, player.getName() });
            return;
        }

        this.getLogger().log(Level.INFO, "{0}; player: {1}, world: {2}, x: {3}, y: {4}, z:{5}"
                , new Object[] { note, player.getName(), loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() });
    }

}

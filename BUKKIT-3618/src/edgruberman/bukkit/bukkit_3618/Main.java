package edgruberman.bukkit.bukkit_3618;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onSplash(final PotionSplashEvent splash) {
        this.getLogger().log(Level.INFO, "PotionSplashEvent; durability: {0,number,#}, effects: {1}, affected: {2}"
                , new Object[] { splash.getPotion().getItem().getDurability(), splash.getPotion().getEffects().size(), splash.getAffectedEntities().size() });
    }

}

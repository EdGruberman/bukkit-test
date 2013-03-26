package edgruberman.bukkit.bukkit_1959;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener, Runnable {

    private final List<ItemMergeEvent> events = new ArrayList<ItemMergeEvent>();
    private boolean cancel = false;
    private ItemStack entity = null;
    private ItemStack other = null;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("bukkit-1959:cancel")) {
            this.cancel = !this.cancel;
        } else if (command.getName().equals("bukkit-1959:entity")) {
            final Player player = (Player) sender;
            if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                this.entity = null;
            } else {
                this.entity = player.getItemInHand().clone();
            }
        } else if (command.getName().equals("bukkit-1959:other")) {
            final Player player = (Player) sender;
            if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                this.other = null;
            } else {
                this.other = player.getItemInHand().clone();
            }
        }

        this.getLogger().log(Level.INFO, "Cancel ItemMergeEvent: {0}", this.cancel);
        this.getLogger().log(Level.INFO, "Set Entity ItemStack: {0}", ( this.entity == null ? "null" : this.describeItemStack(this.entity) ));
        this.getLogger().log(Level.INFO, " Set Other ItemStack: {0}", ( this.other == null ? "null" : this.describeItemStack(this.other) ));

        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onMerge(final ItemMergeEvent merge) {
        this.events.add(merge);
        this.describe("*Initial*", "Entity", merge.getEntity());
        this.describe("*Initial*", " Other", merge.getOther());

        if (this.cancel) merge.setCancelled(true);
        if (this.entity != null) merge.getEntity().setItemStack(this.entity);
        if (this.other != null) merge.getOther().setItemStack(this.other);

        Bukkit.getScheduler().runTask(this, this);
    }

    @Override
    public void run() {
        for (final ItemMergeEvent merge : this.events) {
            this.describe("==Result" + ( merge.isCancelled()? ":CANCELLED" : "" ) + "=", "Entity", merge.getEntity());
            this.describe("==Result" + ( merge.isCancelled()? ":CANCELLED" : "" ) + "=", " Other", merge.getOther());
        }
        this.events.clear();
    }

    private void describe(final String prefix, final String which, final Item item) {
        if (item.isDead()) {
            this.getLogger().log(Level.INFO, "[{0}] {2,number,#} {1}: -DEAD-  |  {3}"
                    , new Object[] { prefix, which, item.getEntityId(), this.describeItemStack(item.getItemStack()) });
            return;
        }

        this.getLogger().log(Level.INFO, "[{0}] {2,number,#} {1}: age: {4}, delay: {5}  |  {3}"
                , new Object[] { prefix, which, item.getEntityId(), this.describeItemStack(item.getItemStack())
                , item.getTicksLived(), item.getPickupDelay() });
    }

    private String describeItemStack(final ItemStack stack) {
        final StringBuilder sb = new StringBuilder(stack.getType().name());
        if (stack.getDurability() != 0) sb.append('/').append(stack.getDurability());
        if (stack.hasItemMeta()) sb.append('*');
        if (stack.getAmount() != 0) sb.append(" x").append(stack.getAmount());
        return sb.toString();
    }

}

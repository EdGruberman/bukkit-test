package edgruberman.bukkit.bukkit_3371;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Player player = (Player) sender;
        final Location target = player.getTargetBlock(null, 100).getLocation();
        final BlockFace direction = BlockFace.valueOf(args[0].toUpperCase());

        this.getServer().broadcastMessage("target: " + target.getBlock().getType().name() + " " + direction.name() + " " + target.toString());

        if (command.getName().equals("create")) {
            this.create(target, direction);

        } else if (command.getName().equals("change")) {
            this.change(target, direction);
        }

        return true;
    }

    private void create(final Location target, final BlockFace direction) {
        final ItemFrame frame = target.getWorld().spawn(target, ItemFrame.class);
        this.getServer().broadcastMessage("create frame: " + frame.getLocation().getBlock().getType().name() + " " + frame.getFacing().name() + " " + frame.getLocation().toString());
        frame.setFacingDirection(direction, true);
    }

    private void change(final Location target, final BlockFace direction) {
        final Collection<ItemFrame> frames = target.getWorld().getEntitiesByClass(ItemFrame.class);
        this.getServer().broadcastMessage("frames found: " + frames.size());
        for (final ItemFrame frame : frames) {
            if (frame.getLocation().distance(target) > 2) continue;
            this.getServer().broadcastMessage("change frame: " + frame.getLocation().getBlock().getType().name() + " " + frame.getFacing().name() + " " + frame.getLocation().toString());
            frame.setFacingDirection(direction);
            break;
        }
    }

}

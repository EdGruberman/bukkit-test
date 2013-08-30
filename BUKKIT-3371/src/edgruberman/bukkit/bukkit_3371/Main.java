package edgruberman.bukkit.bukkit_3371;

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

        this.getServer().broadcastMessage(target.getBlock().getType().name() + " " + direction.name() + " " + target.toString());

        final ItemFrame frame = target.getWorld().spawn(target, ItemFrame.class);
        frame.setFacingDirection(direction, true);

        return true;
    }

}

package edgruberman.bukkit.bukkit_2368;

import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getConfig().set("integer", new Integer(-2147483648));
        this.getConfig().set("string", new String("String Value"));
        this.getConfig().set("long", new Long(9223372036854775807L));
        this.getConfig().set("true-boolean", Boolean.TRUE);
        this.getConfig().set("false-boolean", Boolean.FALSE);
        this.getConfig().set("vector", new Vector(12345.67, 64.0, -12345.6789));
        this.getConfig().set("list", Arrays.asList(1, 2, 3, 4, 5));
        this.getConfig().set("42", "The Answer");
        this.saveConfig();
    }

}

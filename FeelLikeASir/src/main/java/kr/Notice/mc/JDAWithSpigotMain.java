package kr.Notice.mc;

import kr.Notice.mc.listener.ChatSync;
import kr.Notice.mc.listener.JoinQuit;
import kr.Notice.mc.commands.MPCommand;
import kr.Notice.mc.utils.ConfigUtils;
import kr.Notice.mc.utils.DataContainor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

import static kr.Notice.bot.JDAWithSpigotBot.initJDA;
import static kr.Notice.bot.JDAWithSpigotBot.jda;
import static org.bukkit.Bukkit.getPluginCommand;
import static org.bukkit.Bukkit.getPluginManager;

@SuppressWarnings("all")
public class JDAWithSpigotMain extends JavaPlugin implements CommandExecutor {
    private static JDAWithSpigotMain plugin;
    public static YamlConfiguration config;
    private Logger log;
    public static String prefix;

    public static JDAWithSpigotMain getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        config = ConfigUtils.loadDefaultPluginConfig(plugin);
        log = getLogger();
        prefix = DataContainor.getPrefix();

        /* ## <- Setting Executor -> ## */
        getPluginCommand("DiscordCon").setExecutor(new MPCommand());

        /* ## <- Setting Event Listener -> ## */
        getPluginManager().registerEvents(new JoinQuit(), this);
        getPluginManager().registerEvents(new ChatSync(), this);

        try {
            initJDA();
        }
        catch (LoginException e) {
            e.printStackTrace();
        }

        log.info("[ <- DiscordCon Enabled -> ]");
    }

    @Override
    public void onDisable() {
        jda.shutdown();

        log.info("[ <- DiscordCon Disabled -> ]");
    }
}


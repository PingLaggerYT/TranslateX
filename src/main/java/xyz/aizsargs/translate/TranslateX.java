package xyz.aizsargs.translate;

import lombok.Getter;
import lombok.Setter;
import xyz.aizsargs.translate.command.TranslatorCommand;
import xyz.aizsargs.translate.data.Config;
import xyz.aizsargs.translate.data.ConfigFile;
import xyz.aizsargs.translate.translate.TranslateController;
import xyz.aizsargs.translate.util.ColorUtil;
import xyz.aizsargs.translate.util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public final class TranslateX extends JavaPlugin {

    @Getter private static TranslateX instance;
    @Setter private ConfigFile configFile;
    private Metrics metrics;

    private TranslateController translatorController;

    @Override
    public void onEnable() {
        instance = this;

        reloadConfig();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, this::startPlugin);
    }

    private void startPlugin() {
        this.log("&e                                                                                                    ");
        this.log("&e /$$$$$$$$                                     &f/$$              &f/$$               &f/$$   /$$");
        this.log("&e|__  $$__/                                    &f| $$              &f| $$              |&f $$  / $$");
        this.log("&e   | $$  /&f$$$$$$  &e/$$$$$$  &f/$$$$$$$    &e/$$$$$$$&f| $$  &e/$$$$$$  &f/$$$$$$    &e/$$$$$$ &f| &f$$/$$/");
        this.log("&e   | $$ /&f$$__  $$&e|____  $$| &f$$__  $$  &e/$$_____/&f| $$ &e|____  $$|_  &f$$_/   &e/$$__  $$ &f\\ &f$$$$/");
        this.log("&e   | $$| &f$$  \\__/ &e/$$$$$$$| &f$$  \\ &f$$&e | $$$$$$  &f| $$  &e/$$$$$$$  &f| $$    &e| $$$$$$$$ &f$$  $$ ");
        this.log("&e   | $$| &f$$      &e/$$__  $$| &f$$  | $$  &e\\____  $$&f| $$ &e/$$__  $$  | &f$$ /$$| &e$$_____/ &f/$$/\\ $$");
        this.log("&e   | $$| &f$$     &e|  $$$$$$$| &f$$  | $$ / &e$$$$$$$/| &f$$|  &e$$$$$$$  |  &f$$$$/|  &e$$$$$$$| &f$$  \\ $$");
        this.log("&e   |__/&f|__/      &e\\_______/&f|__/  |__/ &e|_______/ &f|__/ &e\\_______/   &f\\___/   &e\\_______/&f|__/  |__/");
        this.log("&e                                                                                                    ");
        this.log("&e&lDescription: &fTranslateX is a plugin that allows you to translate messages in your server.             ");
        this.log("&e&lAuthor: &fDjorr (Rubix Development)                                                             ");
        this.log("&eVersion: &f0.1-BETA                                                                             ");
        this.log("&eDiscord: &fhttps://discord.rubixdevelopment.nl/                                                        ");
        this.log("                                                                                                    ");

        this.translatorController = new TranslateController();
        this.translatorController.intializeTranslator();

        final PluginCommand command = this.getCommand("translator");
        if (command != null) {
            command.setExecutor(new TranslatorCommand());
            command.setTabCompleter(new TranslatorCommand());
            command.setAliases(Arrays.asList("t", "translatorx", "tx"));
            command.setPermission("translatorx.translate");
        }

        this.log("");
        this.log("- &aSuccesfully enabled &e&lTranslateX &aplugin.");
        this.log("&e                                                                                                    ");

        int pluginId = 19970;
        metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        if (this.translatorController != null) this.translatorController.disable();
        if (metrics != null) metrics.shutdown();
    }

    @Override
    public void reloadConfig() {
        this.configFile = new ConfigFile("config.yml");
        new Config();
        if (this.translatorController != null) {
            this.translatorController.reloadTranslator();
        }
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ColorUtil.translate(message));
    }
}

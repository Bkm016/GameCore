package me.skymc.gamecore.handler;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import lombok.Getter;
import me.skymc.gamecore.GameCore;
import me.skymc.taboolib.fileutils.FileUtils;

/**
 * @author Bkm016
 * @since 2018-04-21
 */
public class GameModuleHandler {
	
	@Getter
	private static HashMap<String, GameModule> modules = new HashMap<>();
	@Getter
	private static HashMap<String, FileConfiguration> configurations = new HashMap<>();
	
	public static void register(GameModule module) {
		modules.put(module.getName(), module);
	}
	
	public static void enableAll() {
		for (GameModule module : modules.values()) {
			if (GameCore.getInst().getConfig().getString("DisableModule").contains(module.getName())) {
				continue;
			}
			if (module instanceof Listener) {
				Bukkit.getPluginManager().registerEvents((Listener) module, GameCore.getInst());
			}
			if (module.enableConfig()) {
				loadConfig(module);
			}
			module.onEnable();
		}
	}
	
	public static void disableAll() {
		modules.values().forEach(x -> x.onEnable());
	}
	
	public static void loadConfig(GameModule module) {
		if (GameCore.getInst().getResource(module.getName() + ".yml") != null) {
			GameCore.getInst().saveResource(module.getName() + ".yml", true);
		}
		File file = FileUtils.file(GameCore.getInst().getDataFolder(), module.getName() + ".yml");
		module.setConfig(YamlConfiguration.loadConfiguration(file));
	}
}

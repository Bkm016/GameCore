package me.skymc.gamecore;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.skymc.gamecore.handler.GameModuleHandler;
import me.skymc.gamecore.module.SuperFurnace;
import me.skymc.taboolib.fileutils.ConfigUtils;

/**
 * @author Bkm016
 * @since 2018-04-21
 */
public class GameCore extends JavaPlugin {
	
	@Getter
	private static GameCore inst;
	@Getter
	private FileConfiguration config;
	
	@Override
	public void reloadConfig() {
		config = ConfigUtils.saveDefaultConfig(this, "config.yml");
	}
	
	@Override
	public void onLoad() {
		inst = this;
		reloadConfig();
	}
	
	@Override
	public void onEnable() {
		loadModules();
		Bukkit.getScheduler().runTask(this, () -> GameModuleHandler.enableAll());
	}
	
	@Override
	public void onDisable() {
		GameModuleHandler.disableAll();
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	private void loadModules() {
		GameModuleHandler.register(new SuperFurnace());
	}
}

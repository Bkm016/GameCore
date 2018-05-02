package me.skymc.gamecore.handler;

import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bkm016
 * @since 2018-04-21
 */
public abstract class GameModule implements Module {
	
	@Getter
	@Setter
	private FileConfiguration config;
	
	public boolean enableConfig() {
		return true;
	}
	
	public void onEnable() {}
	public void onDisable() {}
}

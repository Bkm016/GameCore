package me.skymc.gamecore.module;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import lombok.Getter;
import me.skymc.gamecore.GameCore;
import me.skymc.gamecore.handler.GameModule;

/**
 * @author Bkm016
 * @since 2018-04-21
 */
public class SuperFurnace extends GameModule implements Listener {
	
	@Getter
	private HashMap<String, BukkitTask> furnaceTask = new HashMap<>();
	@Getter
	private HashMap<String, Player> furnaceData = new HashMap<>(); 

	@Override
	public String getName() {
		return "superfurnace";
	}
	
    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
    	Furnace furnace = ((Furnace)e.getBlock().getState());
    	if (furnace.getInventory().getViewers().size() == 0 || furnaceData.containsKey(e.getBlock().getLocation().toString())) {
    		return;
    	}
    	Player ownder = (Player) furnace.getInventory().getViewers().get(0);
    	int level = getPlayerFurnaceLevel(ownder);
    	if (level == 0) {
    		return;
    	}
    	furnaceTask.put(e.getBlock().getLocation().toString(), new BukkitRunnable() {
			
			@Override
			public void run() {
				if (e.getBlock().getType().equals(Material.FURNACE)) {
					furnaceData.remove(e.getBlock().getLocation().toString());
					cancel();
				}
				else {
					furnace.setCookTime((short) (furnace.getCookTime() + level));
					furnace.update();
				}
			}
		}.runTaskTimer(GameCore.getInst(), 1, 2));
    }
    
	public int getPlayerFurnaceLevel(Player player) {
		for (String perm : getConfig().getConfigurationSection("Permission").getKeys(false)) {
			if (player.hasPermission("gamecore.superfurnace." + perm)) {
				return getConfig().getInt("Permission." + perm);
			}
		}
		return 0;
	}
}

package com.enjin.officialplugin.permlisteners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.enjin.officialplugin.EnjinMinecraftPlugin;
import com.enjin.officialplugin.threaded.DelayedPlayerPermsUpdate;

import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.events.PermissionEntityEvent;
import ru.tehkode.permissions.events.PermissionEntityEvent.Action;

public class PexChangeListener implements Listener {
	
	EnjinMinecraftPlugin plugin;
	
	public PexChangeListener(EnjinMinecraftPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void pexGroupAdded(PermissionEntityEvent event) {
		Action theaction = event.getAction();
		if(theaction == Action.DEFAULTGROUP_CHANGED || theaction == Action.RANK_CHANGED) {
			PermissionEntity theentity = event.getEntity();
			if(theentity instanceof PermissionUser) {
				PermissionUser permuser = (PermissionUser)theentity;
				Player p = Bukkit.getPlayerExact(permuser.getName());
				if(p == null) {
					return;
				}
				plugin.debug(p.getName() + " just got a rank change... processing...");
				plugin.listener.updatePlayerRanks(p);
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void preCommandListener(PlayerCommandPreprocessEvent event) {
		if(event.isCancelled()) {
			return;
		}
		Player p = event.getPlayer();
		String command = event.getMessage();
		//Make sure the user has permissions to run the command, otherwise we are just wasting time...
		if(command.toLowerCase().startsWith("/pex group ")) {
			String[] args = command.split(" ");
			if(args.length > 5 && p.hasPermission("permissions.manage.membership." + args[2])) {
				if(args[3].equalsIgnoreCase("user") && (args[4].equalsIgnoreCase("add") || args[4].equalsIgnoreCase("remove"))) {
					//This command accepts csv lists of players
					if(args[5].contains(",")) {
						String[] players = args[5].split(",");
						for(int i = 0; i < players.length; i++) {
							Player ep = Bukkit.getPlayerExact(players[i]);
							//If the player isn't on the server we can't process the rank change...
							if(ep == null) {
								continue;
							}
							//We need to make sure the command executes before we actually grab the data.
							plugin.debug(ep.getName() + " just got a rank change... processing...");
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new DelayedPlayerPermsUpdate(plugin.listener, ep), 2);
						}
					}else {
						Player ep = Bukkit.getPlayerExact(args[5]);
						//If the player isn't on the server we can't process the rank change...
						if(ep == null) {
							return;
						}
						//We need to make sure the command executes before we actually grab the data.
						plugin.debug(ep.getName() + " just got a rank change... processing...");
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new DelayedPlayerPermsUpdate(plugin.listener, ep), 2);
					}
				}
			}
		}else if(command.toLowerCase().startsWith("/pex user ")) {
			String[] args = command.split(" ");
			if(args.length > 6 && p.hasPermission("permissions.manage.membership." + args[5])) {
				if(args[3].equalsIgnoreCase("group") && (args[4].equalsIgnoreCase("add") || args[4].equalsIgnoreCase("remove") || args[4].equalsIgnoreCase("set"))) {
					Player ep = Bukkit.getPlayerExact(args[2]);
					//If the player isn't on the server we can't process the rank change...
					if(ep == null) {
						return;
					}
					//We need to make sure the command executes before we actually grab the data.
					plugin.debug(ep.getName() + " just got a rank change... processing...");
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new DelayedPlayerPermsUpdate(plugin.listener, ep), 2);
				}
			}
		}
	}

}

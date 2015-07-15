package com.enjin.officialplugin.tickets;

import com.enjin.officialplugin.EnjinMinecraftPlugin;
import com.enjin.rpc.mappings.mappings.tickets.Module;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Map;

public class TicketListener implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        if (args.length == 1) {
            String command = args[0].replace("/", "");
            for (final Map.Entry<Integer, Module> entry : EnjinMinecraftPlugin.getModules().entrySet()) {
                final Module module = entry.getValue();
                if (module.getCommand() != null && !module.getCommand().isEmpty()) {
                    if (module.getCommand().equalsIgnoreCase(command)) {
                        Bukkit.getScheduler().runTaskAsynchronously(EnjinMinecraftPlugin.instance, new Runnable() {
                            @Override
                            public void run() {
                                new TicketCreationSession(event.getPlayer(), entry.getKey(), module);
                            }
                        });
                    }
                }
            }
        }
    }
}

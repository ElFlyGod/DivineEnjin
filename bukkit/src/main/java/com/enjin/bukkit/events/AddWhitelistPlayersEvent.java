package com.enjin.bukkit.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This gets called whenever the Enjin plugin whitelists a player.
 *
 * @author Tux2
 */
public class AddWhitelistPlayersEvent extends Event {

    // Custom Event Requirements
    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    OfflinePlayer[] players;

    public AddWhitelistPlayersEvent(OfflinePlayer[] players) {
        super(true);
        this.players = players;
    }

    public OfflinePlayer[] getPlayers() {
        return players;
    }

}
package jp.faketuna.minecraft2fa.paper.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class AuthExpireEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID uuid;

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }


    @Override
    public HandlerList getHandlers(){
        return HANDLERS;
    }

    public AuthExpireEvent(UUID uuid){
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}

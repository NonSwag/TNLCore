package net.nonswag.tnl.core.api.event;

public abstract class Event {

    private boolean cancelled = false;

    protected Event() {
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public boolean call() {
        EventManager.callEvent(this);
        return !isCancelled();
    }
}

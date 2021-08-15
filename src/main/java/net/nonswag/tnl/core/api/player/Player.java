package net.nonswag.tnl.core.api.player;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class Player extends OfflinePlayer {

    @Nonnull
    private final String username;
    @Nonnull
    private final UUID uniqueId;
    @Nonnull
    private final GameProfile profile;

    protected Player(@Nonnull String username, @Nonnull UUID uniqueId) {
        this.username = username;
        this.uniqueId = uniqueId;
        this.profile = new GameProfile(uniqueId, username);
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    @Nonnull
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Nonnull
    public GameProfile getProfile() {
        return profile;
    }
}

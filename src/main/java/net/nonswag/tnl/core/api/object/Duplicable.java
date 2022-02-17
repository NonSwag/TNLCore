package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;

public interface Duplicable {

    @Nonnull
    <C> C duplicate() throws Exception;
}

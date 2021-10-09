package net.nonswag.tnl.core.api.object;

public interface Condition extends Getter<Boolean> {

    default boolean check() {
        return get();
    }
}

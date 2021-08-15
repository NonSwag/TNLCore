package net.nonswag.tnl.core.api.object;

import javax.annotation.Nonnull;
import java.util.Objects;

public record Generic<T>(@Nonnull T parameter) implements Duplicable {

    public Generic(@Nonnull T parameter) {
        this.parameter = parameter;
    }

    @Nonnull
    public Class<T> getParameterType() {
        return (Class<T>) parameter().getClass();
    }

    @Nonnull
    @Override
    public Generic<T> duplicate() {
        return new Generic<>(parameter());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generic<?> generic = (Generic<?>) o;
        return parameter.equals(generic.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter);
    }

    @Override
    public String toString() {
        return "Generic{" +
                "parameter=" + parameter +
                '}';
    }
}

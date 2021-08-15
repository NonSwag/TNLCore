package net.nonswag.tnl.core.api.event;

import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Documented
@TypeQualifier
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    boolean ignoreCancelled() default false;
}

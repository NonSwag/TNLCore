package net.nonswag.tnl.core.api.reflection;

import com.google.gson.JsonObject;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.object.Objects;
import net.nonswag.tnl.core.api.object.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    @Nonnull
    public static Object createInstance(@Nonnull Class<?> clazz) {
        return createInstance(clazz, new Object[]{});
    }

    public static Object createInstance(@Nonnull Class<?> clazz, @Nullable Object[] args, @Nullable Class<?>... parameters) {
        try {
            return clazz.getConstructor(parameters).newInstance(args);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            Logger.error.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public static Object createInstance(@Nonnull String link) {
        return createInstance(link, new Object[]{});
    }

    public static Object createInstance(@Nonnull String link, @Nullable Object[] args, @Nullable Class<?>... parameters) {
        Class<?> aClass = getClass(link);
        if (aClass == null) throw new NullPointerException();
        return createInstance(aClass, args, parameters);
    }

    @Nullable
    public static Class<?> getClass(@Nonnull String link) {
        try {
            return Class.forName(link);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static void setField(@Nonnull Object clazz, @Nonnull String name, @Nullable Object value) {
        try {
            Field field = clazz.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(clazz, value);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Logger.error.println(e.getMessage());
        }
    }

    public static void setField(@Nonnull Object clazz, @Nonnull Class<?> superclass, @Nonnull String name, @Nullable Object value) {
        try {
            Field field = superclass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(clazz, value);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Logger.error.println(e.getMessage());
        }
    }

    public static void setStaticFinalField(@Nonnull Class<?> clazz, @Nonnull String name, @Nullable Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            field.setAccessible(true);
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            field.set(null, value);
            modifiers.setAccessible(false);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Logger.error.println(e.getMessage());
        }
    }

    @Nonnull
    public static Objects<Method> getMethod(@Nonnull Object clazz, @Nonnull String method, @Nullable Class<?>... parameters) {
        try {
            Method declaredMethod = clazz.getClass().getDeclaredMethod(method, parameters);
            declaredMethod.setAccessible(true);
            return new Objects<>(declaredMethod);
        } catch (NoSuchMethodException e) {
            Logger.error.println(e);
            return new Objects<>();
        }
    }

    @Nonnull
    public static Objects<Method> getStaticMethod(@Nonnull Class<?> clazz, @Nonnull String method, @Nullable Class<?>... parameters) {
        try {
            Method declaredMethod = clazz.getMethod(method, parameters);
            declaredMethod.setAccessible(true);
            return new Objects<>(declaredMethod);
        } catch (NoSuchMethodException e) {
            Logger.error.println(e);
            return new Objects<>();
        }
    }

    @Nonnull
    public static Objects<?> getField(@Nonnull Object object, @Nonnull String field) {
        return getField(object, Object.class, field);
    }

    @Nonnull
    public static <P> Objects<P> getField(@Nonnull Object object, @Nonnull Class<? extends P> parameter, @Nonnull String field) {
        return getField(object, object.getClass(), parameter, field);
    }

    @Nonnull
    public static <P> Objects<P> getField(@Nonnull Object object, @Nonnull Class<?> superclass, @Nonnull Class<? extends P> parameter, @Nonnull String field) {
        try {
            Field declaredField = superclass.getDeclaredField(field);
            declaredField.setAccessible(true);
            return new Objects<>((P) declaredField.get(object));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Logger.error.println(e);
            return new Objects<>();
        }
    }

    public static boolean hasField(@Nonnull Object object, @Nonnull String field) {
        return hasField(object, object.getClass(), field);
    }

    public static boolean hasField(@Nonnull Object object, @Nonnull Class<?> superclass, @Nonnull String field) {
        try {
            Object o = superclass.getDeclaredField(field).get(object);
            return true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    @Nonnull
    public static Objects<?> getStaticField(@Nonnull Class<?> clazz, @Nonnull String field) {
        return getStaticField(clazz, Object.class, field);
    }

    @Nonnull
    public static <P> Objects<P> getStaticField(@Nonnull Class<?> clazz, @Nonnull Class<? extends P> parameter, @Nonnull String field) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            return new Objects<>((P) declaredField.get(null));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return new Objects<>();
        }
    }

    @Nonnull
    public static JsonObject toJsonObject(@Nonnull Object clazz) {
        JsonObject object = new JsonObject();
        JsonObject fields = new JsonObject();
        for (String field : getFields(clazz.getClass())) {
            Objects<?> o = getField(clazz, field);
            if (o.getValue() instanceof Pair<?, ?> pair) {
                if ((pair.getValue() instanceof String)) {
                    fields.addProperty(pair.getKey().toString(), ((String) pair.getValue()));
                } else if ((pair.getValue() instanceof Number)) {
                    fields.addProperty(pair.getKey().toString(), ((Number) pair.getValue()));
                } else if ((pair.getValue() instanceof Boolean)) {
                    fields.addProperty(pair.getKey().toString(), ((Boolean) pair.getValue()));
                } else if ((pair.getValue() instanceof Character)) {
                    fields.addProperty(pair.getKey().toString(), ((Character) pair.getValue()));
                }
            } else fields.addProperty(field, o.hasValue() ? o.nonnull().toString() : "");
        }
        object.add(clazz.getClass().getSimpleName(), fields);
        return object;
    }

    @Nonnull
    public static List<String> getFields(@Nonnull Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) fields.add(field.getName());
        for (Field field : clazz.getFields()) fields.add(field.getName());
        return fields;
    }
}

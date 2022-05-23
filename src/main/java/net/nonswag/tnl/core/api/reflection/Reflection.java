package net.nonswag.tnl.core.api.reflection;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.nonswag.tnl.core.api.logger.Logger;
import net.nonswag.tnl.core.api.object.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Reflection {

    private Reflection() {
    }

    @Nonnull
    public static Object createInstance(@Nonnull Class<?> clazz, @Nonnull Object... parameters) throws ReflectionException {
        List<Class<?>> args = new ArrayList<>();
        for (Object parameter : parameters) args.add(parameter.getClass());
        return createInstance(clazz, parameters, args.toArray(new Class[]{}));
    }

    @Nonnull
    public static Object createInstance(@Nonnull Class<?> clazz, @Nullable Object[] parameters, @Nullable Class<?>[] args) {
        try {
            return clazz.getConstructor(args).newInstance(parameters);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    @Nonnull
    public static Object createInstance(@Nonnull String link, @Nonnull Object... parameters) throws ReflectionException {
        Class<?> aClass = getClass(link);
        if (aClass == null) throw new ReflectionException();
        return createInstance(aClass, parameters);
    }

    @Nonnull
    public static Object createInstance(@Nonnull String link, @Nullable Object[] args, @Nullable Class<?>[] parameters) throws ReflectionException {
        Class<?> aClass = getClass(link);
        if (aClass == null) throw new ReflectionException();
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

    public static <T> void deepCopy(@Nullable T origin, @Nullable T copy) {
        if (origin == null || copy == null || origin == copy) return;
        for (String field : getFields(origin.getClass())) {
            setField(copy, field, Reflection.getField(origin, field).getValue());
        }
    }

    public static void setField(@Nonnull Object clazz, @Nonnull String name, @Nullable Object value) {
        try {
            Field field = clazz.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(clazz, value);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            Logger.error.println(e.getMessage());
        } catch (NoSuchFieldException e) {
            Logger.error.println("The field <" + name + ":" + clazz.getClass().getName() + "> does not exist");
        }
    }

    public static void setField(@Nonnull Object clazz, @Nonnull Class<?> superclass, @Nonnull String name, @Nullable Object value) {
        try {
            Field field = superclass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(clazz, value);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            Logger.error.println(e.getMessage());
        } catch (NoSuchFieldException e) {
            Logger.error.println("The field <" + name + ":" + clazz.getClass().getName() + "> does not exist");
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
        } catch (IllegalAccessException e) {
            Logger.error.println(e.getMessage());
        } catch (NoSuchFieldException e) {
            Logger.error.println("The static field <" + name + ":" + clazz.getName() + "> does not exist");
        }
    }

    @Nonnull
    public static Objects<Method> getMethod(@Nonnull Object clazz, @Nonnull String method, @Nullable Class<?>... parameters) {
        try {
            Method declaredMethod = clazz.getClass().getDeclaredMethod(method, parameters);
            declaredMethod.setAccessible(true);
            return new Objects<>(declaredMethod);
        } catch (NoSuchMethodException e) {
            Logger.error.println("The method <" + method + ":" + clazz.getClass().getName() + "> does not exist");
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
            Logger.error.println("The static method <" + method + ":" + clazz.getName() + "> does not exist");
            return new Objects<>();
        }
    }

    @Nonnull
    public static <P> Objects<P> getField(@Nonnull Object object, @Nonnull String field) {
        return getField(object, object.getClass(), field);
    }

    @Nonnull
    public static <P> Objects<P> getField(@Nonnull Object object, @Nonnull Class<?> superclass, @Nonnull String field) {
        try {
            Field declaredField = superclass.getDeclaredField(field);
            declaredField.setAccessible(true);
            return new Objects<>((P) declaredField.get(object));
        } catch (NoSuchFieldException e) {
            Logger.error.println("The field <" + field + ":" + superclass.getName() + "> does not exist");
        } catch (Exception ignored) {
        }
        return new Objects<>();
    }

    public static boolean hasField(@Nonnull Object object, @Nonnull String field) {
        return hasField(object, object.getClass(), field);
    }

    public static boolean hasField(@Nonnull Object object, @Nonnull Class<?> superclass, @Nonnull String field) {
        try {
            superclass.getDeclaredField(field).get(object);
            return true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    public static boolean hasStaticField(@Nonnull Object object, @Nonnull String field) {
        return hasStaticField(object, object.getClass(), field);
    }

    public static boolean hasStaticField(@Nonnull Object object, @Nonnull Class<?> superclass, @Nonnull String field) {
        try {
            superclass.getField(field).get(object);
            return true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    @Nonnull
    public static <P> Objects<P> getStaticField(@Nonnull Class<?> clazz, @Nonnull String field) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            return new Objects<>((P) declaredField.get(null));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return new Objects<>();
        }
    }

    @Nonnull
    public static JsonObject toJsonObject(@Nonnull Object instance) {
        JsonObject object = new JsonObject();
        addFields(object, instance.getClass().getName(), instance);
        return object;
    }

    private static void addFields(@Nonnull JsonArray jsonArray, @Nonnull Object object) {
        if (object instanceof StringBuilder builder) jsonArray.add(builder.toString());
        else if (object instanceof String string) jsonArray.add(string);
        else if (object instanceof Number number) jsonArray.add(number);
        else if (object instanceof Boolean b) jsonArray.add(b);
        else if (object instanceof Character c) jsonArray.add(c);
        else jsonArray.add(toJsonObject(object));
    }

    private static void addFields(@Nonnull JsonObject jsonObject, @Nonnull String name, @Nullable Object object) {
        try {
            if (object == null) jsonObject.add(name, null);
            else if (object instanceof StringBuilder builder) jsonObject.addProperty(name, builder.toString());
            else if (object instanceof String string) jsonObject.addProperty(name, string);
            else if (object instanceof Number number) jsonObject.addProperty(name, number);
            else if (object instanceof Boolean b) jsonObject.addProperty(name, b);
            else if (object instanceof Character c) jsonObject.addProperty(name, c);
            else if (object instanceof Map map) {
                JsonArray array = new JsonArray();
                JsonObject root = new JsonObject();
                for (Object key : map.keySet()) {
                    JsonObject keySet = new JsonObject();
                    addFields(keySet, "key", key);
                    addFields(keySet, "value", map.get(key));
                    array.add(keySet);
                }
                root.add(map.getClass().getName(), array);
                jsonObject.add(name, root);
            } else if (object instanceof Object[] list) {
                JsonArray array = new JsonArray();
                for (Object o : list) addFields(array, o);
                jsonObject.add(name, array);
            } else if (object instanceof List list) {
                JsonArray array = new JsonArray();
                for (Object o : list) addFields(array, o);
                jsonObject.add(name, array);
            } else {
                JsonObject o = new JsonObject();
                for (Class<?> clazz : getConnectingClasses(object.getClass())) {
                    for (String field : getFields(clazz)) {
                        addFields(o, field, getField(object, clazz, field).getValue());
                    }
                }
                jsonObject.add(name, o);
            }
        } catch (Exception ignored) {
        }
    }

    @Nonnull
    public static List<Class<?>> getConnectingClasses(@Nonnull Class<?> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        if (clazz.getSuperclass() == null) classes.add(clazz);
        else classes.addAll(getConnectingClasses(clazz.getSuperclass()));
        if (!classes.contains(clazz)) classes.add(clazz);
        classes.remove(Object.class);
        return classes;
    }

    @Nonnull
    public static List<String> getFieldsRecursive(@Nonnull Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Class<?> superclass : getConnectingClasses(clazz)) fields.addAll(getFields(superclass));
        return fields;
    }

    @Nonnull
    public static List<String> getStaticFieldsRecursive(@Nonnull Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Class<?> superclass : getConnectingClasses(clazz)) fields.addAll(getStaticFields(superclass));
        return fields;
    }

    @Nonnull
    public static List<String> getFields(@Nonnull Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) fields.add(field.getName());
        }
        return fields;
    }

    @Nonnull
    public static List<String> getStaticFields(@Nonnull Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) fields.add(field.getName());
        }
        return fields;
    }
}

package net.nonswag.tnl.core.api.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

public abstract class IntelligentMap<K, V> extends HashMap<K, V> {

    public abstract void update();

    @Override
    public V put(K key, V value) {
        if (!Objects.equals(value, get(key))) update();
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        update();
        super.putAll(m);
    }

    @Override
    public V remove(Object key) {
        if (containsKey(key)) update();
        return super.remove(key);
    }

    @Override
    public void clear() {
        if (!isEmpty()) update();
        super.clear();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (containsKey(key)) update();
        return super.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (!super.remove(key, value)) return false;
        update();
        return true;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (!super.replace(key, oldValue, newValue)) return false;
        update();
        return true;
    }

    @Override
    public V replace(K key, V value) {
        if (!Objects.equals(get(key), value)) update();
        return super.replace(key, value);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        update();
        return super.merge(key, value, remappingFunction);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        update();
        super.replaceAll(function);
    }
}

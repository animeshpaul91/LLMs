package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericMap <K, V> {
    private final Map<K, V> map;

    public GenericMap() {
        map = new HashMap<>();
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public V remove(K key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Set<K> keySet() {
        return Set.copyOf(map.keySet());
    }

    public Collection<V> values() {
        return List.copyOf(map.values());
    }
}

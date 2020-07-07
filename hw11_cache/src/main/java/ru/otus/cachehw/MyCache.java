package ru.otus.cachehw;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    WeakHashMap<K, V> cache = new WeakHashMap();

    List<SoftReference<HwListener>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new SoftReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        Optional<SoftReference<HwListener>> softReferenceListener = listeners.stream().filter(hwListenerSoftReference -> hwListenerSoftReference.get().equals(listener)).findAny();
        if (softReferenceListener.isPresent()) {
            listeners.remove(softReferenceListener.get());
        }
    }

    @Override
    public String toString() {
        return "MyCache{" +
                "cache=" + cache +
                '}';
    }

    @Override
    public int getCacheSize() {
        return cache.size();
    }
}

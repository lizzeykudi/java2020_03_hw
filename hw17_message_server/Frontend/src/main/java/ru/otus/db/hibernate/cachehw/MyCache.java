package ru.otus.db.hibernate.cachehw;

import org.springframework.stereotype.Component;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.WeakHashMap;

@Component
public class MyCache<K, V> implements HwCache<K, V> {

    private WeakHashMap<K, V> cache = new WeakHashMap();

    private List<SoftReference<HwListener>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyAllListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V removedValue = cache.remove(key);
        notifyAllListeners(key, removedValue, "remove");
    }

    private void notifyAllListeners(K key, V value, String action) {
        listeners.forEach(ref -> {
            if (ref.get()!=null) {ref.get().notify(key,value,"put");}
        });
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

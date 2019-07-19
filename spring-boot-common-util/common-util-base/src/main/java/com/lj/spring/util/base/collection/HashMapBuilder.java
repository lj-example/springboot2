package com.lj.spring.util.base.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by junli on 2019-07-11
 */
public class HashMapBuilder<K, V> {

    private HashMap<K, V> map;

    private HashMapBuilder() {
        this.map = new HashMap<>();
    }

    public static <K, V> HashMapBuilder<K, V> newBuilder() {
        return new HashMapBuilder<>();
    }

    public HashMapBuilder(Map<? extends K, ? extends V> m) {
        this.map = new HashMap<>(m);
    }

    public HashMapBuilder(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    public HashMapBuilder(int initialCapacity, float loadFactor) {
        this.map = new HashMap(initialCapacity, loadFactor);
    }

    public HashMapBuilder<K, V> put(K k, V v) {
        this.map.put(k, v);
        return this;
    }

    public HashMap<K, V> build() {
        return this.map;
    }

    /**
     * 擦除数据类型
     */
    public <NK, NV> HashMap<NK, NV> buildUnsafe() {
        return (HashMap<NK, NV>) this.map;
    }

}


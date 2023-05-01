package dev.kavu.gameapi;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ValueContainer<V> {

    V get();
    void set(V value);

    void modify(Consumer<V> modifier);
    void modify(Function<V, V> modifier);
}

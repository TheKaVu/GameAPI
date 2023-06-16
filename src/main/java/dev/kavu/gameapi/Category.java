package dev.kavu.gameapi;

public interface Category<T> {

    String getName();

    Class<? extends T> getRoot();
}

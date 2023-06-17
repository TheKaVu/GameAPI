package dev.kavu.gameapi;

public interface Category<T extends Category<T>> {

    String getName();

    Class<? extends T> getCategory();
}

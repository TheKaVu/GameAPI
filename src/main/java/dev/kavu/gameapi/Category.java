package dev.kavu.gameapi;

/**
 * The interface representing game category.
 * @param <T> Class implementing this interface
 */
public interface Category<T extends Category<T>> {

    /**
     * @return Name of this category
     */
    String getName();

    /**
     * @return Class of this category
     */
    Class<? extends T> getCategory();
}

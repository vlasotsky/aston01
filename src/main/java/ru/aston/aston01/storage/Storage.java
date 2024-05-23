package ru.aston.aston01.storage;

/**
 * The interface declaring different CRUD operations (create, read, update, delete)
 * meant to be implemented by different storage types.
 *
 * @param <Element> the type of the elements to be stored in a storage
 */
public interface Storage<Element> {
    void save(Element element);

    void insert(int idx, Element element);

    void update(int idx, Element elem);

    Element get(int idx);

    void delete(Element element);

    void deleteByIndex(int idx);

    void clear();

    int size();

    Element[] getAll();

    Element[] getAllSorted();
}

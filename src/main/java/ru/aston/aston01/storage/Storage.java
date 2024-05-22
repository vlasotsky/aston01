package ru.aston.aston01.storage;

public interface Storage {
    void save(Object element);

    void insert(int idx, Object element);

    void update(int idx, Object elem);

    Object get(int idx);

    void delete(Object element);

    void deleteByIndex(int idx);

    void clear();

    int size();

    Object[] getAll();
}

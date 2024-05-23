package ru.aston.aston01.storage;

import java.util.Arrays;

public class ArrayListStorage<Element> implements Storage<Element> {
    private Object[] elementData;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 8;

    public ArrayListStorage() {
        this.capacity = DEFAULT_CAPACITY;
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public ArrayListStorage(int customCapacity) {
        if (customCapacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be less than 1");
        }

        this.elementData = new Object[customCapacity];
        this.capacity = customCapacity;
        this.size = 0;
    }

    @Override
    public void save(Element element) {
        validateCapacity();
        doSave(size, element);
    }

    @Override
    public void insert(int idx, Element element) {
        validateCapacity();
        validateIndex(idx);

        shiftElements(idx, idx + 1);
        doSave(idx, element);
    }

    @Override
    public void update(int idx, Element element) {
        validateIndex(idx);

        elementData[idx] = element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Element get(int idx) {
        validateIndex(idx);

        return (Element) elementData[idx];
    }

    @Override
    public void delete(Element element) {
        final int idx = getIndex(element);
        validateIndex(idx);

        doDelete(idx);
    }

    @Override
    public void deleteByIndex(int idx) {
        validateIndex(idx);

        doDelete(idx);
    }

    @Override
    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Element[] getAll() {
        return (Element[]) Arrays.copyOfRange(elementData, 0, size);
    }

    @Override
    public Element[] getAllSorted() {
        return null;
    }

    private void validateCapacity() {
        if (elementData.length == size + 1) {
            Object[] newStorage = new Object[capacity *= 2];
            System.arraycopy(elementData, 0, newStorage, 0, size);

            elementData = newStorage;
        }
    }

    private void validateIndex(int idx) {
        if (idx < 0) {
            throw new IllegalArgumentException("There cannot be a negative index " + idx);
        } else if (idx >= size) {
            throw new IndexOutOfBoundsException("Index " + idx + " is out of bound for size " + size);
        }
    }

    private void doSave(int idx, Element element) {
        elementData[idx] = element;
        size++;
    }

    private void doDelete(int idx) {
        shiftElements(idx + 1, idx);
        elementData[size - 1] = null;
        size--;
    }

    private int getIndex(Element element) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    private void shiftElements(int from, int to) {
        System.arraycopy(elementData, from, elementData, to, size - from);
    }
}

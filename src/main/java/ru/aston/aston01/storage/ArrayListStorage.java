package ru.aston.aston01.storage;

import ru.aston.aston01.util.ArrayListStorageSorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A custom implementation of the standard ArrayList class.
 * Supports basic CRUD (Create, read, update, delete) operations
 * and automatically expands its capacity when reaching a certain load factor.
 *
 * @param <Element> the type of elements in this list
 */
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

    /**
     * Saves an element into the list by appending it to the end and checks if the storage
     * needs to be dynamically resized if it is full.
     *
     * @param element the element to be saved in the list
     */
    @Override
    public void save(Element element) {
        validateCapacity();
        doSave(size, element);
    }

    /**
     * Inserts an element into the list, shifting all other elements to the right,
     * and checks if the storage needs to be dynamically resized if it is full.
     * Also checks if the index is valid.
     *
     * @param idx     the index at which an element should be saved
     * @param element the element to be saved in the list
     */
    @Override
    public void insert(int idx, Element element) {
        validateCapacity();
        validateIndex(idx);

        shiftElements(idx, idx + 1);
        doSave(idx, element);
    }

    /**
     * Updates the element at a specified index,
     * replacing the original object with a new one.
     * Also checks if the index is valid.
     *
     * @param idx     the index at which the object should be updated
     * @param element the element that replaces the object at the specified index
     */
    @Override
    public void update(int idx, Element element) {
        validateIndex(idx);

        elementData[idx] = element;
    }

    /**
     * Returns the element from the list at the specified index.
     * Also checks if the index is valid.
     *
     * @param idx the index of the element to return
     * @return the element at the specified index
     */
    @Override
    @SuppressWarnings("unchecked")
    public Element get(int idx) {
        validateIndex(idx);

        return (Element) elementData[idx];
    }

    /**
     * Deletes the first occurrence of the specified element from the list.
     * Also checks if the index is valid.
     *
     * @param element the element to delete from the list
     */
    @Override
    public void delete(Element element) {
        final int idx = getIndex(element);
        validateIndex(idx);

        doDelete(idx);
    }

    /**
     * Deletes the element at the specified index from the list.
     * Also checks if the index is valid.
     *
     * @param idx the index of the element to delete
     */
    @Override
    public void deleteByIndex(int idx) {
        validateIndex(idx);

        doDelete(idx);
    }

    /**
     * Clears all elements of the list.
     * The list will be empty after this operation.
     */
    @Override
    public void clear() {
        Arrays.fill(elementData, 0, size, null);
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all elements in the list.
     *
     * @return an array containing all elements in the list
     */
    @Override
    @SuppressWarnings("unchecked")
    public Element[] getAll() {
        return (Element[]) Arrays.copyOfRange(elementData, 0, size);
    }

    /**
     * Returns an array containing all elements from the list in the natural order.
     *
     * @return an array with naturally sorted elements or {@code null} if the method is not yet implemented
     */
    @Override
    public Element[] getAllSorted() {
        return ArrayListStorageSorter.quickSort((Element[]) elementData, Comparator.naturalOrder(), elementData.getClass().componentType());
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
            throw new IndexOutOfBoundsException("Index " + idx + " is out of bounds for size " + size);
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

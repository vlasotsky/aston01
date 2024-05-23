package ru.aston.aston01.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The utility class that is meant to sort elements
 */
public class ArrayListStorageSorter {
    /**
     * The static method to sort elements in the array
     *
     * @param array      the array of type Element to be sorted
     * @param comparator the comparator in which logic the array should be sorted
     * @param type       the type of the elements in the array specified to avoid ClassCastException
     * @param <Element>  type of the elements used with the method
     * @return a sorted array of objects of type Element
     */
    public static <Element> Element[] quickSort(Element[] array, Comparator<? super Element> comparator, Class<?> type) {
        if (array.length < 2) {
            return array;
        }

        Element pivot = array[0];

        @SuppressWarnings("unchecked")
        Element[] left = (Element[]) Array.newInstance(type, array.length);
        @SuppressWarnings("unchecked")
        Element[] right = (Element[]) Array.newInstance(type, array.length);
        int leftCounter = 0;
        int rightCounter = 0;


        for (int i = 1; i < array.length; i++) {
            final Element currentElement = array[i];
            final int resultComparison = comparator.compare(currentElement, pivot);

            if (resultComparison < 0) {
                left[leftCounter++] = currentElement;
            } else if (resultComparison > 0) {
                right[rightCounter++] = currentElement;
            }
        }

        Element[] leftSorted = quickSort(Arrays.copyOf(left, leftCounter), comparator, type);
        Element[] rightSorted = quickSort(Arrays.copyOf(right, rightCounter), comparator, type);

        @SuppressWarnings("unchecked")
        Element[] result = (Element[]) Array.newInstance(type, array.length);

        System.arraycopy(leftSorted, 0, result, 0, leftSorted.length);
        result[leftSorted.length] = pivot;
        System.arraycopy(rightSorted, 0, result, leftSorted.length + 1, rightSorted.length);

        return result;
    }
}
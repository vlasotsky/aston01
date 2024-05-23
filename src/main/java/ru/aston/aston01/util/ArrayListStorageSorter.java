package ru.aston.aston01.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class ArrayListStorageSorter {
    public static <Element> Element[] quickSort(Element[] array, Comparator<? super Element> comparator, Class<Element> type) {
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
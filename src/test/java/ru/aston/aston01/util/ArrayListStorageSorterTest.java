package ru.aston.aston01.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static ru.aston.aston01.util.ArrayListStorageSorter.quickSort;

public class ArrayListStorageSorterTest {
    private static final UUID[] UUIDS;
    private static final Integer[] INTEGERS;
    private static final String[] STRINGS;

    static {
        UUIDS = new UUID[]{
                UUID.fromString("1-1-1-1-1"),
                UUID.fromString("0-0-0-0-0"),
                UUID.fromString("3-3-3-3-3"),
                UUID.fromString("2-2-2-2-2")};

        INTEGERS = new Integer[]{1, 3, 5, 2, 6, 4, 9, -1};
        STRINGS = new String[]{"Wednesday", "Friday", "Aaron", "Christian", "Hugh", "Monday", "Abraham"};
    }

    @Test
    public void quickSortIntegers() {
        final Integer[] actual = quickSort(INTEGERS, Comparator.naturalOrder(), Integer.class);
        final Integer[] expected = Arrays.stream(INTEGERS)
                .sorted()
                .toArray(Integer[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void quickSortUuids() {
        final UUID[] actual = quickSort(UUIDS, Comparator.naturalOrder(), UUID.class);
        final UUID[] expected = Arrays.stream(UUIDS)
                .sorted()
                .toArray(UUID[]::new);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void quickSortStrings() {
        final String[] actual = quickSort(STRINGS, Comparator.reverseOrder(), String.class);
        final String[] expected = Arrays.stream(STRINGS)
                .sorted(Comparator.reverseOrder())
                .toArray(String[]::new);

        assertArrayEquals(expected, actual);
    }
}
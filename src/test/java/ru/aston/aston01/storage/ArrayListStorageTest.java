package ru.aston.aston01.storage;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayListStorageTest {
    private final Storage<UUID> storage;

    private static final int INITIAL_SIZE = 7;
    private static final int INITIAL_SIZE_ONE_LESS = INITIAL_SIZE - 1;
    private static final int DEFAULT_CAPACITY = 8;
    private static final int FIRST_EXPAND_CAPACITY = 16;
    private static final int SIZE_AFTER_1000 = 1008;
    private static final int DUMMY_INDEX = -1;

    private static final UUID UUID_01;
    private static final UUID UUID_02;
    private static final UUID UUID_03;
    private static final UUID UUID_04;
    private static final UUID UUID_05;
    private static final UUID UUID_06;
    private static final UUID UUID_07;

    private static final UUID DUMMY;

    private static final Object[] AFTER_INSERT;

    static {
        UUID_01 = UUID.randomUUID();
        UUID_02 = UUID.randomUUID();
        UUID_03 = UUID.randomUUID();
        UUID_04 = UUID.randomUUID();
        UUID_05 = UUID.randomUUID();
        UUID_06 = UUID.randomUUID();
        UUID_07 = UUID.randomUUID();

        DUMMY = UUID.fromString("0-0-0-0-0");

        AFTER_INSERT = new Object[]{
                UUID_01, UUID_02, UUID_03, DUMMY, UUID_04, UUID_05, UUID_06, UUID_07
        };
    }


    public ArrayListStorageTest() {
        this.storage = new ArrayListStorage<>();
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(UUID_01);
        storage.save(UUID_02);
        storage.save(UUID_03);
        storage.save(UUID_04);
        storage.save(UUID_05);
        storage.save(UUID_06);
        storage.save(UUID_07);
    }

    @Test
    public void save() {
        storage.save(DUMMY);

        assertSize(8);
        assertEquals(DUMMY, storage.get(INITIAL_SIZE));
    }

    @Test
    public void saveWithOverflow() throws NoSuchFieldException, IllegalAccessException {
        assertSize(INITIAL_SIZE);

        final Field field = storage.getClass().getDeclaredField("capacity");
        field.setAccessible(true);
        int capacity = field.getInt(storage);
        assertEquals(DEFAULT_CAPACITY, capacity);

        storage.save(DUMMY);
        capacity = field.getInt(storage);
        assertEquals(FIRST_EXPAND_CAPACITY, capacity);

        for (int i = 0; i < 1000; i++) {
            storage.save(UUID.randomUUID());
        }

        assertSize(SIZE_AFTER_1000);
    }

    @Test
    public void insert() {
        storage.insert(3, DUMMY);
        assertEquals(DUMMY, storage.get(3));
        assertArrayEquals(AFTER_INSERT, storage.getAll());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void insertOutOfBounds() {
        storage.insert(INITIAL_SIZE, DUMMY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNotExisting() {
        storage.insert(DUMMY_INDEX, DUMMY);
    }

    @Test
    public void update() {
        assertEquals(UUID_02, storage.get(1));
        storage.update(1, DUMMY);
        assertEquals(DUMMY, storage.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExisting() {
        storage.update(DUMMY_INDEX, DUMMY);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void updateOutOfBounds() {
        storage.update(INITIAL_SIZE, DUMMY);
    }

    @Test
    public void get() {
        assertEquals(UUID_01, storage.get(0));
        assertEquals(UUID_05, storage.get(4));
        assertEquals(UUID_07, storage.get(6));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutOfBounds() {
        storage.get(INITIAL_SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNotExisting() {
        storage.get(DUMMY_INDEX);
    }

    @Test
    public void deleteByObject() {
        assertSize(INITIAL_SIZE);

        storage.delete(UUID_04);
        assertSize(INITIAL_SIZE_ONE_LESS);

        assertEquals(UUID_05, storage.get(3));
    }

    @Test
    public void deleteByIndex() {
        assertSize(INITIAL_SIZE);

        storage.deleteByIndex(6);
        assertSize(INITIAL_SIZE_ONE_LESS);

        assertEquals(UUID_06, storage.get(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistingIndex() {
        storage.deleteByIndex(DUMMY_INDEX);

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNotExistingObject() {
        storage.delete(DUMMY);
    }

    @Test
    public void clear() {
        assertSize(INITIAL_SIZE);
        storage.clear();
        assertSize(0);
    }

    @Test
    public void size() {
        assertEquals(INITIAL_SIZE, storage.size());
    }

    @Test
    public void getAllSorted() {
    }

    private void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }
}
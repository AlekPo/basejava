package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.DataStrategy;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(new DataStrategy(), STORAGE_DIR));
    }
}

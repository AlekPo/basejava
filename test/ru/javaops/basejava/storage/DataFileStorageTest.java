package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.DataStrategy;

public class DataFileStorageTest extends AbstractStorageTest {
    public DataFileStorageTest() {
        super(new FileStorage(new DataStrategy(), STORAGE_DIR));
    }
}

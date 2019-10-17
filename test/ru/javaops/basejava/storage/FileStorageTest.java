package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.ObjectStrategy;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new ObjectStrategy(), STORAGE_DIR));
    }

}

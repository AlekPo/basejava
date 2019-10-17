package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.ObjectStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(new ObjectStrategy(), STORAGE_DIR));
    }
}

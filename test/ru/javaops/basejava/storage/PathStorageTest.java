package ru.javaops.basejava.storage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(new IOStrategy(), STORAGE_DIR));
    }
}

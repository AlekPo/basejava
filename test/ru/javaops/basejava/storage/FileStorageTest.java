package ru.javaops.basejava.storage;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new IOStrategy(), STORAGE_DIR));
    }

}

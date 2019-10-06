package ru.javaops.basejava.storage;

public class FileStorageStrategyObjectIOStreamTest extends AbstractStorageTest {
    public FileStorageStrategyObjectIOStreamTest() {
        super(new FileStorage(new StrategyObjectIOStream(), STORAGE_DIR));
    }

}

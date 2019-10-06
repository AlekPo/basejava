package ru.javaops.basejava.storage;

public class PathStorageStrategyObjectIOStreamTest extends AbstractStorageTest {
    public PathStorageStrategyObjectIOStreamTest() {
        super(new PathStorage(new StrategyObjectIOStream(), STORAGE_DIR));
    }
}

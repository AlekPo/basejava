package ru.javaops.basejava.storage;

public class PathObjectIOStreamTest extends AbstractStorageTest {

    public PathObjectIOStreamTest() {
        super(new PathObjectIOStream(new StrategyObjectIOStream(), STORAGE_DIR));
    }
}

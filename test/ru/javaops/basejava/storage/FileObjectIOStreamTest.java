package ru.javaops.basejava.storage;

public class FileObjectIOStreamTest extends AbstractStorageTest {

    public FileObjectIOStreamTest() {
        super(new FileObjectIOStream(new StrategyObjectIOStream(), STORAGE_DIR));
    }
}

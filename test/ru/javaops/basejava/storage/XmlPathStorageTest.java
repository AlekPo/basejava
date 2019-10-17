package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.XmlStrategy;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(new XmlStrategy(), STORAGE_DIR));
    }
}

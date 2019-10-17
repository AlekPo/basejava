package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.XmlStrategy;

public class XmlFileStorageTest extends AbstractStorageTest {
    public XmlFileStorageTest() {
        super(new FileStorage(new XmlStrategy(), STORAGE_DIR));
    }
}

package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.JsonStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(new JsonStrategy(), STORAGE_DIR));
    }
}

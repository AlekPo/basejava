package ru.javaops.basejava.storage;

import ru.javaops.basejava.storage.serializer.JsonStrategy;

public class JsonFileStorageTest extends AbstractStorageTest {
    public JsonFileStorageTest() {
        super(new FileStorage(new JsonStrategy(), STORAGE_DIR));
    }
}

package ru.javaops.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("FullName" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Overflow " + e);
        }
        storage.save(new Resume("Overflow"));
    }
}
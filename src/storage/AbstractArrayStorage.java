package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doSave(Object index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            throw new StorageException("Storage overflow", uuid);
        } else {
            insertElement(resume, (Integer) index);
            size++;
        }
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}

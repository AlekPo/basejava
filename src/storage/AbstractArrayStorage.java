package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

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

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected abstract Object getSearchKey(String uuid);

    @Override
    protected boolean checkIn(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void insertObj(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            String uuid = resume.getUuid();
            throw new StorageException("Storage overflow", uuid);
        } else {
            int tempIndex = insertIndex((int) searchKey);
            storage[tempIndex] = resume;
            size++;
        }
    }

    @Override
    protected void deleteObj(Object searchKey) {
        deleteIndex((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateObj(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    protected abstract int insertIndex(int index);

    protected abstract void deleteIndex(int index);
}

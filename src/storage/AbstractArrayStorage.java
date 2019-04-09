package storage;

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
    protected abstract int getIndex(String uuid);

    @Override
    protected boolean checkIn(int index, String uuid) {
        return index >= 0;
    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage[index];
    }

    @Override
    protected boolean checkStorageOverflow() {
        return size == STORAGE_LIMIT;
    }

    @Override
    protected void insertObj(Resume resume, int index) {
        int tempIndex = insertIndex(index);
        storage[tempIndex] = resume;
        size++;
    }

    @Override
    protected void deleteObj(int index, String uuid) {
        deleteIndex(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateObj(int index, Resume resume) {
        storage[index] = resume;
    }

    protected abstract int insertIndex(int index);

    protected abstract void deleteIndex(int index);

}

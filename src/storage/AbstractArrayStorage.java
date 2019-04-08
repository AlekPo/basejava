package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
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

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (index < 0) {
            int tempIndex = insertObj(index);
            storage[tempIndex] = resume;
            size++;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteObj(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract int insertObj(int index);

    protected abstract void deleteObj(int index);
}

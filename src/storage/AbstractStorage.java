package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!(checkIn(index, uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(index, uuid);
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (checkStorageOverflow()) {
            throw new StorageException("Storage overflow", uuid);
        } else if (!(checkIn(index, uuid))) {
            insertObj(resume, index);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (checkIn(index, uuid)) {
            deleteObj(index, uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (checkIn(index, uuid)) {
            updateObj(index, resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract boolean checkIn(int index, String uuid);

    protected abstract Resume doGet(int index, String uuid);

    protected abstract boolean checkStorageOverflow();

    protected abstract void insertObj(Resume resume, int index);

    protected abstract void deleteObj(int index, String uuid);

    protected abstract void updateObj(int index, Resume resume);

}

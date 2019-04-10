package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!checkIn(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getSearchKey(uuid);
        if (!checkIn(searchKey)) {
            insertObj(searchKey, resume);
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (checkIn(searchKey)) {
            deleteObj(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getSearchKey(uuid);
        if (checkIn(searchKey)) {
            updateObj(searchKey, resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean checkIn(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void insertObj(Object searchKey, Resume resume);

    protected abstract void deleteObj(Object searchKey);

    protected abstract void updateObj(Object searchKey, Resume resume);
}

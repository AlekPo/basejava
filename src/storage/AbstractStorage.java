package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        return getList().stream().
                sorted().
                collect(Collectors.toList());
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    private Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract List<Resume> getList();

}

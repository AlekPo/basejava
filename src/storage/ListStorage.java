package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume get(String uuid) {
        for (Resume res : storage) {
            if (res.getUuid() == uuid) {
                return res;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public void save(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> it = storage.iterator();
        while (it.hasNext()) {
            Resume res = it.next();
            if (res.getUuid() == uuid) {
                it.remove();
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void update(Resume resume) {
        ListIterator<Resume> it = storage.listIterator();
        while (it.hasNext()) {
            Resume res = it.next();
            if (res.getUuid() == resume.getUuid()) {
                it.set(resume);
                return;
            }
        }
        throw new NotExistStorageException(resume.getUuid());
    }
}

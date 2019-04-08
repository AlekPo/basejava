package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;
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

    /**
     * First version of the method get()
     */
//    @Override
//    public Resume get(String uuid) {
//        for (Resume res : storage) {
//            if (res.getUuid() == uuid) {
//                return res;
//            }
//        }
//        throw new NotExistStorageException(uuid);
//    }
    @Override
    public Resume get(String uuid) {
        Resume resTemp = listIter(uuid).previous();
        return resTemp;
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

    /**
     * First version of the method delete()
     */
//    @Override
//    public void delete(String uuid) {
//        ListIterator<Resume> it = storage.listIterator();
//        while (it.hasNext()) {
//            Resume res = it.next();
//            if (res.getUuid() == uuid) {
//                it.remove();
//                return;
//            }
//        }
//        throw new NotExistStorageException(uuid);
//    }
    @Override
    public void delete(String uuid) {
        listIter(uuid).remove();
    }

    /**
     * First version of the method update()
     */
//    @Override
//    public void update(Resume resume) {
//        String uuid = resume.getUuid();
//        ListIterator<Resume> it = storage.listIterator();
//        while (it.hasNext()) {
//            Resume res = it.next();
//            if (res.getUuid() == uuid) {
//                it.set(resume);
//                return;
//            }
//        }
//        throw new NotExistStorageException(uuid);
//    }
    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        listIter(uuid).set(resume);
    }

    private ListIterator<Resume> listIter(String uuid) {
        ListIterator<Resume> it = storage.listIterator();
        while (it.hasNext()) {
            Resume resTemp = it.next();
            if (resTemp.getUuid() == uuid) {
                return it;
            }
        }
        throw new NotExistStorageException(uuid);
    }

}

package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

//    @Override
//    public Resume get(String uuid) {
//        Resume resTemp = storage.get(uuid);
//        if (resTemp != null) {
//            return resTemp;
//        }
//        throw new NotExistStorageException(uuid);
//    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

//    @Override
//    public void save(Resume resume) {
//        String uuid = resume.getUuid();
//        if (storage.containsKey(uuid)) {
//            throw new ExistStorageException(uuid);
//        }
//        storage.put(uuid, resume);
//    }
//
//    @Override
//    public void delete(String uuid) {
//        if (storage.remove(uuid) == null) {
//            throw new NotExistStorageException(uuid);
//        }
//    }
//
//    @Override
//    public void update(Resume resume) {
//        String uuid = resume.getUuid();
//        if (storage.put(uuid, resume) == null) {
//            throw new NotExistStorageException(uuid);
//        }
//    }

    @Override
    protected int getIndex(String uuid) {
        return -1;
    }

    @Override
    protected boolean checkIn(int index, String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean checkStorageOverflow() {
        return false;
    }

    @Override
    protected void insertObj(Resume resume, int index) {
        String uuid = resume.getUuid();
        storage.put(uuid, resume);
    }

    @Override
    protected void deleteObj(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void updateObj(int index, Resume resume) {
        String uuid = resume.getUuid();
        storage.put(uuid, resume);
    }
}

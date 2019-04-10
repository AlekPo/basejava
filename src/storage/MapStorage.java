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

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean checkIn(Object searchKey) {
        String uuid = (String) searchKey;
        return storage.containsKey(uuid);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        String uuid = (String) searchKey;
        return storage.get(uuid);
    }

    @Override
    protected void insertObj(Object searchKey, Resume resume) {
        String uuid = resume.getUuid();
        storage.put(uuid, resume);
    }

    @Override
    protected void deleteObj(Object searchKey) {
        String uuid = (String) searchKey;
        storage.remove(uuid);
    }

    @Override
    protected void updateObj(Object searchKey, Resume resume) {
        String uuid = (String) searchKey;
        storage.put(uuid, resume);
    }
}

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

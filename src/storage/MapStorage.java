package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    protected Map<String, Resume> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        String uuid = (String) searchKey;
        return map.containsKey(uuid);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        String uuid = (String) searchKey;
        return map.get(uuid);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        String uuid = resume.getUuid();
        map.put(uuid, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        String uuid = (String) searchKey;
        map.remove(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        String uuid = (String) searchKey;
        map.put(uuid, resume);
    }
}

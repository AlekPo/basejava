package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

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
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey_Uuid) {
        String uuid = (String) searchKey_Uuid;
        return map.containsKey(uuid);
    }

    @Override
    protected Resume doGet(Object searchKey_Uuid) {
        String uuid = (String) searchKey_Uuid;
        return map.get(uuid);
    }

    @Override
    protected void doSave(Object searchKey_Uuid, Resume resume) {
        String uuid = resume.getUuid();
        map.put(uuid, resume);
    }

    @Override
    protected void doDelete(Object searchKey_Uuid) {
        String uuid = (String) searchKey_Uuid;
        map.remove(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey_Uuid, Resume resume) {
        String uuid = (String) searchKey_Uuid;
        map.put(uuid, resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }
}

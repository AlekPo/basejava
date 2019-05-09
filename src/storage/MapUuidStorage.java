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
    protected boolean isExist(Object searchKeyUuid) {
        return map.containsKey((String) searchKeyUuid);
    }

    @Override
    protected Resume doGet(Object searchKeyUuid) {
        return map.get((String) searchKeyUuid);
    }

    @Override
    protected void doSave(Object searchKeyUuid, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKeyUuid) {
        map.remove((String) searchKeyUuid);
    }

    @Override
    protected void doUpdate(Object searchKeyUuid, Resume resume) {
        map.put((String) searchKeyUuid, resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }
}

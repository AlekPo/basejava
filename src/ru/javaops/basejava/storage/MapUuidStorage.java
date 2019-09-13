package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

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
    protected boolean isExist(String searchKeyUuid) {
        return map.containsKey(searchKeyUuid);
    }

    @Override
    protected Resume doGet(String searchKeyUuid) {
        return map.get(searchKeyUuid);
    }

    @Override
    protected void doSave(String searchKeyUuid, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(String searchKeyUuid) {
        map.remove(searchKeyUuid);
    }

    @Override
    protected void doUpdate(String searchKeyUuid, Resume resume) {
        map.put(searchKeyUuid, resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }
}

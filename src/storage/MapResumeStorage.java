package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

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
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey_Resume) {
        return searchKey_Resume != null;
    }

    @Override
    protected Resume doGet(Object searchKey_Resume) {
        return (Resume) searchKey_Resume;
    }

    @Override
    protected void doSave(Object searchKey_Resume, Resume resume) {
        String uuid = resume.getUuid();
        map.put(uuid, resume);
    }

    @Override
    protected void doDelete(Object searchKey_Resume) {
        String uuid = ((Resume) searchKey_Resume).getUuid();
        map.remove(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey_Resume, Resume resume) {
        String uuid = resume.getUuid();
        map.put(uuid, resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }
}

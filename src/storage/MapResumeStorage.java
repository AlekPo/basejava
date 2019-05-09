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
    protected boolean isExist(Object searchKeyResume) {
        return searchKeyResume != null;
    }

    @Override
    protected Resume doGet(Object searchKeyResume) {
        return (Resume) searchKeyResume;
    }

    @Override
    protected void doSave(Object searchKeyResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKeyResume) {
        map.remove(((Resume) searchKeyResume).getUuid());
    }

    @Override
    protected void doUpdate(Object searchKeyResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }
}

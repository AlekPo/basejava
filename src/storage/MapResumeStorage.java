package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

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
    protected boolean isExist(Resume searchKeyResume) {
        return searchKeyResume != null;
    }

    @Override
    protected Resume doGet(Resume searchKeyResume) {
        return searchKeyResume;
    }

    @Override
    protected void doSave(Resume searchKeyResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume searchKeyResume) {
        map.remove((searchKeyResume).getUuid());
    }

    @Override
    protected void doUpdate(Resume searchKeyResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }
}

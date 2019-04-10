package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    List<Resume> storage = new ArrayList<>();

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
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resTemp;
        for (int i = 0; i < storage.size(); i++) {
            resTemp = storage.get(i);
            if (resTemp.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean checkIn(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void insertObj(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void deleteObj(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void updateObj(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }
}

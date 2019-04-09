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
    protected int getIndex(String uuid) {
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
    protected boolean checkIn(int index, String uuid) {
        return index >= 0;
    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected boolean checkStorageOverflow() {
        return false;
    }

    @Override
    protected void insertObj(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void deleteObj(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    protected void updateObj(int index, Resume resume) {
        storage.set(index, resume);
    }
}

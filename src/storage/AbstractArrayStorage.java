package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int tempSize;
    protected int index;
    protected int tempIndex = 0;

    @Override
    public int size() {
        return tempSize;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, tempSize, null);
        tempSize = 0;
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Error - Resume uuid '" + uuid + "' not found");
            return null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, tempSize);
    }

    @Override
    public void save(Resume resume) {
        index = findIndex(resume.getUuid());
        if (tempSize == STORAGE_LIMIT) {
            System.out.println("Error - the resume database is full");
        } else if (index < 0) {
            insertObj(index);
            storage[tempIndex] = resume;
            tempSize++;
        } else {
            System.out.println("Error - Resume with uuid '" +
                    resume.getUuid() + "' already have");
        }
    }

    @Override
    public void delete(String uuid) {
        index = findIndex(uuid);
        if (index >= 0) {
            deleteObj(index);
            tempSize--;
        } else {
            System.out.println("Error - Resume uuid '" + uuid + "' not found");
        }
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Error - Resume uuid '" + resume.getUuid()
                    + "' not found");
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract void insertObj(int index);

    protected abstract void deleteObj(int index);
}

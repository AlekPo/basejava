package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int tempSize;

    public void clear() {
        Arrays.fill(storage, 0, tempSize, null);
        tempSize = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Error - model.Resume uuid '" + resume.getUuid()
                    + "' not found");
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (tempSize == STORAGE_LIMIT) {
            System.out.println("Error - the resume database is full");
        } else if (index == -1) {
            storage[tempSize] = resume;
            tempSize++;
        } else {
            System.out.println("Error - model.Resume with uuid '" +
                    resume.getUuid() + "' already have");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Error - model.Resume uuid '" + uuid + "' not found");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[tempSize - 1];
            storage[tempSize - 1] = null;
            tempSize--;
        } else {
            System.out.println("Error - model.Resume uuid '" + uuid + "' not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] tempStorage = new Resume[tempSize];
        System.arraycopy(storage, 0, tempStorage, 0, tempSize);
        return tempStorage;
    }

    public int size() {
        return tempSize;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

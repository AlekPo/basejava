import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int tempSize;

    void clear() {
        Arrays.fill(storage, 0, tempSize, null);
        tempSize = 0;
    }

    void update(Resume resume) {
        if (tempSize == 0) {
            System.out.println("Error - the resume database is empty");
        }
        for (int i = 0; i < tempSize; i++) {
            if (resume.uuid.equals(storage[i].uuid)) {
                storage[i] = resume;
                break;
            } else if ((i + 1) == tempSize) {
                System.out.println("Error - Resume uuid '" + resume.uuid
                                    + "' not found");
            }
        }
    }

    void save(Resume r) {
        if (tempSize == storage.length) {
            System.out.println("Error - the resume database is full");
        } else if (tempSize == 0) {
            storage[tempSize] = r;
            tempSize++;
        } else {
            for (int i = 0; i < tempSize; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Error - Resume with uuid '" + r.uuid
                                        + "' already have");
                    break;
                } else if ((i + 1) == tempSize) {
                    storage[tempSize] = r;
                    tempSize++;
                    break;
                }
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        System.out.println("Error - Resume uuid '" + uuid + "' not found");
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].uuid)) {
                if ((tempSize - 1 - i) >= 0) {
                    System.arraycopy(storage, i + 1, storage, i,
                                    tempSize - 1 - i);
                }
                storage[tempSize - 1] = null;
                tempSize--;
                break;
            } else if ((i + 1) == tempSize) {
                System.out.println("Error - Resume uuid '" + uuid
                                    + "' not found");
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] tempStorage = new Resume[tempSize];
        System.arraycopy(storage, 0, tempStorage, 0, tempSize);
        return tempStorage;
    }

    int size() {
        return tempSize;
    }
}

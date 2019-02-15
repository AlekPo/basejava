import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        int tempSize = size();
        for (int i = 0; i < tempSize; i++) {
            Arrays.fill(storage, 0, tempSize, null);
        }
    }

    void save(Resume r) {
        int tempSize = size();
        if (tempSize == storage.length) {
            System.out.println("Error - the resume database is full");
        } else if (tempSize == 0) {
            storage[tempSize] = r;
        } else {
            for (int i = 0; i < tempSize; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Error - Resume with uuid '" + r.uuid + "' already have");
                    break;
                } else if (i + 1 == tempSize) {
                    storage[tempSize] = r;
                }
            }
        }
    }

    Resume get(String uuid) {
        int tempSize = size();
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        System.out.println("Error - uuid '" + uuid + "' not found");
        return null;
    }

    void delete(String uuid) {
        int tempSize = size();
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].uuid)) {
                if (tempSize - 1 - i >= 0) {
                    System.arraycopy(storage, i + 1, storage, i, tempSize - 1 - i);
                }
                storage[tempSize - 1] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int tempSize = size();
        Resume[] tempStorage = new Resume[tempSize];
        System.arraycopy(storage, 0, tempStorage, 0, tempSize);
        return tempStorage;
    }

    int size() {
        int counter = 0;
        for (Resume resCount : storage) {
            if (resCount != null) {
                counter++;
            }
        }
        return counter;
    }
}

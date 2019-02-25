import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int tempSize; //Текущее заполнение базы резюме
    private int checkNumber; //Позиция обнаруженного, в базе резюме, совпадения со входящим запросом

    void clear() {
        Arrays.fill(storage, 0, tempSize, null);
        tempSize = 0;
    }

    void update(Resume resume) {
        if (checkIn(resume)) {
            storage[checkNumber] = resume;
        } else {
            System.out.println("Error - Resume uuid '" + resume.getUuid()
                    + "' not found");
        }
    }

    void save(Resume resume) {
        if (tempSize == storage.length) {
            System.out.println("Error - the resume database is full");
        } else if (!checkIn(resume)) {
            storage[tempSize] = resume;
            tempSize++;
        } else {
            System.out.println("Error - Resume with uuid '" +
                    resume.getUuid() + "' already have");
        }
    }

    Resume get(String uuid) {
        if (checkIn(uuid)) {
            return storage[checkNumber];
        } else {
            System.out.println("Error - Resume uuid '" + uuid + "' not found");
            return null;
        }
    }

    void delete(String uuid) {
        if (checkIn(uuid)) {
            storage[checkNumber] = storage[tempSize - 1];
            storage[tempSize - 1] = null;
            tempSize--;
        } else {
            System.out.println("Error - Resume uuid '" + uuid + "' not found");
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

    private boolean checkIn(Resume resume) {
        for (int i = 0; i < tempSize; i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                checkNumber = i;
                return true;
            }
        }
        checkNumber = 0;
        return false;
    }

    private boolean checkIn(String uuid) {
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                checkNumber = i;
                return true;
            }
        }
        checkNumber = 0;
        return false;
    }
}

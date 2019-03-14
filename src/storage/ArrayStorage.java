package storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int insertObj(int index) {
        return size;
    }

    @Override
    protected void deleteObj(int index) {
        storage[index] = storage[size - 1];
    }
}

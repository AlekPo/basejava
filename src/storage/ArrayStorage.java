package storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < tempSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int insertObj(int index) {
        return tempSize;
    }

    @Override
    protected void deleteObj(int index) {
        storage[index] = storage[tempSize - 1];
        storage[tempSize - 1] = null;
    }
}

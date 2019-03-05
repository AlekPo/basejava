package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, tempSize, searchKey);
    }

    @Override
    protected void insertObj(int index) {
        index = (index * -1) - 1;
        System.arraycopy(storage, index, storage, index + 1, tempSize - index);
        tempIndex = index;
    }

    @Override
    protected void deleteObj(int index) {
        if (tempSize - 1 - index > 0) {
            System.arraycopy(storage, index + 1, storage, index, tempSize - 1 - index);
        } else {
            storage[index] = null;
        }
    }
}

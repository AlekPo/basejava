package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected int insertIndex(int index) {
        int insIndex = -index - 1;
        System.arraycopy(storage, insIndex, storage, insIndex + 1, size - insIndex);
        return insIndex;
    }

    @Override
    protected void deleteIndex(int index) {
        int rest = size - 1 - index;
        if (rest > 0) {
            System.arraycopy(storage, index + 1, storage, index, rest);
        }
    }
}

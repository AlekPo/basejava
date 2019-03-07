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
    protected int insertObj(int index) {
        int insIndex = -index - 1;
        System.arraycopy(storage, insIndex, storage, insIndex + 1, tempSize - insIndex);
        return insIndex;
    }

    @Override
    protected void deleteObj(int index) {
        int rest = tempSize - 1 - index;
        System.arraycopy(storage, index + 1, storage, index, rest);
    }
}

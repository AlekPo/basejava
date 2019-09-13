package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based map for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR =
            (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected void fillDeletedElement(int index) {
        int rest = size - 1 - index;
        if (rest > 0) {
            System.arraycopy(storage, index + 1, storage, index, rest);
        }
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insIndex = -index - 1;
        System.arraycopy(storage, insIndex, storage, insIndex + 1, size - insIndex);
        storage[insIndex] = resume;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}

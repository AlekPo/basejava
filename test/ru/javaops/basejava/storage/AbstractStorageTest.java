package ru.javaops.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.ResumeTestData;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Name1");
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Name2");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Name3");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Name4");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertSize(3);
//        Assert.assertEquals(Stream.of(RESUME_1, RESUME_2, RESUME_3)
//                .sorted()
//                .collect(Collectors.toList()), list);
        List<Resume> listTest = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(listTest);
        Assert.assertEquals(listTest, list);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3, "New Name");
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}
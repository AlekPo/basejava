package storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

//    @Override
//    @Ignore
//    @Test
//    public void getAllSorted() {
//
//    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {

    }

//
//    private Storage storage;
//    private static final String UUID_1 = "uuid1";
//    private static final Resume RESUME_1 = new Resume(UUID_1);
//    private static final String UUID_2 = "uuid2";
//    private static final Resume RESUME_2 = new Resume(UUID_2);
//    private static final String UUID_3 = "uuid3";
//    private static final Resume RESUME_3 = new Resume(UUID_3);
//    private static final String UUID_4 = "uuid4";
//    private static final Resume RESUME_4 = new Resume(UUID_4);

//    public MapStorageTest() {
//        storage = new MapStorage();
//    }
//
//    @Before
//    public void setUp() {
//        storage.clear();
//        storage.save(RESUME_1);
//        storage.save(RESUME_2);
//        storage.save(RESUME_3);
//    }
//
//    @Test
//    public void size() {
//        assertSize(3);
//    }
//
//    @Test
//    public void clear() {
//        storage.clear();
//        assertSize(0);
//    }
//
//    @Test
//    public void get() {
//        assertGet(RESUME_1);
//        assertGet(RESUME_2);
//        assertGet(RESUME_3);
//    }
//
//    @Test(expected = NotExistStorageException.class)
//    public void getNotExist() {
//        storage.get("dummy");
//    }
//
//    @Test
//    public void getAll() {
//        Assert.assertArrayEquals(new Resume[]{RESUME_2, RESUME_1, RESUME_3}, storage.getAll());
//        assertSize(3);
//    }
//
//    @Test
//    public void save() {
//        storage.save(RESUME_4);
//        assertSize(4);
//        assertGet(RESUME_4);
//    }
//
//    @Test(expected = ExistStorageException.class)
//    public void saveExist() {
//        storage.save(RESUME_3);
//    }
//
///*    @Test(expected = StorageException.class)
//    public void saveOverflow() {
//        map.clear();
//        try {
//            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
//                map.save(new Resume());
//            }
//        } catch (StorageException e) {
//            Assert.fail("" + e);
//        }
//        map.save(new Resume());
//    }*/
//
//    @Test(expected = NotExistStorageException.class)
//    public void delete() {
//        storage.delete(UUID_3);
//        assertSize(2);
//        storage.get(UUID_3);
//    }
//
//    @Test(expected = NotExistStorageException.class)
//    public void deleteNotExist() {
//        storage.delete("dummy");
//    }
//
//    @Test
//    public void update() {
//        Resume RESUME_3_NEW = new Resume(UUID_3);
//        storage.update(RESUME_3_NEW);
//        Assert.assertSame(RESUME_3_NEW, storage.get(UUID_3));
//    }
//
//    @Test(expected = NotExistStorageException.class)
//    public void updateNotExist() {
//        storage.update(RESUME_4);
//    }
//
//    private void assertSize(int size) {
//        Assert.assertEquals(size, storage.size());
//    }
//
//    private void assertGet(Resume r) {
//        Assert.assertEquals(r, storage.get(r.getUuid()));
//    }
}
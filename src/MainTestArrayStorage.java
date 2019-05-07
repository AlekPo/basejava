import model.Resume;
import storage.SortedArrayStorage;
import storage.Storage;

/**
 * Test for your map.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
//        final Resume r1 = new Resume(uuid);
//        r1.setUuid("uuid1");
//        final Resume r2 = new Resume(uuid);
//        r2.setUuid("uuid2");
//        final Resume r3 = new Resume(uuid);
//        r3.setUuid("uuid3");
//
//        final Resume r4 = new Resume(uuid);
//        r4.setUuid("uuid4");
//        final Resume r5 = new Resume(uuid);
//        r5.setUuid("uuid2");
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");

        final Resume r4 = new Resume("uuid4");
        final Resume r5 = new Resume("uuid5");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("\nUpdate r4, uuid4:");
        ARRAY_STORAGE.update(r4);
        printAll();
        System.out.println("\nUpdate r5, uuid2:");
        ARRAY_STORAGE.update(r5);
        printAll();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}

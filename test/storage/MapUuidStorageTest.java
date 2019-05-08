package storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapUuidStorageTest extends AbstractStorageTest {
    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    @Ignore
    @Test
    public void saveOverflow() {

    }
}
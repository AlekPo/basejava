package storage;

import model.Resume;

public interface Storage {

    int size();

    void clear();

    Resume get(String uuid);

    Resume[] getAll();

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);
}

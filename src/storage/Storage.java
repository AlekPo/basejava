package storage;

import model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void clear();

    Resume get(String uuid);

    List<Resume> getAllSorted();

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);
}

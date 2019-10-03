package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String dir) {
        super(dir);
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            try {
                return (Resume) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new StorageException("File read error", null, e);
            }
        }
    }
}

package ru.javaops.basejava.storage;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private IOStrategy strategy;

    protected FileStorage(IOStrategy strategy, String dir) {
        this.strategy = strategy;
        directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.exists()) {
            throw new IllegalArgumentException("Path '" + directory.getAbsolutePath() + "' does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("'" + directory + "' in path '" + directory.getAbsolutePath() + "' is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("Directory '" + directory.getAbsolutePath() + "' is not readable/writable");
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Error creating file, directory: " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File deletion error, directory: " + file.getAbsolutePath(), file.getName());
        }
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            strategy.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error, directory: " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list()).length;
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            doDelete(file);
        }
    }
}

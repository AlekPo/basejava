package ru.javaops.basejava.storage;

import ru.javaops.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PathObjectIOStream extends AbstractPathStorage {
    private StrategyObjectIOStream strategy;

    protected PathObjectIOStream(StrategyObjectIOStream strategy, File directory) {
        super(directory.toString());
        this.strategy = strategy;
    }

    @Override
    protected void doWrite(OutputStream os, Resume resume) throws IOException {
        strategy.doWrite(os, resume);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return strategy.doRead(is);
    }
}

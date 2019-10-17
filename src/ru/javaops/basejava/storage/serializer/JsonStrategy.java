package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStrategy implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}

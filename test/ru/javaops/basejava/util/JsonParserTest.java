package ru.javaops.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.basejava.model.AbstractSection;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.SectionType;

import java.util.Map;

import static ru.javaops.basejava.storage.AbstractStorageTest.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void testAbstractSection() {
        for (Map.Entry<SectionType, AbstractSection> entry : RESUME_1.getSections().entrySet()) {
            AbstractSection abstractSection = entry.getValue();
            String json = JsonParser.write(abstractSection, AbstractSection.class);
            System.out.println(json);
            AbstractSection newAbstractSection = JsonParser.read(json, AbstractSection.class);
            Assert.assertEquals(abstractSection, newAbstractSection);
        }
    }
}
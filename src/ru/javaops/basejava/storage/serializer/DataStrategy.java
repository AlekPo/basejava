package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DataStrategy implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            //contacts
            writeCollection(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            //sections
            writeCollection(dos, resume.getSections().entrySet(), entry -> {
                SectionType section = entry.getKey();
                dos.writeUTF(section.name());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) resume.getSection(section)).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        saveStringSection(resume, section, dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        saveOrganizationSection(resume, section, dos);
                        break;
                    default:
                        throw new StorageException("Calling an unknown section from SectionType");
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            //contacts
            readCollection(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            //sections
            readCollection(dis, () -> {
                String sectionName = dis.readUTF();
                AbstractSection section;
                switch (sectionName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        section = new TextSection(dis.readUTF());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        section = readStringSection(dis);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        section = readOrganizationSection(dis);
                        break;
                    default:
                        throw new StorageException("Calling an unknown section from SectionType");
                }
                resume.setSection(SectionType.valueOf(sectionName), section);
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface ElementWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
        Objects.requireNonNull(collection);
        dos.write(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private void saveStringSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        List<String> strings = ((ListSection) resume.getSection(sectionType)).getItems();
        writeCollection(dos, strings, dos::writeUTF);
    }

    private void saveOrganizationSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        List<Organization> organizations = ((OrganizationSection) resume.getSection(sectionType)).getOrganizations();
        writeCollection(dos, organizations, organization -> {
            dos.writeUTF(organization.getHomePage().getName());
            saveCheckIsNull(organization.getHomePage().getUrl(), dos);
            List<Position> positions = organization.getPositions();
            writeCollection(dos, positions, position -> {
                saveDate(position.getDateStart(), dos);
                saveDate(position.getDateEnd(), dos);
                dos.writeUTF(position.getTitle());
                saveCheckIsNull(position.getDescription(), dos);
            });
        });
    }

    private void saveCheckIsNull(String str, DataOutputStream dos) throws IOException {
//          Решение только для для NULL объекта.
        dos.writeUTF(Objects.isNull(str) ? "" : str);
//          Решение для NULL объекта и пустой строки "".
//        if (Objects.isNull(str)) {
//            dos.writeUTF("null");
//        } else if (str.isEmpty()) {
//            dos.writeUTF("");
//        } else {
//            dos.writeUTF(str);
//        }
    }

    private void saveDate(YearMonth yearMonth, DataOutputStream dos) throws IOException {
        String year = String.valueOf(yearMonth.getYear());
        dos.writeUTF(year);
        String month = String.valueOf(yearMonth.getMonthValue());
        dos.writeUTF(month);
    }

    @FunctionalInterface
    private interface ElementReader {
        void read() throws IOException;
    }

    private void readCollection(DataInputStream dis, ElementReader reader) throws IOException {
        int size = dis.read();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private ListSection readStringSection(DataInputStream dis) throws IOException {
        List<String> strings = new ArrayList<>();
        readCollection(dis, () -> strings.add(dis.readUTF()));
        return new ListSection(strings);
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        readCollection(dis, () -> {
            String nameLink = dis.readUTF();
            String httpLink = dis.readUTF();
            List<Position> positions = new ArrayList<>();
            readCollection(dis, () -> {
                YearMonth dateStart = readDate(dis);
                YearMonth dateEnd = readDate(dis);
                String title = dis.readUTF();
                String description = dis.readUTF();
                positions.add(new Position(dateStart, dateEnd, title, readCheckIsNull(description)));
            });
            organizations.add(new Organization(new Link(nameLink, readCheckIsNull(httpLink)), positions));
        });
        return new OrganizationSection(organizations);
    }

    private String readCheckIsNull(String str) {
//          Решение только для NULL объекта.
        return (str.equals("") ? null : str);
//          Решение для NULL объекта и пустой строки "".
//        return str.equals("null") ? null : str;
    }

    private YearMonth readDate(DataInputStream dis) throws IOException {
        int year = Integer.parseInt(dis.readUTF());
        int month = Integer.parseInt(dis.readUTF());
        return YearMonth.of(year, month);
    }
}

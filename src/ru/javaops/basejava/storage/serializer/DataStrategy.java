package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStrategy implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            //contacts
            int sizeContacts = resume.getContacts().size();
            dos.write(sizeContacts);
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //sections
            int sizeSections = resume.getSections().size();
            dos.write(sizeSections);
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType section = entry.getKey();
                dos.writeUTF(section.name());
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        saveTextSection(resume, section, dos);
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
                        throw new StorageException("Call an indescribable section from SectionType");
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            //contacts
            int sizeContacts = dis.read();
            for (int i = 0; i < sizeContacts; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //sections
            int sizeSections = dis.read();
            for (int i = 0; i < sizeSections; i++) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        readTextSection(resume, SectionType.valueOf(sectionName), dis);
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        readStringSection(resume, SectionType.valueOf(sectionName), dis);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        readOrganizationSection(resume, SectionType.valueOf(sectionName), dis);
                        break;
                    default:
                        throw new StorageException("Call an indescribable section from SectionType");
                }
            }
            return resume;
        }
    }

    private void saveTextSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        if (!(resume.getSection(sectionType) == null)) {
            dos.write(1);
            String string = ((TextSection) resume.getSection(sectionType)).getContent();
            dos.writeUTF(string);
        } else {
            dos.write(0);
        }
    }

    private void saveStringSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        int size;
        if (!(resume.getSection(sectionType) == null)) {
            List<String> strings = ((ListSection) resume.getSection(sectionType)).getItems();
            size = strings.size();
            dos.write(size);
            for (String string : strings) {
                dos.writeUTF(string);
            }
        } else {
            size = 0;
            dos.write(size);
        }
    }

    private void saveOrganizationSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        if (!(resume.getSection(sectionType) == null)) {
            List<Organization> organizations = ((OrganizationSection) resume.getSection(sectionType)).getOrganizations();
            dos.write(organizations.size());
            for (Organization organization : organizations) {
                dos.writeUTF(organization.getHomePage().getName());
                dos.writeUTF(Objects.nonNull(organization.getHomePage().getUrl()) ? organization.getHomePage().getUrl() : "null");
                List<Position> positions = organization.getPositions();
                dos.write(positions.size());
                for (Position position : positions) {
                    saveDate(position.getDateStart().getYear(), dos);
                    saveDate(position.getDateStart().getMonthValue(), dos);
                    saveDate(position.getDateEnd().getYear(), dos);
                    saveDate(position.getDateEnd().getMonthValue(), dos);
                    dos.writeUTF(position.getTitle());
                    dos.writeUTF(Objects.nonNull(position.getDescription()) ? position.getDescription() : "null");
                }
            }
        } else {
            dos.write(0);
        }
    }

    private void saveDate(int date, DataOutputStream dos) throws IOException {
        dos.writeUTF(String.valueOf(date));
    }

    private void readTextSection(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
        int size = dis.read();
        if (!(size == 0)) {
            resume.setSection(sectionType, new TextSection(dis.readUTF()));
        }
    }

    private void readStringSection(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
        int size = dis.read();
        if (!(size == 0)) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                strings.add(dis.readUTF());
            }
            resume.setSection(sectionType, new ListSection(strings));
        }
    }

    private void readOrganizationSection(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
        int size = dis.read();
        if (!(size == 0)) {
            List<Organization> organizations = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String nameLink = dis.readUTF();
                String httpLink = dis.readUTF();
                int sizePositions = dis.read();
                List<Position> positions = new ArrayList<>();
                for (int j = 0; j < sizePositions; j++) {
                    int yearStart = readDate(dis);
                    int monthStart = readDate(dis);
                    int yearEnd = readDate(dis);
                    int monthEnd = readDate(dis);
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    positions.add(new Position(YearMonth.of(yearStart, monthStart), YearMonth.of(yearEnd, monthEnd), title
                            , (description.equals("null") ? null : description)));
                }
                organizations.add(new Organization(new Link(nameLink, (httpLink.equals("null") ? null : httpLink)), positions));
            }
            resume.setSection(sectionType, new OrganizationSection(organizations));
        }
    }

    private int readDate(DataInputStream dis) throws IOException {
        return Integer.parseInt(dis.readUTF());
    }
}

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
                        resume.setSection(SectionType.valueOf(sectionName), new TextSection(dis.readUTF()));
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
                        throw new StorageException("Calling an unknown section from SectionType");
                }
            }
            return resume;
        }
    }

    private void saveStringSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        List<String> strings = ((ListSection) resume.getSection(sectionType)).getItems();
        int size = strings.size();
        dos.write(size);
        for (String string : strings) {
            dos.writeUTF(string);
        }
    }

    private void saveOrganizationSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        List<Organization> organizations = ((OrganizationSection) resume.getSection(sectionType)).getOrganizations();
        dos.write(organizations.size());
        for (Organization organization : organizations) {
            dos.writeUTF(organization.getHomePage().getName());
//          Решение для NULL объекта и пустой строки ""
//            String nullStr = "null";
//            if (Objects.isNull(organization.getHomePage().getUrl())) {
//                dos.writeUTF(nullStr);
//            } else if (organization.getHomePage().getUrl().isEmpty()) {
//                dos.writeUTF("");
//            } else {
//                dos.writeUTF(organization.getHomePage().getUrl());
//            }
//          Решение для NULL объекта
            saveCheckIsNull(organization.getHomePage().getUrl(), dos);
//
            List<Position> positions = organization.getPositions();
            dos.write(positions.size());
            for (Position position : positions) {
                saveDates(position, dos);
                dos.writeUTF(position.getTitle());
//              Решение для NULL объекта и пустой строки ""
//                if (Objects.isNull(position.getDescription())) {
//                    dos.writeUTF(nullStr);
//                } else if (position.getDescription().isEmpty()) {
//                    dos.writeUTF("");
//                } else {
//                    dos.writeUTF(position.getDescription());
//                }
//              Решение для NULL объекта
                saveCheckIsNull(position.getDescription(), dos);
//
            }
        }
    }

    private void saveCheckIsNull(String str, DataOutputStream dos) throws IOException {
        dos.writeUTF(Objects.isNull(str) ? "" : str);
    }

    private void saveDates(Position position, DataOutputStream dos) throws IOException {
        String yearStart = String.valueOf(position.getDateStart().getYear());
        dos.writeUTF(yearStart);
        String monthStart = String.valueOf(position.getDateStart().getMonthValue());
        dos.writeUTF(monthStart);
        String yearEnd = String.valueOf(position.getDateEnd().getYear());
        dos.writeUTF(yearEnd);
        String monthEnd = String.valueOf(position.getDateEnd().getMonthValue());
        dos.writeUTF(monthEnd);
    }

    private void readStringSection(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
        int size = dis.read();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            strings.add(dis.readUTF());
        }
        resume.setSection(sectionType, new ListSection(strings));
    }

    private void readOrganizationSection(Resume resume, SectionType sectionType, DataInputStream dis) throws IOException {
        int size = dis.read();
        List<Organization> organizations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String nameLink = dis.readUTF();
            String httpLink = dis.readUTF();
            int sizePositions = dis.read();
            List<Position> positions = new ArrayList<>();
            for (int j = 0; j < sizePositions; j++) {
                YearMonth dateStart = readDate(dis);
                YearMonth dateEnd = readDate(dis);
                String title = dis.readUTF();
                String description = dis.readUTF();
//          Решение для NULL объекта и пустой строки ""
//                String nullStr = "null";
//                positions.add(new Position(dateStart, dateEnd, title, (description.equals(nullStr) ? null : description)));
//          Решение для NULL объекта
                positions.add(new Position(dateStart, dateEnd, title, (description.equals("") ? null : description)));
//
            }
//          Решение для NULL объекта и пустой строки ""
//            String nullStr = "null";
//            organizations.add(new Organization(new Link(nameLink, (httpLink.equals(nullStr) ? null : httpLink)), positions));
//          Решение для NULL объекта
            organizations.add(new Organization(new Link(nameLink, (httpLink.equals("") ? null : httpLink)), positions));
//
        }
        resume.setSection(sectionType, new OrganizationSection(organizations));
    }

    private YearMonth readDate(DataInputStream dis) throws IOException {
        int year = Integer.parseInt(dis.readUTF());
        int month = Integer.parseInt(dis.readUTF());
        return YearMonth.of(year, month);
    }
}

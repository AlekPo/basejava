package ru.javaops.basejava.storage.serializer;

import ru.javaops.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javaops.basejava.model.SectionType.*;

public class DataStrategy implements SerializationStrategy {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            //contacts
            int sizeContacts = resume.getContacts().size();
            dos.write(sizeContacts);
            if (!(sizeContacts == 0)) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                }
            }
            //OBJECTIVE
            saveTextSection(resume, OBJECTIVE, dos);
            //PERSONAL
            saveTextSection(resume, PERSONAL, dos);
            //ACHIEVEMENT
            saveStringSection(resume, ACHIEVEMENT, dos);
            //QUALIFICATIONS
            saveStringSection(resume, QUALIFICATIONS, dos);
            //EXPERIENCE
            saveOrganizationSection(resume, EXPERIENCE, dos);
            //EDUCATION
            saveOrganizationSection(resume, EDUCATION, dos);
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
            //OBJECTIVE
            readTextSection(resume, OBJECTIVE, dis);
            //PERSONAL
            readTextSection(resume, PERSONAL, dis);
            //ACHIEVEMENT
            readStringSection(resume, ACHIEVEMENT, dis);
            //QUALIFICATIONS
            readStringSection(resume, QUALIFICATIONS, dis);
            //EXPERIENCE
            readOrganizationSection(resume, EXPERIENCE, dis);
            //EDUCATION
            readOrganizationSection(resume, EDUCATION, dis);
            return resume;
        }
    }

    private void saveTextSection(Resume resume, SectionType sectionType, DataOutputStream dos) throws IOException {
        if (!(resume.getSection(sectionType) == null)) {
            dos.write(1);
            dos.writeUTF(resume.getSection(sectionType).toString());
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
            if (!(size == 0)) {
                for (String string : strings) {
                    dos.writeUTF(string);
                }
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
                dos.writeUTF(organization.getHomePage().getUrl());
                List<Position> positions = organization.getPositions();
                dos.write(positions.size());
                for (Position position : positions) {
                    int yearStart = position.getDateStart().getYear();
                    dos.writeUTF(String.valueOf(yearStart));
                    int monthStart = position.getDateStart().getMonthValue();
                    dos.writeUTF(String.valueOf(monthStart));
                    int yearEnd = position.getDateEnd().getYear();
                    dos.writeUTF(String.valueOf(yearEnd));
                    int monthEnd = position.getDateEnd().getMonthValue();
                    dos.writeUTF(String.valueOf(monthEnd));
                    dos.writeUTF(position.getTitle());
                    dos.writeUTF(position.getDescription());
                }
            }
        } else {
            dos.write(0);
        }
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
                    int yearStart = Integer.parseInt(dis.readUTF());
                    int monthStart = Integer.parseInt(dis.readUTF());
                    int yearEnd = Integer.parseInt(dis.readUTF());
                    int monthEnd = Integer.parseInt(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    positions.add(new Position(YearMonth.of(yearStart, monthStart), YearMonth.of(yearEnd, monthEnd), title, description));
                }
                organizations.add(new Organization(new Link(nameLink, httpLink), positions));
            }
            resume.setSection(sectionType, new OrganizationSection(organizations));
        }
    }
}

package model;

import java.util.UUID;

/**
 * Initial resume class
 */
//public class Resume implements Comparable<Resume> {
public class Resume {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    //
    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }
    //

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid + " - " + fullName;
    }

//    @Override
//    public int compareTo(Resume o) {
//        return uuid.compareTo(o.uuid);
//    }
}

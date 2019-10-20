package ru.javaops.basejava.model;

import ru.javaops.basejava.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth dateStart;
    @XmlJavaTypeAdapter(YearMonthAdapter.class)
    private YearMonth dateEnd;
    private String title;
    private String description;

    public Position(YearMonth dateStart, YearMonth dateEnd, String title, String description) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    public Position() {
    }

    public YearMonth getDateStart() {
        return dateStart;
    }

    public YearMonth getDateEnd() {
        return dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (!dateStart.equals(position.dateStart)) return false;
        if (!dateEnd.equals(position.dateEnd)) return false;
        if (!title.equals(position.title)) return false;
        return description != null ? description.equals(position.description) : position.description == null;
    }

    @Override
    public int hashCode() {
        int result = dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String str;
        str = dateStart.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateEnd.format(DateTimeFormatter.ofPattern("MM/yyyy")) + "   ";
        str += Objects.isNull(description) ? title + "\n" : title + "\n" + "   " + description + "\n";
        return str;
    }
}
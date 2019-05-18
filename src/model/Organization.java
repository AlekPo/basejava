package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final YearMonth dateStart;
    private final YearMonth dateEnd;
    private final String title;
    private final String description;

    public Organization(String name, String nameHttp, YearMonth dateStart, YearMonth dateEnd, String title, String description) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = new Link(name, nameHttp);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    public Link getHomePage() {
        return homePage;
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

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!dateStart.equals(that.dateStart)) return false;
        if (!dateEnd.equals(that.dateEnd)) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String str;
        str = homePage.toString();
        str += dateStart.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateEnd.format(DateTimeFormatter.ofPattern("MM/yyyy")) + "   ";
        str += description.isEmpty() ? title + "\n" : title + "\n" + "   " + description + "\n";
        return str;
    }
}

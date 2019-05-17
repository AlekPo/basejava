package model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Organization {
    private String name;
    private String nameHttp;
    private YearMonth dateStart;
    private YearMonth dateEnd;
    private String title;
    private String description;

    public Organization(String name, String nameHttp, YearMonth dateStart, YearMonth dateEnd, String title, String description) {
        this.name = name;
        this.nameHttp = nameHttp;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameHttp() {
        return nameHttp;
    }

    public void setNameHttp(String nameHttp) {
        this.nameHttp = nameHttp;
    }

    public YearMonth getDateStart() {
        return dateStart;
    }

    public void setDateStart(YearMonth dateStart) {
        this.dateStart = dateStart;
    }

    public YearMonth getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(YearMonth dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameHttp != null ? !nameHttp.equals(that.nameHttp) : that.nameHttp != null) return false;
        if (dateStart != null ? !dateStart.equals(that.dateStart) : that.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameHttp != null ? nameHttp.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String str;
        str = name + "\n   ";
        str += nameHttp.isEmpty() ? "" : nameHttp + "\n   ";
        str += dateStart.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " + dateEnd.format(DateTimeFormatter.ofPattern("MM/yyyy")) + "   ";
        str += description.isEmpty() ? title + "\n" : title + "\n" + "   " + description + "\n";
        return str;
    }
}

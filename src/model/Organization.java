package model;

public class Organization {
    private String name;
    private String nameHttp;
    private String date;
    private String level;
    private String function;

    public Organization(String name, String nameHttp, String date, String level, String function) {
        this.name = name;
        this.nameHttp = nameHttp;
        this.date = date;
        this.level = level;
        this.function = function;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameHttp != null ? !nameHttp.equals(that.nameHttp) : that.nameHttp != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        return function != null ? function.equals(that.function) : that.function == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameHttp != null ? nameHttp.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String str;
        str = name + "\n   ";
        str += nameHttp.isEmpty() ? "" : nameHttp + "\n   ";
        str += date + "   " + level + "\n   " + function;
        return str;
    }
}

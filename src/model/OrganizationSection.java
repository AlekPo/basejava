package model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    private String name;
    private String nameHttp;
    private String date;
    private String level;
    private String function;

    private List<Organization> list = new ArrayList<>();

    public void addValue(String name, String nameHttp, String date, String level, String function) {
        list.add(new Organization(name, nameHttp, date, level, function));
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

    public List<Organization> getList() {
        return list;
    }

    public void setList(List<Organization> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameHttp != null ? !nameHttp.equals(that.nameHttp) : that.nameHttp != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (function != null ? !function.equals(that.function) : that.function != null) return false;
        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameHttp != null ? nameHttp.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();
        for (Organization content : list) {
            output.append(" * ").append(content).append("\n\n");
        }
        return output.toString();
    }

}

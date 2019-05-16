package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {

    private List<Organization> list = new ArrayList<>();

    public void addValue(String name, String nameHttp, YearMonth dateStart, YearMonth dateEnd, String level, String function) {
        list.add(new Organization(name, nameHttp, dateStart, dateEnd, level, function));
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

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
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

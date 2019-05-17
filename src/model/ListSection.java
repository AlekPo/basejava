package model;

import java.util.List;

public class ListSection extends AbstractSection {

    private List<String> contents;

    public ListSection(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return contents != null ? contents.equals(that.contents) : that.contents == null;
    }

    @Override
    public int hashCode() {
        return contents != null ? contents.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (String content : contents) {
            output.append(" * ").append(content).append("\n\n");
        }
        return output.toString();
    }
}

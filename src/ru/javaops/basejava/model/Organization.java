package ru.javaops.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization(String name, String nameHttp, Position... positions) {
        this(new Link(name, nameHttp), new ArrayList<>(Arrays.asList(positions)));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Organization() {
    }

    public List<Position> getPositions() {
        return positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

// Хороший конструктор
//    @Override
//    public String toString() {
//
//        StringBuilder output = new StringBuilder();
//        output.append(homePage.toString());
//        for (Position position : positions) {
//            output.append(position);
//        }
//        return output.toString();
//    }

    // Костыль - Добавляет пробелы слева при выводе второго и более элемента positions (при наличии)
    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();
        output.append(homePage.toString());

        for (int i = 0; i < positions.size(); i++) {
            if (i > 0) {
                output.append("   ").append(positions.get(i).toString());
            } else {
                output.append(positions.get(i).toString());
            }
        }
        return output.toString();
    }

}

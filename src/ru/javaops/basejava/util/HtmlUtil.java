package ru.javaops.basejava.util;

import ru.javaops.basejava.model.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.trim().length() == 0;
    }

    public static String textSectionTopToHtml(AbstractSection abstractSection) {
        String text = ((TextSection) abstractSection).getContent();
        return "" +
                "<tr>\n" +
                "   <td colspan=\"2\">\n" +
                "       <h3>" + text + "</h3>\n" +
                "   </td>\n" +
                "</tr>";
    }

    public static String textSectionToHtml(AbstractSection abstractSection) {
        String text = ((TextSection) abstractSection).getContent();
        return "" +
                "<tr>\n" +
                "   <td colspan=\"2\">\n" +
                "       " + text +
                "   </td>\n" +
                "</tr>";
    }

    public static String listSectionToHtml(AbstractSection abstractSection) {
        List<String> items = ((ListSection) abstractSection).getItems();
        StringBuilder text = new StringBuilder();
        for (String content : items) {
            text.append("<li>").append(content).append("</li>\n");
        }
        return "" +
                "<tr>\n" +
                "   <td colspan=\"2\">\n" +
                "       <ul>\n" +
                "           " + text.toString() +
                "       </ul>\n" +
                "   </td>\n" +
                "</tr>";
    }

    public static String OrganizationSectionToHtml(AbstractSection abstractSection) {
        List<Organization> items = ((OrganizationSection) abstractSection).getOrganizations();
        StringJoiner html = new StringJoiner("");
        for (Organization organization : items) {
            String name;
            StringJoiner strPosition = new StringJoiner("");
            if (Objects.isNull(organization.getHomePage().getUrl()) ||
                    organization.getHomePage().getUrl().trim().isEmpty()) {
                name = "<h3>" + organization.getHomePage().getName() + "</h3>\n";
            } else {
                name = "" +
                        "<h3>" +
                        "   <a href=\"" + organization.getHomePage().getUrl() + "\">" +
                        "       " + organization.getHomePage().getName() +
                        "   </a>" +
                        "</h3>\n";
            }
            for (Position position : organization.getPositions()) {
                String dates = position.getDateStart().format(DateTimeFormatter.ofPattern("MM/yyyy")) +
                        " - " + position.getDateEnd().format(DateTimeFormatter.ofPattern("MM/yyyy"));
                String strTitle = position.getTitle();
                String strDescription = Objects.isNull(position.getDescription()) ? "" : position.getDescription();
                strPosition.add("" +
                        "<tr>\n" +
                        "   <td width=\"15%\" style=\"vertical-align: top\">" + dates + "\n" +
                        "   </td>\n" +
                        "   <td><b>" + strTitle + "</b><br>" + strDescription + "</td>\n" +
                        "</tr>\n");
            }
            html.add("" +
                    "<tr>\n" +
                    "   <td colspan=\"2\">\n" +
                    "       " + name +
                    "   </td>\n" +
                    "</tr>\n" +
                    strPosition
            );
        }
        return html.toString();
    }

    public static String replacingQuotes(String str) {
        return str.replaceAll("\"", "'");
    }
}

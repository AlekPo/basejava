package ru.javaops.basejava.web;

import ru.javaops.basejava.Config;
import ru.javaops.basejava.model.ContactType;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    //    protected Storage storage = Config.get().getStorage();
    protected Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");

/*
        String strFirst = "<table border=\"1\" cellspacing=\"0\">\n";
        String strHead = "<tr>\n<td>uuid</td>\n<td>full_name</td>\n</tr>\n";
        String strBody = "";
        String strEnd = "</table>\n";
        StringBuilder builder = new StringBuilder();
        builder.append(strFirst);
        builder.append(strHead);

        List<Resume> list = storage.getAllSorted();
        for (Resume resume : list) {
            strBody = "<tr>\n<td>" + resume.getUuid() + "</td>\n<td>" + resume.getFullName() + "</td>\n</tr>\n";
            builder.append(strBody);
        }
        builder.append(strEnd);
        String strTable = builder.toString();
        response.getWriter().write(strTable);
*/

        Writer writer = response.getWriter();
        writer.write("<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>Список всех резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<section>\n" +
                "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                "    <tr>\n" +
                "        <th>Имя</th>\n" +
                "        <th>Email</th>\n" +
                "    </tr>\n");
        for (Resume resume : storage.getAllSorted()) {
            writer.write(
                    "<tr>\n" +
                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                            "     <td>" + resume.getContact(ContactType.MAIL) + "</td>\n" +
                            "</tr>\n");
        }
        writer.write("</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>\n");
    }
}

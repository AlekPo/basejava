package ru.javaops.basejava.web;

import ru.javaops.basejava.Config;
import ru.javaops.basejava.exception.StorageException;
import ru.javaops.basejava.model.*;
import ru.javaops.basejava.storage.Storage;
import ru.javaops.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//@WebServlet(name = "ResumeServlet", urlPatterns = "/resume")
public class ResumeServlet extends HttpServlet {
    //    protected Storage storage = Config.get().getStorage();
    protected Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;
        /*if (!(uuid).isEmpty()) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
            filling(request, resume);
            storage.update(resume);
        } else {
            resume = new Resume(fullName);
            filling(request, resume);
            storage.save(resume);
        }*/
        if (!(uuid).isEmpty()) {
            storage.delete(uuid);
            resume = new Resume(uuid, fullName);
        } else {
            resume = new Resume(fullName);

        }
        filling(request, resume);
        storage.save(resume);

        response.sendRedirect("resume");
    }

    private void filling(HttpServletRequest request, Resume resume) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (Objects.nonNull(value) && value.trim().length() != 0) {
                resume.setContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType section : SectionType.values()) {
            String value = request.getParameter(section.name());
            switch (section) {
                case OBJECTIVE:
                case PERSONAL:
                    if (Objects.nonNull(value) && value.trim().length() != 0) {
                        resume.setSection(section, new TextSection(value));
                    } else {
                        resume.getSections().remove(section);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (Objects.nonNull(value) && value.trim().length() != 0) {
                        resume.setSection(section, new ListSection(Arrays.asList(value.split("\n"))));
                    } else {
                        resume.getSections().remove(section);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    String[] organizationValues = request.getParameterValues(section.name());
                    String[] organizationHttpValues = request.getParameterValues(section + "_http");
                    if (Objects.isNull(organizationValues)) {
                        break;
                    }
                    for (int i = 0; i < organizationValues.length; i++) {
                        if (!organizationValues[i].trim().isEmpty()) {
                            Link link = new Link(organizationValues[i], organizationHttpValues[i]);
                            String[] positionStartDateValues = request.getParameterValues(section +
                                    "_" + (i + 1) + "_positionStartDate");
                            String[] positionEndDateValues = request.getParameterValues(section +
                                    "_" + (i + 1) + "_positionEndDate");
                            String[] positionNameValues = request.getParameterValues(section +
                                    "_" + (i + 1) + "_positionName");
                            String[] positionDescriptionValues = request.getParameterValues(section +
                                    "_" + (i + 1) + "_positionDescription");
                            List<Position> positions = new ArrayList<>();
                            Position position;
                            for (int k = 0; k < positionStartDateValues.length; k++) {
                                if (!positionNameValues[k].trim().isEmpty()) {
                                    YearMonth startDate = positionStartDateValues[k].isEmpty() ? YearMonth.now() : DateUtil.parsing(positionStartDateValues[k]);
                                    YearMonth endDate = positionEndDateValues[k].isEmpty() ? YearMonth.now() : DateUtil.parsing(positionEndDateValues[k]);
                                    position = new Position(startDate, endDate, positionNameValues[k], positionDescriptionValues[k]);
                                    positions.add(position);
                                }
                            }
                            Organization organization = new Organization(link, positions);
                            organizations.add(organization);
                        }
                    }
                    resume.setSection(section, new OrganizationSection(organizations));
                    break;
                default:
                    throw new StorageException("Calling an unknown section from SectionType");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                if (Objects.nonNull(uuid) && uuid.trim().length() != 0) {
                    resume = storage.get(uuid);
                } else {
                    resume = new Resume("", "Новое резюме");

                    ContactType[] contactTypes = ContactType.values();
                    for (ContactType type : contactTypes) {
                        resume.setContact(type, "");
                    }
                    SectionType[] sectionTypes = SectionType.values();
                    for (SectionType type : sectionTypes) {
                        switch (type) {
                            case OBJECTIVE:
                            case PERSONAL:
                                resume.setSection(type, new TextSection(""));
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                List<String> list = Arrays.asList("", "", "");
                                resume.setSection(type, new ListSection(list));
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                List<Organization> experience = new ArrayList<>();
                                experience.add(new Organization("", "", new Position(YearMonth.now(), YearMonth.now(), "", "")));
                                resume.setSection(type, new OrganizationSection(experience));
                                break;
                        }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
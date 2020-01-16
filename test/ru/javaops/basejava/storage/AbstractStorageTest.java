package ru.javaops.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.Config;
import ru.javaops.basejava.ResumeTestData;
import ru.javaops.basejava.exception.ExistStorageException;
import ru.javaops.basejava.exception.NotExistStorageException;
import ru.javaops.basejava.model.ListSection;
import ru.javaops.basejava.model.Resume;
import ru.javaops.basejava.model.TextSection;

import java.util.*;

import static ru.javaops.basejava.model.ContactType.*;
import static ru.javaops.basejava.model.SectionType.*;

public abstract class AbstractStorageTest {
    //    protected static final String STORAGE_DIR = "storage";
    static final String STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Name1");
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Name2");
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Name3");
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Name4");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertSize(3);
//        Assert.assertEquals(Stream.of(RESUME_1, RESUME_2, RESUME_3)
//                .sorted()
//                .collect(Collectors.toList()), list);
//        List<Resume> listTest = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> listTest = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        Collections.sort(listTest);
        Assert.assertEquals(listTest, list);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        assertSize(2);
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3, "New Name");

        newResume.setContact(MOBILE, "1234567");
        newResume.setContact(SKYPE, "skype");
        newResume.setContact(MAIL, "mail");
        newResume.setContact(LINKEDIN, "linkedin");
        newResume.setContact(GITHUB, "github");
        newResume.setContact(STACKOVERFLOW, "stackoverflow");
        newResume.setContact(HOME_PAGE, "www");

        //OBJECTIVE
        newResume.setSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям."));
        //PERSONAL
        newResume.setSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность."));
        //ACHIEVEMENT
        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников. ");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера. ");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django). ");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа. ");
        newResume.setSection(ACHIEVEMENT, new ListSection(achievement));
        //QUALIFICATIONS
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce ");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, ");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB ");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, ");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts, ");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ");
        qualifications.add("Python: Django. ");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, ");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer. ");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов ");
        qualifications.add("проектрирования, архитектурных шаблонов, UML, функционального ");
        qualifications.add("программирования ");
        qualifications.add("Родной русский, английский \"upper intermediate\" ");
        newResume.setSection(QUALIFICATIONS, new ListSection(qualifications));

        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}
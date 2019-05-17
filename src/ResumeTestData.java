import model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static model.ContactType.*;
import static model.SectionType.*;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume resumeTest = new Resume("Григорий Кислин");

/**
 * initialization of the Contact section of the object ResumeTest
 */
//        resumeTest.setContact(PHONE, "+7(921) 855-0482");
        resumeTest.setContact(MOBILE, "+7(921) 855-0482");
//        resumeTest.setContact(HOME_PHONE, "+7(921) 855-0482");
        resumeTest.setContact(SKYPE, "grigory.kislin");
        resumeTest.setContact(MAIL, "gkislin@yandex.ru");
        resumeTest.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resumeTest.setContact(GITHUB, "https://github.com/gkislin");
        resumeTest.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resumeTest.setContact(HOME_PAGE, "http://gkislin.ru/");

        System.out.println(resumeTest.getFullName() + "\n");

        for (ContactType type : ContactType.values()) {
            if (resumeTest.getContact(type) != null) {
                System.out.println(type.getTitle() + ": " + resumeTest.getContact(type));
            }
        }

/**
 * initialization of the Section section of the object ResumeTest
 */
//OBJECTIVE
        resumeTest.setSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
//PERSONAL
        resumeTest.setSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
//ACHIEVEMENT
        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resumeTest.setSection(ACHIEVEMENT, new ListSection(achievement));
//QUALIFICATIONS
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов");
        qualifications.add("проектрирования, архитектурных шаблонов, UML, функционального");
        qualifications.add("программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        resumeTest.setSection(QUALIFICATIONS, new ListSection(qualifications));
//EXPERIENCE
        List<Organization> experience = new ArrayList<>();
        experience.add(new Organization("Java Online Projects", "http://javaops.ru/", YearMonth.of(2013, 10), YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experience.add(new Organization("Wrike", "https://www.wrike.com/", YearMonth.of(2014, 10), YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experience.add(new Organization("RIT Center", "", YearMonth.of(2012, 4), YearMonth.of(2014, 10), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        experience.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", YearMonth.of(2010, 12), YearMonth.of(2012, 4), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experience.add(new Organization("Yota", "https://www.yota.ru/", YearMonth.of(2008, 6), YearMonth.of(2010, 12), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experience.add(new Organization("Enkata", "http://enkata.com/", YearMonth.of(2007, 3), YearMonth.of(2008, 6), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        experience.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", YearMonth.of(2005, 1), YearMonth.of(2007, 2), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        experience.add(new Organization("Alcatel", "http://www.alcatel.ru/", YearMonth.of(1997, 9), YearMonth.of(2005, 1), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        resumeTest.setSection(EXPERIENCE, new OrganizationSection(experience));
//EDUCATION
        List<Organization> education = new ArrayList<>();
        education.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", YearMonth.of(2013, 3), YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky", ""));
        education.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", YearMonth.of(2011, 3), YearMonth.of(2011, 4), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""));
        education.add(new Organization("Siemens AG", "http://www.siemens.ru/", YearMonth.of(2005, 1), YearMonth.of(2005, 4), "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        education.add(new Organization("Alcatel", "http://www.alcatel.ru/", YearMonth.of(1997, 9), YearMonth.of(1998, 3), "6 месяцев обучения цифровым телефонным сетям (Москва)", ""));
        education.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", ""));
        education.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", ""));
        education.add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", YearMonth.of(1984, 9), YearMonth.of(1987, 6), "Закончил с отличием", ""));
        resumeTest.setSection(EDUCATION, new OrganizationSection(education));


        for (SectionType type : SectionType.values()) {
            if (resumeTest.getSection(type) != null) {
                System.out.println("\n" + type.getTitle() + "\n" + resumeTest.getSection(type));
            }
        }
    }
}

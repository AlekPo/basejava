import model.*;

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
        ListSection achievement = new ListSection();
        achievement.addValue("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.addValue("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.addValue("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.addValue("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.addValue("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.addValue("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resumeTest.setSection(ACHIEVEMENT, achievement);
//QUALIFICATIONS
        ListSection qualifications = new ListSection();
        qualifications.addValue("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addValue("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addValue("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.addValue("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.addValue("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.addValue("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.addValue("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.addValue("Python: Django.");
        qualifications.addValue("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.addValue("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.addValue("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.addValue("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.addValue("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.addValue("Отличное знание и опыт применения концепций ООП, SOA, шаблонов");
        qualifications.addValue("проектрирования, архитектурных шаблонов, UML, функционального");
        qualifications.addValue("программирования");
        qualifications.addValue("Родной русский, английский \"upper intermediate\"");
        resumeTest.setSection(QUALIFICATIONS, qualifications);
//EXPERIENCE
        OrganizationSection experience = new OrganizationSection();
        experience.addValue("Java Online Projects", "http://javaops.ru/", "10/2013 - Сейчас", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        experience.addValue("Wrike", "https://www.wrike.com/", "10/2014 - 01/2016", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experience.addValue("RIT Center", "", "04/2012 - 10/2014", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        experience.addValue("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", "12/2010 - 04/2012", "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        experience.addValue("Yota", "https://www.yota.ru/", "06/2008 - 12/2010", "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        experience.addValue("Enkata", "http://enkata.com/", "03/2007 - 06/2008", "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        experience.addValue("Siemens AG", "https://www.siemens.com/ru/ru/home.html", "01/2005 - 02/2007", "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        experience.addValue("Alcatel", "http://www.alcatel.ru/", "09/1997 - 01/2005", "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        resumeTest.setSection(EXPERIENCE, experience);
//EDUCATION
        OrganizationSection education = new OrganizationSection();
        education.addValue("Coursera", "https://www.coursera.org/course/progfun", "03/2013 - 05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        education.addValue("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "03/2011 - 04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", "");
        education.addValue("Siemens AG", "http://www.siemens.ru/", "01/2005 - 04/2005", "3 месяца обучения мобильным IN сетям (Берлин)", "");
        education.addValue("Alcatel", "http://www.alcatel.ru/", "09/1997 - 03/1998", "6 месяцев обучения цифровым телефонным сетям (Москва)", "");
        education.addValue("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", "09/1993 - 07/1996", "Аспирантура (программист С, С++)", "");
        education.addValue("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", "09/1987 - 07/1993", "Инженер (программист Fortran, C)", "");
        education.addValue("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/", "09/1984 - 06/1987", "Закончил с отличием", "");
        resumeTest.setSection(EDUCATION, education);

        for (SectionType type : SectionType.values()) {
            if (resumeTest.getSection(type) != null) {
                System.out.println("\n" + type.getTitle() + "\n" + resumeTest.getSection(type));
            }
        }

    }
}

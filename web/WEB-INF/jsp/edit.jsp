<%@ page import="ru.javaops.basejava.model.ContactType" %>
<%@ page import="ru.javaops.basejava.model.ListSection" %>
<%@ page import="ru.javaops.basejava.model.OrganizationSection" %>
<%@ page import="ru.javaops.basejava.model.TextSection" %>
<%@ page import="ru.javaops.basejava.util.HtmlUtil" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt target="HEADING">Имя:</dt>
            <dd><label>
                <input type="text" name="fullName" size=55 value="${resume.fullName}">
            </label></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt target=${type.name()}>${type.title}</dt>
                <dd><label>
                    <input type="text" name="${type.name()}" size=55 value="${resume.getContact(type)}">
                </label></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="type" items="<%=resume.getSections().keySet()%>">
            <c:set var="abstractSection" value="${resume.getSection(type)}"/>
            <jsp:useBean id="abstractSection" type="ru.javaops.basejava.model.AbstractSection"/>
            <dl>
                <dt target="HEADING">${type.title}</dt>
                <br>
                <c:choose>
                    <c:when test="${type.name().equals('OBJECTIVE') or type.name().equals('PERSONAL')}">
                        <dd><label>
                            <input type="text" name="${type.name()}" size=122
                                   value="<%=((TextSection) abstractSection).getContent()%>">
                        </label></dd>
                    </c:when>
                    <c:when test="${type.name().equals('ACHIEVEMENT') or type.name().equals('QUALIFICATIONS')}">
                        <dd><label>
<textarea rows="10" cols="124"
          name="${type.name()}"><%=String.join("\n", ((ListSection) abstractSection).getItems())%></textarea>
                        </label>
                        </dd>
                    </c:when>
                    <c:when test="${type.name().equals('EXPERIENCE') or type.name().equals('EDUCATION')}">
                        <c:forEach var="organization"
                                   items="<%=((OrganizationSection) abstractSection).getOrganizations()%>"
                                   varStatus="counter">
                            Наименование организации:
                            <dd><label>
                                <input type="text" name="${type.name()}" size=122 value="${organization.homePage.name}">
                            </label>
                            </dd>
                            Адрес сайта:
                            <dd><label>
                                <input type="text" name="${type.name()}url" size=122
                                       value="${organization.homePage.url}">
                            </label></dd>
                            <br>
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="ru.javaops.basejava.model.Position"/>
                                <dt class="position">Начальная дата ("MM/yyyy"):</dt>
                                <dd class="position"><label>
                                    <input type="text" name="${type.name()}${counter.index}startDate"
                                           size=118
                                           value="${position.dateStart.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
                                </label>
                                </dd>
                                <dt class="position">Конечная дата ("MM/yyyy"):</dt>
                                <dd class="position"><label>
                                    <input type="text" name="${type.name()}${counter.index}endDate"
                                           size=118
                                           value="${position.dateEnd.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
                                </label>
                                </dd>
                                <dt class="position">Должность\Специальность:</dt>
                                <dd class="position"><label>
                                    <input type="text" name="${type.name()}${counter.index}title"
                                           size=118
                                           value="${HtmlUtil.replacingQuotes(position.title)}">
                                </label></dd>
                                <dt class="position">Описание обязанностей:</dt>
                                <dd class="position"><label>
                                    <input type="text" name="${type.name()}${counter.index}description"
                                           size=118
                                           value="${position.description}">
                                </label></dd>
                                <hr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <%-- Statements which gets executed when all <c:when> tests are false.  --%>
                    </c:otherwise>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
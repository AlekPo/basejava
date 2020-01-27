<%@ page import="ru.javaops.basejava.model.*" %>
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
            <h3>
                <dt>Имя:</dt>
                <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
            </h3>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <%--<input type="text" name="section" size=30 value="1"><br/>--%>
        <%--<input type="text" name="section" size=30 value="2"><br/>--%>
        <%--<input type="text" name="section" size=30 value="3"><br/>--%>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="abstractSection" value="${resume.getSection(type)}"/>
            <jsp:useBean id="abstractSection" type="ru.javaops.basejava.model.AbstractSection"/>
            <dl>
                <h3>
                    <dt>${type.title}</dt>
                </h3>
                <c:choose>
                    <c:when test="${type.name().equals('OBJECTIVE') or type.name().equals('PERSONAL')}">
                        <dd><input type="text" name="${type.name()}" size=97
                                   value="<%=((TextSection) abstractSection).getContent()%>"></dd>
                    </c:when>
                    <c:when test="${type.name().equals('ACHIEVEMENT') or type.name().equals('QUALIFICATIONS')}">
                        <dd><textarea rows="10" cols="99"
                                      name="${type.name()}"><%=String.join("\n", ((ListSection) abstractSection).getItems())%></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${type.name().equals('EXPERIENCE') or type.name().equals('EDUCATION')}">
                        <c:forEach var="organization"
                                   items="<%=((OrganizationSection) abstractSection).getOrganizations()%>"
                                   varStatus="loop">
                            <br><br>Наименование организации:
                            <dd><input type="text" name="${type.name()}" size=122 value="${organization.homePage.name}">
                            </dd>
                            <br><br>Адрес сайта:
                            <dd><input type="text" name="${type.name()}_http" size=122
                                       value="${organization.homePage.url}"></dd>
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="ru.javaops.basejava.model.Position"/>
                                <br><br>Начальная дата ("MM/yyyy"):
                                <dd><input type="text" name="${type.name()}_${loop.count}_positionStartDate" size=122
                                           value="${position.dateStart.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
                                </dd>
                                <br><br>Конечная дата ("MM/yyyy"):
                                <dd><input type="text" name="${type.name()}_${loop.count}_positionEndDate" size=122
                                           value="${position.dateEnd.format(DateTimeFormatter.ofPattern("MM/yyyy"))}">
                                </dd>
                                <br><br>Должность\Специальность:
                                <dd><input type="text" name="${type.name()}_${loop.count}_positionName" size=122
                                           value="${position.title}"></dd>
                                <br><br>Описание обязанностей:
                                <dd><input type="text" name="${type.name()}_${loop.count}_positionDescription" size=122
                                           value="${position.description}"></dd>
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
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
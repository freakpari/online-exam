<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>لیست دانشجوهای استاد</h2>

<c:forEach var="entry" items="${groupedStudents}">
    <c:set var="courseInstanceId" value="${entry.key}" />
    <c:set var="students" value="${entry.value}" />

    <h3>دوره شماره ${courseInstanceId} - ${students[0].courseName} (${students[0].schedule})</h3>

    <table border="1" cellpadding="5">
        <tr>
            <th>نام دانشجو</th>
            <th>تلفن</th>
            <th>تاریخ ثبت‌نام</th>

        </tr>

        <c:forEach var="s" items="${students}">
            <tr>
                <td>${s.studentName}</td>
                <td>${s.studentPhone}</td>
                <td>${s.enrollmentDate}</td>
            </tr>
        </c:forEach>
    </table>
    <br/>
</c:forEach>

</body>
</html>


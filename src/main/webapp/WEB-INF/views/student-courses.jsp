<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Courses</title>
    <meta charset="UTF-8">
</head>
<body>
<h2>لیست دوره‌های دانشجو</h2>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>شماره</th>
        <th>نام دوره</th>
        <th>مدرس</th>
        <th>تاریخ شروع</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ci" items="${courses}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${ci.course.courseName}</td>
            <td>${ci.teacher.person.nameFamily}</td>
            <td>${ci.startDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

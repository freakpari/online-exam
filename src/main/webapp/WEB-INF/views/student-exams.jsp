<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<body>
<h2>آزمون‌های فعال</h2>

<c:if test="${empty exams}">
    <p>در حال حاضر آزمونی فعال نیست.</p>
</c:if>

<c:forEach var="exams" items="${exams}">
    <div style="border:1px solid black; padding:10px; margin:5px;">
        <h3>${exams.title}</h3>
        <p>${exams.description}</p>
        <p>تاریخ: ${exams.examDate}</p>
        <p>ساعت: ${exams.startTime} تا ${exams.endTime}</p>
        <a href="/exam/start/${exams.id}">شروع آزمون</a>
    </div>
</c:forEach>


</body>
</html>

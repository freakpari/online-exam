<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<body>
<h2>آزمون‌های فعال</h2>

<c:if test="${empty exams}">
    <p>در حال حاضر آزمونی فعال نیست.</p>
</c:if>

<c:forEach var="exam" items="${exams}">
    <div style="border:1px solid black; padding:10px; margin:5px;">
        <h3>${exam.title}</h3>
        <p>${exam.description}</p>
        <p>تاریخ: ${exam.examDate}</p>
        <p>ساعت: ${exam.startTime} تا ${exam.endTime}</p>
        <a href="/student/exams/${exam.id}/questions">شروع آزمون</a>
    </div>
</c:forEach>


</body>
</html>

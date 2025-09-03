<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Exam Questions</title>
</head>
<body>
<h2>لیست سوال‌ها</h2>

<c:if test="${empty questions}">
    <p>هیچ سوالی برای این آزمون یافت نشد.</p>
</c:if>

<c:forEach var="q" items="${questions}">
    <div style="margin-bottom:20px; border:1px solid #ccc; padding:10px;">
        <p><strong>سوال:</strong> ${q.questionText}</p>

        <c:choose>

            <c:when test="${q.type eq 'multipleChoice'}">
                <ul>
                    <c:forEach var="option" items="${q.options}" varStatus="loop">
                        <li>
                            <c:if test="${loop.index + 1 == q.correctOptionIndex}">
                                <b>${loop.index + 1}. ${option} ✅</b>
                            </c:if>
                            <c:if test="${loop.index + 1 != q.correctOptionIndex}">
                                ${loop.index + 1}. ${option}
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>


            <c:when test="${q.type eq 'descriptive'}">
                <p><i>این سوال تشریحی است.</i></p>
            </c:when>
        </c:choose>
    </div>
</c:forEach>

</body>
</html>

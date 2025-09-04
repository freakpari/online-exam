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


<form action="submitAnswers" method="post">

    <c:forEach var="q" items="${questions}" varStatus="qStatus">
        <div style="margin-bottom:20px; border:1px solid #ccc; padding:10px;">
            <p><strong>سوال ${qStatus.index + 1}:</strong> ${q.questionText}</p>

            <c:choose>

                <c:when test="${q.type eq 'multipleChoice'}">
                    <ul>
                        <c:forEach var="option" items="${q.options}" varStatus="loop">
                            <li>

                                <input type="radio"
                                       <%--name="answer_${q.id}"--%>
                                       value="${loop.index + 1}" />
                                    ${loop.index + 1}. ${option}
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>


                <c:when test="${q.type eq 'descriptive'}">
                    <textarea <%--name="answer_${q.id}"--%> rows="3" cols="50"
                              placeholder="پاسخ خود را بنویسید..."></textarea>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>


    <button type="submit">ارسال پاسخ‌ها</button>
</form>

</body>
</html>

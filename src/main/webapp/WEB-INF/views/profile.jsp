<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Welcome, ${username}</h2>

<c:if test="${isTeacher}">
    <h3>Teacher Menu</h3>
    <ul>
        <li><a href="#">Manage Courses</a></li>
        <li><a href="/teacher/${teacherId}/students">View Students</a></li>
        <P>${teacherId}</P>
    </ul>
</c:if>

<c:if test="${isStudent}">
    <h3>Student Menu</h3>
    <ul>
        <li><a href="/student/${studentId}/courses">View Courses</a></li>
        <p>StudentId: ${studentId}</p>

        <li><a href="#">Submit Assignment</a></li>
        <c:forEach var="course" items="${courses}">
            <li>
                <a href="/student/exams/${course.courseInstanceId}">
                    View Exams for ${course.courseName}
                </a>
            </li>
        </c:forEach>
        <c:forEach var="course" items="${courses}">
            <p>Loop working!</p>
        </c:forEach>
    </ul>
</c:if>

<script>
    var isTeacher = ${isTeacher};
    var isStudent = ${isStudent};
    console.log(isTeacher);
    console.log(isStudent);
</script>

</body>
</html>
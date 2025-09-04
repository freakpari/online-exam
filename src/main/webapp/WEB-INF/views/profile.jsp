<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Welcome, ${username}</h2>

<c:if test="${isTeacher}">
    <h3>Teacher Menu</h3>
    <ul>
        <li><a href="#">Manage Courses</a></li>
        <li><a href="/teacher/${teacherId}/students">View Students</a></li>
        <li><a href="#"></a></li>
    </ul>
</c:if>

<c:if test="${isStudent}">
    <h3>Student Menu</h3>
    <ul>
        <li><a href="/courses/student/${studentId}">View Courses</a></li>
        <li><a href="#">Submit Assignment</a></li>
        <c:forEach var="course" items="${courses}">
            <li>
                <a href="/student/exams/${course.courseInstanceId}">
                    View Exams for ${course.courseName}
                </a>
            </li>
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
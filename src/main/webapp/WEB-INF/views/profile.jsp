<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Welcome, ${username}</h2>

<c:if test="${isTeacher}">
    <h3>Teacher Menu</h3>
    <ul>
        <li><a href="#">Manage Courses</a></li>
        <li><a href="#">View Students</a></li>
    </ul>
</c:if>

<c:if test="${isStudent}">
    <h3>Student Menu</h3>
    <ul>
        <li><a href="#">View Courses</a></li>
        <li><a href="#">Submit Assignment</a></li>
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

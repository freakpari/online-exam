<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>

<div style="max-width:900px;margin:2rem auto;font-family:sans-serif;">
    <h2>Student Dashboard</h2>
    <p>Welcome, <strong>${username}</strong> (role: ${role}).</p>

    <h3>My Exams</h3>
    <ul>
        <li><a href="#">Upcoming Exams</a></li>
        <li><a href="#">Take Exam</a></li>
        <li><a href="#">Results</a></li>
    </ul>
</div>

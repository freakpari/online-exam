<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div style="padding:1rem; background:#f7f7f7; display:flex; gap:1rem; align-items:center;">
    <div><a href="${pageContext.request.contextPath}/dashboard">Exam Manager</a></div>
    <div style="margin-left:auto;">
        <sec:authorize access="isAuthenticated()">
            Hello, <strong><sec:authentication property="name"/></strong>
            <span style="opacity:.7">(
        <sec:authorize access="hasRole('TEACHER')">TEACHER</sec:authorize>
        <sec:authorize access="hasRole('STUDENT')">STUDENT</sec:authorize>
      )</span>
            |
            <form action="${pageContext.request.contextPath}/perform_logout" method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit" style="background:none; border:none; color:#06c; cursor:pointer;">Logout</button>
            </form>
        </sec:authorize>
    </div>
</div>

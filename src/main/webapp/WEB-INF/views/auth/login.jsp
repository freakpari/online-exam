<%@ page session="false" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: sans-serif; max-width: 420px; margin: 4rem auto; }
        .box { border: 1px solid #ddd; padding: 2rem; border-radius: 8px; }
        .msg { margin-bottom: 1rem; color: #b00; }
    </style>
</head>
<body>
<div class="box">
    <h2>Sign in</h2>

    <div class="msg" style="${param.error == null ? 'display:none;' : ''}">
        Invalid username or password.
    </div>
    <div class="msg" style="color:green;${param.logout == null ? 'display:none;' : ''}">
        You have been logged out.
    </div>

    <form action="${pageContext.request.contextPath}/perform_login" method="post">
        <label>Username
            <input type="text" name="username" required />
        </label>
        <label>Password
            <input type="password" name="password" required />
        </label>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>

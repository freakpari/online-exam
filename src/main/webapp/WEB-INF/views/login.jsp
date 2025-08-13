<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<body>
<h2>Login</h2>
<form action="/login" method="post">
    Username: <input type="text" name="username" /><br/>
    Password: <input type="password" name="password" /><br/>
    <button type="submit">Login</button>
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.exam.demo.model.User" %>
<%
    List<User> userList = (List<User>) request.getAttribute("register");
%>
<html>
<head>
    <title>لیست کاربران</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label>Username:</label>
    <input type="text" name="username" required/><br/><br/>

    <label>Password:</label>
    <input type="password" name="password" required/><br/><br>

    <label>Full Name:</label>
    <input type="text" name="nameFamily" required/><br/><br/>

    <label>Phone:</label>
    <input type="text" name="phone" required/><br/><br/>

    <label>National Code:</label>
    <input type="text" name="nationalCode" required/><br/><br/>
    <button type="submit">Register</button>
</form>

<hr/>

<h1>لیست کاربران</h1>

<table border="1" cellpadding="5" cellspacing="5" id="userTable">
    <tr>
        <th>id</th>
        <th>username</th>


    </tr>
    <%
        if (userList != null) {
            for (User user : userList) {
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getUsername() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>

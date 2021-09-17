<%--
  Created by IntelliJ IDEA.
  User: lily
  Date: 9/15/21
  Time: 7:59 PM
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> System Login</title>
</head>
<body>
    <form method="post" id="form" action="<%=path%>/loginServlet">
        <h1> CFM Employee Login Page</h1>
        <input type="text" id="username" name="username" value="${username}" placeholder="please input username:" />
        <input type="password" id="password" name="password" placeholder="please input password:" />
        <button type="button" name="loginButton" onclick="loginVerify();">Login</button>
            ${error}
    </form>
</body>
</html>

<script>
    function loginVerify() {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        if (username == '') {
            alert('Username cannot be null, input again!');
            return;
        }
        if (password == '') {
            alert('Password cannot be null, input again!');
            return;
        }
        // servlet, transfer data to
        document.getElementById("form").submit();
    }
</script>

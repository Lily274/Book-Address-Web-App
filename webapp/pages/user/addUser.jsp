<%--
  Created by IntelliJ IDEA.
  User: lily
  Date: 9/16/21
  Time: 3:29 PM
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<form id="form" method="post" action="<%=path%>/userServlet">
    <input type="hidden" value="1" name="type" />
    <input type="hidden" value="${flag}" name="flag" id="flag" />
    User ID: <input type="text" id="userId" name="userId" value="${user.userId}" /> <br>
    Password: <input type="password" id="userPassword" name="userPassword" value="${user.userPassword}" /> <br>
    User Name: <input type="text" id="userName" name="userName" value="${user.userName}"/> <br>
    Gender: <input type="radio" id="gender1" name="gender" value="1" <c:if test="${user.gender == 1}">checked</c:if>/> M <input type="radio" id="gender2" name="gender" value="2" <c:if test="${user.gender == 2}">checked</c:if>/> F <br>
    Role: <select id="roleId" name="roleId">
        <option value="">Please select</option>
        <option value="1" <c:if test="${user.roleId == 1}">selected</c:if>>Administrator</option>
        <option value="2" <c:if test="${user.roleId == 2}">selected</c:if>>Normal User</option>
    </select><br>
    <button type="button" onclick="add();" >Save</button>
    <button type="button" onclick="cancel();" >Cancel</button>
</form>
</body>
</html>

<script>
    function add() {
        var flag = document.getElementById("flag").value;
        var userId = document.getElementById("userId").value;
        var userPassword = document.getElementById("userPassword").value;
        var roleId = document.getElementById("roleId").value;
        if (userId == null || userId == '') {
            alert("Please input a user name!");
            return;
        }
        if (userPassword == null || userPassword == '') {
            alert("Please input a password!");
            return;
        }
        if (roleId == null || roleId == '') {
            alert("Please select a role");
            return;
        }
        var ret ;
        if (flag != "1") {
            // 判断用户名是否重复
            // ajax
            var url = '<%=path%>/userServlet', params='type=4&userId='+userId;
            ret = getDataByAjax(url, params);
        } else {
            ret = "1";
        }
        if (ret == "1") {
            // submit
            document.getElementById("form").submit();
        } else {
            // return
            alert("Your user ID has been used, please input again!");
            return;
        }
    }
    function cancel() {
        window.location.href = "<%=path%>/userServlet?type=0";  // a get() request.
    }
    // params: "a=1&b=2&c=3"
    function getDataByAjax(url, params) {
        var ajaxObj = null;
        if (window.ActiveXObject) {   // low version IE.
            ajaxObj = new ActiveXObject("Microsoft.XMLHTTP");
        } else {    // other browsers.
            ajaxObj = new XMLHttpRequest();
        }
        ajaxObj.open('POST', url, false);   // false means synchronization.
        ajaxObj.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        ajaxObj.send(params);
        return ajaxObj.responseText;
    }
</script>

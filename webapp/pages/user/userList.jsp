<%--
  Created by IntelliJ IDEA.
  User: lily
  Date: 9/16/21
  Time: 9:47 AM
  To change this template use File | Settings | File Templates.
--%>
<% String path = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address_Book</title>
</head>
<body>
    <h1>Welcome to CFM Employee Address Book Management Page!</h1>
    <button type="button" onclick="addUser();">Add</button>
    <form method="post" id="queryForm" action="<%=path%>/userServlet">
        <input type="text" name="uId" value="${user.userId}" placeholder="User ID"/>
        <input type="text" name="uName" value="${user.userName}" placeholder="User Name"/>
        <select name="gdr">
            <option value="0">Gender</option>
            <option value="1" <c:if test="${user.gender == 1}">selected</c:if>>M</option>
            <option value="2" <c:if test="${user.gender == 2}">selected</c:if>>F</option>
        </select>
        <input type="hidden" name="type" value="0" />
        <input type="hidden" name="pageNum" value="${pageNum}" />
        <input type="hidden" id = "changeNum" name="changeNum" value="" />
        <button onclick="changePage(0);">Search</button>
    </form>
    <table border="1">
        <tr>
            <td>Number</td>
            <td>User ID</td>
            <td>Name</td>
            <td>Gender</td>
            <td>Role Name</td>
            <td>Operation</td>
        </tr>
        <c:forEach items="${userList}" var="l" varStatus="vs">
            <tr>
                <td>${vs.index + 1}</td>
                <td>${l.userId}</td>
                <td>${l.userName}</td>
                <td>${l.gender == 1 ? 'M' : 'F'}</td>
                <td>${l.roleName}</td>
                <td>
                    <button type="button" onclick="editUser('${l.userId}');">Update</button>
                    <button type="button" onclick="deleteUser('${l.userId}');">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <button onclick="changePage(-1);">last page</button>
    <button onclick="changePage(1);">next page</button>
    current ${pageNum} page, total ${totalPageNum} pages, total ${totalNum} records.
</body>
</html>

<script>
    function addUser() {
        window.location.href = "<%=path%>/pages/user/addUser.jsp";
    }
    function editUser(userId) {
        window.location.href = "<%=path%>/userServlet?type=2&userId="+userId;
    }
    function deleteUser(userId) {
        if (window.confirm("Are you sure to delete this record?")) {
            window.location.href = "<%=path%>/userServlet?type=3&userId="+userId;
        }
    }
    function changePage(num) {
        document.getElementById("changeNum").value = num;
        document.getElementById("queryForm").submit();
    }
</script>

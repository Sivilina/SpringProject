<%@ taglib uri="http://www.springframework.org/tags/form" prefix="twitter"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <%
        List<User> users = (List<User>)request.getAttribute("userList");

        if(users!=null){
            for(User u: users){
                out.print("<tr>");
                out.print("<td>"+u.getName()+"</td><td>"+u.getSurname()+"</td><td>"+u.getLogin()+"</td>");
                out.print("<td>"+u.getRole().getName()+"</td>");
                out.print("</tr>");
            }
        }

    %>
</table>

<form:form id = "addRoleForm" action="/addrole" method="post" modelAttribute="userAttr">
    <form:input path="role.name"/>
    <input type="submit" value="ADD ROLE">
</form:form>

<form:form id = "addUserForm" action="/adduser" method="post" modelAttribute="userAttr">
    <form:input path="name" />
    <form:input path="surname" />
    <form:input path="login" />
    <form:input path="password" />
    <form:select path="role.id">
        <form:options items = "${roleList}" itemValue="id" itemLabel="name"/>
    </form:select>
    <input type="submit" value="ADD USER">
</form:form>

</body>
</html>

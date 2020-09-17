<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>All Users</title>
</head>
<body>

<h2>All Users</h2>

<table>
  <tbody>
  <c:forEach items="${users}" var="user">
    <tr>
      <td>${user.id}</td>
      <td>${user.name}</td>  </tr>
  </c:forEach>
  </tbody>
</table>

<a href="/ProselyteSpringMVC_war/user">New User</a>

</body>
</html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
  <title>user</title>
</head>
<body>

<h2>Add user</h2>
<form:form method="post" action="addUser">
  <table>
    <tr>
      <td><form:label path="id">Id</form:label></td>
      <td><form:input path="id" /></td>
    </tr>
    <tr>
      <td><form:label path="name">Name</form:label></td>
      <td><form:input path="name" /></td>
    </tr>
    <tr>
      <td colspan="2">
        <input type="submit" value="Submit"/>
      </td>
    </tr>
  </table>
</form:form>
</body>
</html>
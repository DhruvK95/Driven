<%--
  Created by IntelliJ IDEA.
  User: Dhruv
  Date: 18/10/2016
  Time: 6:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver Home</title>
</head>
<body>
<%@ include file="navBar.html" %>
<h1 align="center">Hi ${requestScope.fname} ${requestScope.lname}, Welcome to Driven</h1>
<hr>
<b>Please choose an action to proceed with for your RegistrationNumber:
    ${requestScope.RegistrationNumber}</b>
<form class="col s12" action="home" method="post">
    <button class="btn waves-effect waves-light" type="submit" name="action">Cancel
        <i class="material-icons right">error</i>
    </button>
</form>
<form class="col s12" action="home" method="post">
    <button class="btn waves-effect waves-light" type="submit" name="action">Process
        <i class="material-icons right">input</i>
    </button>
</form>
</body>
</html>

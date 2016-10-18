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
<div class="row">
    <div class="col s12"><p></p></div>
    <div class="col s12 m4 l2"><p></p></div>
    <div class="col s12 m4 l8"><p></p>
        <h1 align="center">Hi ${requestScope.fname} ${requestScope.lname}, Welcome to Driven</h1>
        <hr>
        <b>Please choose an action to proceed with for your RegistrationNumber:
            ${requestScope.RegistrationNumber}</b>
        <form class="col s12" action="workflowController" method="post">
            <input type="hidden" name="action" value="vaaaaaaa" />

            <button class="btn waves-effect waves-light" type="submit" name="action">Cancel
                <i class="material-icons right">error</i>
            </button>
        </form>

        <br>


        <table class="striped centered">
            <thead>
            <tr>
                <th data-field="id">Field</th>
                <th data-field="name">Value</th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>RegistrationID</td>
                <td>${requestScope.registration.getrID()}</td>
            </tr>
            <tr>
                <td>RegistrationNumber</td>
                <td>${requestScope.registration.getRegistrationNumber()}</td>
            </tr>
            <tr>
                <td>validTill</td>
                <td>${requestScope.registration.getValidTill().toString()}</td>
            </tr>
            <tr>
                <td>LastName</td>
                <td>${requestScope.registration.getDriver().getLastName()}</td>
            </tr>
            <tr>
                <td>FirstName</td>
                <td>${requestScope.registration.getDriver().getFirstName()}</td>
            </tr>
            <tr>
                <td>LicenseNumber</td>
                <td>${requestScope.registration.getDriver().getLicenseNumber()}</td>
            </tr>
            <tr>
                <td>Address</td>
                <td>${requestScope.registration.getDriver().getAddress()}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${requestScope.registration.getDriver().getEmail()}</td>
            </tr>
            </tbody>
        </table>


        <%--${requestScope.registration.getRegistrationNumber()}--%>

        <form class="col s12" action="workflowController" method="post">
        <div class="row">
                    <div class="input-field col s6">
                <input value="${requestScope.address}" id="address" name="address" type="text" class="validate">
                <label class="active" for="address">Address</label>
            </div><br>
        </div>
            <input type="hidden" name="action" value="process" />
            <br>
            <button class="btn waves-effect waves-light" type="submit" name="action">Process
                <i class="material-icons right">input</i>
            </button>
        </form>
    </div>
    <div class="col s12 m4 l2"><p></p></div>
</body>
</html>

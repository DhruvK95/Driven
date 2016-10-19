<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
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
                                <td>${registration.getrID()}</td>
                            </tr>
                            <tr>
                                <td>RegistrationNumber</td>
                                <td>${registration.getRegistrationNumber()}</td>
                            </tr>
                            <tr>
                                <td>validTill</td>
                                <td>${registration.getValidTill().toString()}</td>
                            </tr>
                            <tr>
                                <td>LastName</td>
                                <td>${registration.getDriver().getLastName()}</td>
                            </tr>
                            <tr>
                                <td>FirstName</td>
                                <td>${registration.getDriver().getFirstName()}</td>
                            </tr>
                            <tr>
                                <td>LicenseNumber</td>
                                <td>${registration.getDriver().getLicenseNumber()}</td>
                            </tr>
                            <tr>
                                <td>Address</td>
                                <td>${registration.getDriver().getAddress()}</td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td>${registration.getDriver().getEmail()}</td>
                            </tr>
                        </tbody>
                    </table>

</body>
</html>
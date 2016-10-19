<%--
Created by IntelliJ IDEA.
User: Dhruv
Date: 18/10/2016
Time: 6:29 PM
To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
            <h1 align="center">Hi ${requestScope.registration.getDriver().getFirstName()} ${requestScope.registration.getDriver().getLastName()}, Welcome to Driven</h1>
            <hr>
                <h4 class"header" style="color: #ee6e73;font-weight: 300;"> Current Registration details: </h4>

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

                    <c:if test="${requestScope.notice.getStatus() != 'created'}">
                        <h2 class"header" align="center" style="color: #ee6e73;font-weight: 300;">
                            Notice status: ${requestScope.notice.getStatus()}</h2>
                    </c:if>

                    <c:choose>
                        <c:when test="${requestScope.notice.getStatus() == 'accepted'}">
                            <%-- Payment Form --%>
                            <h3>Fee:$ ${requestScope.payment.getAmount()}</h3><hr>
                            <form class="col s12" action="workflowController" method="post">
                                <p class="flow-text">Payment Details</p>
                                <div class="row">
                                    <div class="input-field col s6">
                                        <i class="material-icons prefix">credit_card</i>
                                        <input id="ccn" type="number" class="validate" name="ccn" required>
                                            <label for="ccn">Credit Card Number</label>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="input-field col s6">
                                            <i class="material-icons prefix">credit_card</i>
                                            <input id="cca" type="text" class="validate" name="cca"required>
                                                <label for="cca">Credit Card Name</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="input-field col s6">
                                                <i class="material-icons prefix">credit_card</i>
                                                <input id="ccv" type="number" class="validate" name="ccv" required>
                                                <label for="ccv">Credit Card CCV</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <input type="hidden" name="action" value="pay" />
                                            <button class="btn waves-effect waves-light" style="background-color: #417ab5" type="submit"
                                                name="action">Pay
                                                <i class="material-icons right">credit_card</i>
                                            </button>
                                        </div>
                                        </div>
                                        <input type="hidden" name="action" value="payment" />
                                    </form>
                                </c:when>

                                <c:when test="${requestScope.notice.getStatus() == 'under-review'}">
                                </c:when>
                                <c:when test="${not empty requestScope.reason}"> ${requestScope.reason}
                                </c:when>
                                <c:when test="${requestScope.notice.getStatus() == 'cancelled'}"> CANCEL BUTTON
                                </c:when>
                                <c:otherwise>
                                    <form class="col s12" action="workflowController" method="post">
                                        <p class="flow-text">Update Address</p>
                                        <div class="row">
                                            <div class="input-field col s6">
                                                <input value="${requestScope.registration.getDriver().getAddress()}" id="address" name="address" type="text" class="validate">
                                                    <label class="active" for="address">Address</label>
                                                </div>
                                            </div>
                                            <input type="hidden" name="action" value="update" />
                                            <button class="btn waves-effect waves-light" style="background-color: #417ab5" type="submit"
                                                name="action">Update
                                                <i class="material-icons right">present_to_all</i>
                                            </button>
                                    </form>
                                        <hr>
                                            <%-- Other Actions --%>
                                            <div class="row">
                                                <form class="col s4" action="workflowController" method="post">
                                                    <p class="flow-text">Other Actions</p>
                                                    <input type="hidden" name="action" value="process" />
                                                    <button class="btn waves-effect waves-light btn-large" type="submit"
                                                        name="action">Lodge process request
                                                        <i class="material-icons right">input</i>
                                                    </button>
                                                </form>

                                                <form class="col s3" action="workflowController" method="post">
                                                    <p class="flow-text">&nbsp;</p>
                                                    <input type="hidden" name="action" value="cancel" />
                                                    <button class="btn waves-effect waves-light btn-large" style="background-color: #b5665f" type="submit"
                                                        name="action">Cancel
                                                        <i class="material-icons right">error</i>
                                                    </button>
                                                </form>
                                            </div>



                                            ${requestScope.lodged}

                                        </div>
                                        <div class="col s12 m4 l2"><p></p></div>
                                    </body>
                                </html>
                            </c:otherwise>
                        </c:choose>

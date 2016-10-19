<%--
  Created by IntelliJ IDEA.
  User: Dhruv
  Date: 18/10/2016
  Time: 10:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="navBar.html" %>
<h1 align="center">Hi Officer, Welcome to Driven</h1>
 
			
		        <form class="col s12" action="officerhome" method="post">
	        	<input type="hidden" name="action" value="generate" />
	            <br>
	            
	            <button class="btn waves-effect waves-light" type="submit" name="action">generate Notices
	                <i class="material-icons right">input</i>
	            </button>
		        </form>
	        	</td>
	        
	







</body>
</html>

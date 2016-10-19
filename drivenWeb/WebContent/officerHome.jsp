<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="navBar.html" %>
 <div class="row">
            <div class="col s12"><p></p></div>
            <div class="col s12 m4 l2"><p></p></div>
            <div class="col s12 m4 l8"><p></p>
<h1 align="center">Hi Officer, Welcome to Driven</h1>
 
			
		        <form class="col s12" action="officerhome" method="post">
	        	<input type="hidden" name="action" value="getPending" />
	            <br>
	            
	            <button class="btn waves-effect waves-light" type="submit" name="action">get Pending
	                <i class="material-icons right">input</i>
	            </button>
		        </form>
	        	
	        	
		        <form class="col s12" action="officerhome" method="post">
	        	<input type="hidden" name="action" value="generateNotices" />
	            <br>
	            
	            <button class="btn waves-effect waves-light" type="submit" name="action">generate Notices
	                <i class="material-icons right">input</i>
	            </button>
		        </form>
		        
		        
	        	</td>
	        
	        
 </div>
                                        <div class="col s12 m4 l2"><p></p></div>
	







</body>
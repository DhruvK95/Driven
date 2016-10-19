<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="navBar.html" %>
 
		<table class="table">
		<thead>
			<tr>
				<th>
					[Details]
				</th>
				<th>
					[Address Check]
				</th>
			</tr>
		</thead>
		<tbody>
			
		
			<c:forEach items="${notices}" var="n">
							<td>
			
			<tr>	
		        <td>Link: <c:out value="${n.getRegistration().getrID()}"/>
		        
		        
		        <%-- 
		        Status: <c:out value="${n.RenewalNoticeResponse.renewalNotice.status}"/>  
		        nid: <c:out value="${n.RenewalNoticeResponse.renewalNotice.nid}"/> 
		        rid: <c:out value="${n.renewalNotice.rid}"/> --%> 
		        
		    
				</td>
				<td>
		
		        <form class="col s12" action="officerhome" method="post">
			        
		        	<input type="hidden" name="action" value="check" />
					 <input type="hidden" name="field" value="${n.getRegistration().getrID()}" /> 
		            <br>
		            
		            <button class="btn waves-effect waves-light" type="submit" name="action">check
		                <i class="material-icons right">input</i>
		            </button>
		        </form>
	        	</td>
	        
			</c:forEach>
				        </tr>	
					



		</tbody>
	</table>
	







</body>
</html>

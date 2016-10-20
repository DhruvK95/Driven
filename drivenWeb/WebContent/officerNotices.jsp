<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
</head>
<body>
<!--asdfasd  -->
<%@ include file="navBar.html" %>
 <div class="row">
            <div class="col s12"><p></p></div>
            <div class="col s12 m4 l2"><p></p></div>
            <div class="col s12 m4 l8"><p></p>
	<div class="chip">
			autocheck address allowed
    <i class="close material-icons">close</i>
  </div>		<table class="table striped">
  
		<thead>
			<tr>
				<th>
					Details
				</th>
				<th>
					Status
				</th>
				<th>
					Address Check
				</th>
			</tr>
		</thead>
		<tbody>
			
		
			<c:forEach items="${notices}" var="n">
			
			<tr>	
		        <td>Rid: <c:out value="${n.getRid()}"/> Nid: <c:out value="${n.getNid()}"/></td>
		        
		        <td><c:out value="${n.getStatus()}"/></td>
		        
		       
		        <%-- 
		        Status: <c:out value="${n.RenewalNoticeResponse.renewalNotice.status}"/>  
		        nid: <c:out value="${n.RenewalNoticeResponse.renewalNotice.nid}"/> 
		        rid: <c:out value="${n.renewalNotice.rid}"/> --%> 
		        
		    
				<td>
				 <form class="col s12" action="officerhome" method="post">
			        <input type="hidden" name="rid" value="${n.getRid()}" /> 
		        	<input type="hidden" name="action" value="getRegistrationsDetails" />
		            <br>
		            
		            <button class="btn waves-effect waves-light" type="submit" name="action">Details
		                <i class="material-icons right">input</i>
		            </button>
		        </form>
		        <form class="col s12" action="officerhome" method="post">
			        
		        	<input type="hidden" name="action" value="check" />
					 <input type="hidden" name="field" value="${n.getRid()}" /> 
					 <input type="hidden" name="nid" value="${n.getNid()}" /> 
					 
		            <br>
		            
		            <button class="btn waves-effect waves-light" type="submit" name="action">check
		                <i class="material-icons right">input</i>
		            </button>
		        </form>
	        	</td>
	        				        </tr>	
	        
			</c:forEach>
			
		</tbody>
		
	</table>
	<br><br>
	<div class="chip">
			driver autocheck not allowed
    <i class="close material-icons">close</i>
  </div>
	
	<table class="sortable striped">
		<thead>
			<tr>
				<th>
					Details
				</th>
				<th>
					Status
				</th>
				<th>
					Address Check
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${noticesNotAllowed}" var="n">
			
			<tr>	
		        <td>Rid: <c:out value="${n.getRid()}"/> Nid: <c:out value="${n.getNid()}"/></td>
		        
		        <td><c:out value="${n.getStatus()}"/></td>
		        
		       
		        <%-- 
		        Status: <c:out value="${n.RenewalNoticeResponse.renewalNotice.status}"/>  
		        nid: <c:out value="${n.RenewalNoticeResponse.renewalNotice.nid}"/> 
		        rid: <c:out value="${n.renewalNotice.rid}"/> --%> 
		        
		    
				<td>
				 <form class="col s12" action="officerhome" method="post">
			        <input type="hidden" name="rid" value="${n.getRid()}" /> 
		        	<input type="hidden" name="action" value="getRegistrationsDetails" />
		            <br>
		            
		            <button class="btn waves-effect waves-light" type="submit" name="action">Details
		                <i class="material-icons right">input</i>
		            </button>
		        </form>
		        
	        	</td>
	        				        </tr>	
	        
			</c:forEach>
					



		</tbody>
	</table>
	



 </div>
                                        <div class="col s12 m4 l2"><p></p></div>



</body>
</html>

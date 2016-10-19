</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="navBar.html" %>
	 <c:choose>
	  <c:when test="${exact}">
	<form class="col s12" action="officerhome" method="post">
		ExactMatch found
		        	<input type="hidden" name="action" value="createPayment" />
		            <br>
		             <p class="flow-text">Create Payment</p>
                                        <div class="row">
                                            <div class="input-field col s6">
                                                <input value="" id="amount" name="amount" type="number" class="validate">
                                                    <label class="active" for="amount">Amount for Payment</label>
                                                </div>
                                            </div>
                                            
                                                                                        
		            <button class="btn waves-effect waves-light" type="submit" name="action">Confirm
			            <i class="material-icons right">input</i>
		            </button>
			        </form>
			          </c:when>
	  <c:otherwise>
					<form class="col s12" action="officerhome" method="post">
					ExactMatch Not found
		        	<input type="hidden" name="action" value="rejectPayment" />
		        	
		            <br>
		             <p class="flow-text">Reject Payment</p>
                                        <div class="row">
                                            <div class="input-field col s6">
                                                <input value="" id="rejection" name="rejection" type="text" class="validate">
                                                    <label class="active" for="address">Reason for Rejections</label>
                                                </div>
                                            </div>
                                                                 
                                                                                        
		            <button class="btn waves-effect waves-light" type="submit" name="action">Confirm
			            <i class="material-icons right">input</i>
		            </button>
			        </form>
		         </c:otherwise>
	</c:choose>

</body>
</html>

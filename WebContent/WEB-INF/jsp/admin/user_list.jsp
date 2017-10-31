<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	
	<table>
		<tr>
			<th>ID</th>
			<th>Login</th>
			<th>Name</th>
			<th>Email</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.id}</td>
				<td>${user.login}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="accountList"/>
						<input type="hidden" name="userId" value="${user.id}"/>
						<button type = "submit">Accounts</button>
					</form>
					
					<form action="controller" method="post">
						<input type="hidden" name="command" value="paymentList"/>
						<input type="hidden" name="payed" value="2"/>
						<input type="hidden" name="userId" value="${user.id}"/>
						<button type = "submit">Payments</button>
					</form>
				</td>
				
				<td>
					<c:choose>
						<c:when test = "${user.userStatusId == 2}">
							<form id="lock_form" action="controller" method="post">
								<input type="hidden" name="command" value="setUserStatus"/>
								<input type="hidden" name="statusId" value="2"/>
								<input type="hidden" name="userId" value="${user.id}"/>
								<button type = "submit">Lock</button>
							</form>
						</c:when>
						
						<c:when test = "${user.userStatusId == 1}">
							<form id="unlock_form" action="controller" method="post">
								<input type="hidden" name="command" value="setUserStatus"/>
								<input type="hidden" name="statusId" value="3"/>
								<input type="hidden" name="accountId" value="${user.id}"/>
								<button type = "submit">Unlock</button>
							</form>
						</c:when>		
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
				integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
				crossorigin="anonymous">
</head>
<body>
	<div class = "container"> 
	<div class="col-lg-10 col-lg-offset-1">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class = "row">
	<div class = "well">
	<h3>
		<span class="glyphicon glyphicon-align-justify"></span>
		Payments
	</h3>
	<table class="table table-striped table-bordered">
		<thead>
		<tr>
			<th>Comment</th>
			<th>From</th>
			<th>to</th>
			<th>Sum</th>
			<th>Date</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${payments}" var="payment">
			<tr>
				<td>${payment.comment}</td>
				<td>${payment.accId}</td>
				<td>${payment.reciverId}</td>
				<td>${payment.sum}</td>
				<td>${payment.date}</td>
				<c:if test="${payment.payStatusId == 1}">
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="command" value="confirmPayment"/>
							<input type="hidden" name="paymentId" value="${payment.id}"/>
							<input type="hidden" name="recAccId" value="${payment.reciver}"/>
							<button type="submit">Submit</button>
						</form>
					</td>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	</div>
	</div>
</body>
</html>
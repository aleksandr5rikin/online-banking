<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Accounts</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
			integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
			crossorigin="anonymous">
</head>
<body>
	<div class = "container"> 
	<div class="col-lg-10 col-lg-offset-1">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class = "well">
	<c:if test="${role.name == 'client'}">
		<div class = "row">
		<div class="col-sm-8">
		<form id="account_form" action="controller" method="post">
			<div class="input-group">
				<span class="input-group-btn">
					<button class = "btn btn-info" type = "submit">Add account</button>
				</span>
				<input type="hidden" name="command" value="addAccount"/>
				<input class="form-control" type="text" name="name" required/>
			</div>
		</form>
		</div>
		</div>
	</c:if>
	
	<div class="row">
	<h3>
		<span class="glyphicon glyphicon-align-justify"></span>
		Accounts
	</h3>
	<table class="table table-striped table-bordered">
		<thead>
		<tr>
			<th>ID</th>
			<th>Status</th>
			<th>Name</th>
			<th>Balance</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${accounts}" var="account">
			<tr>
				<td>${account.id}</td>
				<td>${account.accStatusId}</td>
				<td>${account.name}</td>
				<td>${account.balance}</td>
				
				<td class="col-md-3">
					<div class="pull-left">
					<form id="edit_form" action="controller" method="post">
						<input type="hidden" name="command" value="getEdit"/>
						<input type="hidden" name="accountId" value="${account.id}"/>
						<button class = "btn btn-info" type = "submit">Edit</button>
					</form>
					</div>
					<div class="pull-right">
						<form id="payments_form" action="controller" method="post">
						<input type="hidden" name="command" value="paymentList"/>
						<input type="hidden" name="accountId" value="${account.id}"/>
						<button class = "btn btn-info" type = "submit">Payments</button>
					</form>
					</div>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	<c:if test="${role.name == 'client'}">
		<div class = "row">
		 <div class="col-sm-6">
		<div class = "well">
			<form id="payment_form" action="controller" method="post">
				<input type="hidden" name="command" value="addPayment"/>
				<div class="form-group">
					<label for = "accountId">Account</label>
					<select class="form-control" id = "accountId" required>
						<c:forEach items="${accounts}" var="account">
							<option value = "${account.id}">${account.id}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Recipient</label>
					<input class="form-control" type = "text" id = "reciverId" required/>
				</div>
				<div class="form-group">
					<label for = "sum">Sum</label>
					<input class="form-control" type = "text" id = "sum" required/>
				</div>
				<div class="form-group">
					<label for = "comment">Comment</label>
					<textarea class="form-control" id = "comment"></textarea>
				</div>
				<button class = "btn btn-info" type = "submit">Submit</button>
			</form>
		</div>
		</div>
		
		<div class="col-sm-6">
		<div class = "well">
			<form id="foun_form" action="controller" method="post">
				<input type="hidden" name="command" value="addPayment"/>
				<input type="hidden" name = "found" value = "found"/>
				<div class="form-group">
					<label>Account</label>
					<select class="form-control" name = "accountId" id = "acc_id" required>
						<c:forEach items="${accounts}" var="account">
							<option onclick="reciver_id.value = this.value" value = "${account.id}">${account.id}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>Sum</label>
					<input class="form-control" type = "text" name = "sum" required/>
				</div>
				<div class="form-group">
					<label>Comment</label>
					<textarea class="form-control" name = "comment"></textarea>
				</div>
				<button class = "btn btn-info" type = "submit">Found</button>
			</form>
		</div>
		</div>
		</div>
	</c:if>
	</div>
	</div>
</body>
</html>
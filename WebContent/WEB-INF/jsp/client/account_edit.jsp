<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Edit</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
			integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
			crossorigin="anonymous">
</head>
<body>
	<div class = "container">
	<div class="col-lg-8 col-lg-offset-2">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="row">
	<div class = "well">
		<h3>Set account limit</h3>
		<span>Current limit: ${account.lim}</span>
		<form id="edit_form" action="controller" method="post"> 
			<div class="row">
				<input type="hidden" name="command" value="editAccount"/>
				<input type="hidden" name="accountId" value="${account.id}"/>
				<div class="form-group">
					<label>Limit</label>
					<input class="form-control" type = "text" name="iim" required/><br/>
				</div>
				<button type="submit" class="btn btn-lg btn-info">Submit</button>
			</div>								
		</form>
	</div>
	</div>
	
	<div class = "row">
	<div class = "well">
	<h3>Lock account</h3>
	<c:choose>
		<c:when test = "${account.accStatusId == 1}">
			<form id="lock_form" action="controller" method="post">
				<input type="hidden" name="command" value="lockAccount"/>
				<input type="hidden" name="accountId" value="${account.id}"/>
				<button class = "btn btn-info" type = "submit">Lock</button>
			</form>
		</c:when>
		
		<c:when test = "${account.accStatusId == 2 && account.request == false}">
			<form id="unlock_form" action="controller" method="post">
				<input type="hidden" name="command" value="requestAccount"/>
				<input type="hidden" name="accountId" value="${account.id}"/>
				<button class = "btn btn-info" type = "submit">Unlock</button>
			</form>
		</c:when>
	</c:choose>
	</div>
	</div>
	
	</div>
	</div>
</body>
</html>
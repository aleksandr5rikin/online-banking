<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
		crossorigin="anonymous">
</head>
<body>
	<div class = "container">
	<div class="col-lg-8 col-lg-offset-2">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<h1 class="well">Login</h1>
	<div class = "well">
	<div class="row">
	<form id="login_form" action="controller" method="post"> 
		<div class="col-sm-12">
		<div class="row">
		<input type="hidden" name="command" value="login"/>
		<div class="form-group">
			<label>Login</label>
			<input class="form-control" type = "text" name="login" required/><br/>
		</div>
		<div class="form-group">
			<label>Password</label>
			<input class="form-control" type="password" name="password" required/>
		</div>
		<button type="submit" class="btn btn-lg btn-info">Submit</button>	
		</div>
		</div>						
	</form>
	
	<script>
		var socket = new WebSocket("ws://localhost:8080/SummaryTask4/notifications/1");
	
		socket.onopen = function() {
			console.log("Соединение установлено.");
		};
	
		socket.onclose = function(event) {
		  if (event.wasClean) {
			  console.log('Соединение закрыто чисто');
		  } else {
			  console.log('Обрыв соединения'); // например, "убит" процесс сервера
		  }
		  console.log('Код: ' + event.code + ' причина: ' + event.reason);
		};
	
		socket.onmessage = function(event) {
			console.log("Получены данные " + event.data);
			document.getElementById("test").innerHTML = event.data;
		};
	
		socket.onerror = function(error) {
			console.log("Ошибка " + error.message);
		};
	</script>
	</div>
	</div>
	</div>
	</div>
</body>
</html>
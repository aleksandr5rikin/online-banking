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
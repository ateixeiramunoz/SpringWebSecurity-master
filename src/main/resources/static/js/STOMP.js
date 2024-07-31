// Declara una variable global que se usará para manejar la conexión a través del protocolo STOMP

var stompClient = null;
$( document ).ready(function() {

   $("#connect").on( "click",connect);
   $("#disconnect").on( "click",disconnect);
   $("#sendPrivate").on( "click",sendPrivate);


    connect();
});


// Función que actualiza la interfaz de usuario para reflejar el estado de conexión
function setConnected(connected) {
    console.log("Conectado");
    // Deshabilita el botón "Conectar" si ya está conectado
    $("#connect").attr("disabled", connected);
    // Deshabilita el botón "Desconectar" si no está conectado
    $("#disconnect").attr("disabled", !connected);
    // Muestra la sección "Conversación" si está conectado
    if (connected) {
        $("#conversation").show();
    }
    // Oculta la sección "Conversación" si no está conectado
    else {
        $("#conversation").hide();
    }
    // Borra los saludos previos en la interfaz de usuario

    $("#greetings").html("");

}


// Función que se conecta al servidor STOMP a través de un socket SockJS

function connect() {

    // Crea un objeto socket que se conecta a la URL '/gs-guide-websocket'
    var socket = new SockJS('/gs-guide-websocket');
    // Crea un objeto STOMP sobre el socket
    stompClient = Stomp.over(socket);
    // Se conecta al servidor STOMP con una función de callback que se ejecuta después de que la conexión se establece con éxito
    stompClient.connect({}, function (frame) {
        // Actualiza la interfaz de usuario para indicar que se ha establecido una conexión
        setConnected(true);
        $("#connection-status").text("Conectado");
        // Imprime un mensaje en la consola del navegador
        console.log('Connected: ' + frame);
        // Se suscribe al canal '/user/specific' para recibir mensajes privados



        stompClient.subscribe('/user/specific', function (message) {

            stompClient.send('/app/recibir', {}, JSON.stringify({ notificationID: JSON.parse(message.body).notificationID }));

            // Muestra el mensaje privado en la interfaz de usuario
            showPrivate(JSON.parse(message.body).text, JSON.parse(message.body).from );

            const toastLiveExample = document.getElementById('liveToast');
            const toast = new bootstrap.Toast(toastLiveExample);
            $(".toast-body").text(JSON.parse(message.body).text);
            toast.show();

        });



    });

}



// Función que desconecta del servidor STOMP
function disconnect() {
    // Si hay una conexión activa, desconecta el cliente STOMP
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    // Actualiza la interfaz de usuario para indicar que se ha desconectado
    setConnected(false);
    $("#connection-status").text("Desconectado");
    // Borra los saludos previos en la interfaz de usuar
    $("#greetings").empty();
     // Agrega un mensaje en la interfaz de usuario para indicar que se necesita una conexión para consultar o enviar mensajes
     $("#connection-status").after("<p>Conecta para consultar o enviar mensajes</p>");
     // Imprime un mensaje en la consola del navegador
     console.log("Disconnected");

 }


 // Función que envía un mensaje de saludo a través del canal '/app/hello'
 function sendHelloMessage() {

     stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));

 }

 // Función que envía un mensaje privado a otro usuario
 function sendPrivate() {

     // Obtiene el valor del campo de texto con id 'msgTxt'
     var text = document.getElementById('msgTxt').value;
     // Obtiene el valor del campo de texto con id 'namePrivate'
     var to = document.getElementById('namePrivate').value;
     // Obtiene el valor del campo de texto con id 'userID'
     var from = document.getElementById('userID').value;
     // Utiliza el cliente STOMP para enviar un mensaje a través del canal '/app/private' con el contenido y los destinatarios correspondientes
     stompClient.send("/app/private", {}, JSON.stringify({'text':text, 'to':to, 'from':from}));
     // Agrega una fila a la tabla 'greetings' con el mensaje enviado
     $("#greetings").append("<tr><td>YO: " + text + "</td></tr>");

 }




 //Función que muestra un mensaje privado
 function showPrivate(message, from) {
     // Agrega una fila a la tabla 'greetings' con el mensaje privado recibido y el remitente correspondiente
     $("#greetings").append("<tr><td>" + from + " : " + message + "</td></tr>");

 }



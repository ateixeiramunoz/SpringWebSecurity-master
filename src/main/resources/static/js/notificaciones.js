    $( document ).ready(function(){
         $.get( "/numeroNotificaciones", function( data ) {
                $("#numeronotifbadge").text(data)
         });

    });




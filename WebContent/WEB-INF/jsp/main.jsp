<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">

    <%
        response.setHeader("cache-Control","no-cache,no-store,must-revalidate"); // preventing user back to log page
        response.setHeader("Pragma","no-cache");
        response.setHeader("Expires","0");
    %>

    <style>
        <%@include file="/WebDevelopment/css/bootstrap.min.css"%>
    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.0.3/socket.io.js"></script>
    <script src="webjars/jquery/1.11.1/jquery.js"></script>

    <link rel="stylesheet" type="text/css" href="WebDevelopment/css/style.css"/>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font: 13px Helvetica, Arial; background-color: #202020; }
        form { background: #000; padding: 3px; position: fixed; bottom: 0; width: 100%; }
        form input { border: 0; padding: 10px; width: 90%; margin-right: .5%; }
        form button { width: 9%; background: rgb(130, 224, 255); border: none; padding: 10px; }
        #messages { list-style-type: none; margin: 0; padding: 0; }
        #messages li { padding: 5px 10px; color: #c1ddc1; background: #2c2c2c; }
        #messages li:nth-child(odd) { color: #c1ddc1; background: #303030; }
    </style>

    <title>Chat</title>
</head>
<body>

<ul id="messages"></ul>
<form data-toggle="validator" action="">
    <input class="has-feedback input-lg" id="m" autocomplete="off" placeholder="Wpisz wiadomość..." required />
    <button class="btn btn-success btn-lg">Wyślij</button>
</form>

<script type="text/javascript">
    $(function () {
        var socket = io('http://localhost:3000');

        $('form').submit(function(){
            socket.emit('chat message', '<%=request.getSession().getAttribute("userName")%>' + ' : ' + $('#m').val());
            $('#m').val('');
            return false;
        });
        socket.on('chat message', function(msg){
            $('#messages').append($('<li>').text(msg));
        });
    });
</script>

<% request.getSession().removeAttribute("error"); %>

</body>
</html>

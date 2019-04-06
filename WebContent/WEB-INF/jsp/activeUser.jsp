<%--
  Created by IntelliJ IDEA.
  User: Sebastian
  Date: 12.11.2018
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;  charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">

    <style>
        <%@include file="/WebDevelopment/css/bootstrap.min.css"%>
    </style>

    <link rel="stylesheet" type="text/css" href="WebDevelopment/css/style.css"/>

    <title>Aktywacja</title>

</head>
<body>
    <div id="divMain" class="col-xs-offset-5" >
        <p style="font-size: 30px; color: #a6a6a6;">Konto zostało aktywowane.</p>
        <form action="${pageContext.servletContext.contextPath}/activeUser" method="post">
            <input id="formActiveUserButton" type="submit" value="Wróć do logowania" class="btn btn-primary input-lg">
        </form>
    </div>
</body>
</html>

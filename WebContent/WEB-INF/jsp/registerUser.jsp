<%--
  Created by IntelliJ IDEA.
  User: Sebastian
  Date: 10.11.2018
  Time: 10:04
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

    <title>Rejestracja</title>

</head>
<body>

    <div id="divMain" class="col-xs-offset-5">
        <form data-toggle="validator" action="${pageContext.servletContext.contextPath}/registryUser" method="post">
            <input class="has-feedback input-lg" id="inputUser" type="text" name="Username" placeholder="Login..." required><br><br>
            <input class="has-feedback input-lg" id="inputPass" type="password" name="Password" placeholder="Hasło..." required><br><br>
            <input class="has-feedback input-lg" id="inputName" type="text" name="Name" placeholder="Imię..." required><br><br>
            <input class="has-feedback input-lg" id="inputSurname" type="text" name="Surname" placeholder="Nazwisko..." required><br><br>
            <input class="has-feedback input-lg" id="inputEmail" type="email" name="Email" placeholder="Email..." required><br><br>

            <%
                if(request.getSession().getAttribute("isRegistry") != null){
                    if(request.getSession().getAttribute("isRegistry").toString().length() > 0){
                        %>
            <p><font color="red">Użytkownik z podanym <%=request.getSession().getAttribute("error").toString()%> już istnieje.</font></p>
                    <%
                    }
                }
            %>

            <input id="formButton" type="submit" value="Zarejestruj" class="btn btn-primary input-lg">
        </form>
    </div>

</body>
</html>

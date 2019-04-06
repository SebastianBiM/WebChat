<%@ page contentType="text/html;  charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">

        <%
            response.setHeader("cache-Control","no-cache,no-store,must-revalidate"); // preventing user back to log page
            response.setHeader("Pragma","no-cache");
            response.setHeader("Expires","0");
        %>

        <style>
            <%@include file="WebDevelopment/css/bootstrap.min.css"%>
        </style>

        <link rel="stylesheet" type="text/css" href="WebDevelopment/css/style.css"/>

        <title>Logowanie</title>

    </head>
    <body>

        <div id="divMain" class="col-xs-offset-5">
            <form data-toggle="validator" action="${pageContext.servletContext.contextPath}/Main" method="post">
                <input class="has-feedback input-lg" id="inputUser" type="text" name="Username" placeholder="Login..." required><br><br>
                <input class="has-feedback input-lg" id="inputPass" type="password" name="Password" placeholder="Hasło..." required><br><br>
                <p><font color="red"><%=request.getSession().getAttribute("error") != null ? request.getSession().getAttribute("error") : ""%></font></p>
                <% request.getSession().removeAttribute("error"); %>
                <input id="formButton" type="submit" value="Zaloguj się" class="btn btn-primary input-lg">
            </form>
        </div>
        <div class="col-xs-offset-7">
            <form action="${pageContext.servletContext.contextPath}/registryUser" method="get">
                <input id="submitRegistry" type="submit" value="Zarejestruj się" class="btn btn-success">
            </form>
        </div>
        <div class="col-xs-offset-7">
            <form action="${pageContext.servletContext.contextPath}/restoreUser" method="get">
                <input id="submitRestore" type="submit" value="Odzyskaj hasło" class="btn btn-success">
            </form>
        </div>
    </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Sebastian
  Date: 10.11.2018
  Time: 13:22
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

    <script src="https://www.google.com/recaptcha/api.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <script type="text/javascript">
        function verifyRecaptchaCallback() {
            $('#formButton').removeAttr('disabled');
        }
    </script>


    <title>Odzyskiwanie</title>

</head>

<body>

    <div id="divMain" class="col-xs-offset-5">
        <form data-toggle="validator" action="${pageContext.servletContext.contextPath}/restoreUser" method="post">
            <input class="has-feedback input-lg" id="inputEmail" type="email" name="Email" placeholder="Email..." required><br><br>
            <div class="g-recaptcha" data-callback="verifyRecaptchaCallback" data-sitekey="6Lct7HkUAAAAAHPofVUB80iHK_WP--mHeC9umYOT"></div><br>
            <p hidden id="verifyRecaptcha" color="red">Udowodnij, że nie jesteś robotem.</p>
            <input id="formButton" disabled type="submit" value="Odzyskaj" class="btn btn-primary input-lg">
        </form>
    </div>

</body>
</html>

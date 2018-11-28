<%-- 
    Document   : autorHasObra2
    Created on : 30/05/2018, 09:56:00
    Author     : Jaque
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head> <link rel="stylesheet" href="../dist/css/sb-admin-2.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>AutorHasObra</title>
    </head>
    <body>
        <form method="post" action="${pageContext.request.contextPath}/autorHasObra">
            <input type="text" name="nomeAutorHasObra"/>
            <input type="submit" name="listar"/>
        </form>
    </body>
</html>

<%-- 
    Document   : moduloSistemaHasTipoUsuario2
    Created on : 30/05/2018, 09:56:00
    Author     : Jaque
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="${pageContext.request.contextPath}/moduloSistema">
            <input type="text" name="nomeModuloSistema"/>
            <input type="submit" name="listar"/>
        </form>
    </body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: klebe
  Date: 31/12/2021
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="org.quevedo.server.ee.utils.EEConst"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Cambio de contraseña</title>
</head>
<body>
    <h1>Cambio de contraseña</h1>

    <form method="post" action="${pageContext.request.contextPath}<% out.print(EEConst.PATH_SERVLET_PASSWORD_CHANGE); %>?<% out.print(EEConst.COD_PASS); %>=${param.codCambio}">
        <label>
            Introduzca su nueva contraseña
            <input type="password" name="<% out.print(EEConst.PARAM_PASSWORD); %>">
        </label>
        <label>
            Confirme su contraseña por favor
            <input type="password" name="<% out.print(EEConst.PARAM_PASSWORD_CONFIRM); %>">
        </label>
        <label>
            Enviar
            <input type="submit" name="submit">
        </label>
    </form>
</body>
</html>

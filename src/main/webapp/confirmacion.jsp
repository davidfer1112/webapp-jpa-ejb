<%@ page import="org.aguzman.apiservlet.webapp.headers.models.Carro" %><%--
  Created by IntelliJ IDEA.
  User: estudiante
  Date: 22/04/2024
  Time: 4:50 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // obtener el total, impuesto y tarifa de envio de la sesion
    Carro carro = (Carro) session.getAttribute("carro");
    double total = carro.getTotal();
    double impuesto = carro.getImpuesto();
    double envio = (double) session.getAttribute("tarifaEnvio");
    double totalPagar = total + impuesto + envio;

%>
<html>
<head>
    <title>Confirmacion del pago</title>
</head>
<body>
<table>
    <tr>
        <td>Total</td>
        <td><%= total %></td>
    </tr>
    <tr>
        <td>Impuesto</td>
        <td><%= impuesto %></td>
    </tr>
    <tr>
        <td>Tarifa de envio</td>
        <td><%= envio %></td>
    </tr>
    <tr>
        <td>Total a pagar</td>
        <td><%= totalPagar %></td>
    </tr>
</table>

<form action="" method="post">
    <input type="submit" value="aceptar">
    <input type="hidden" value="cancelar">
</form>
</body>
</html>

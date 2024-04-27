<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.aguzman.apiservlet.webapp.headers.models.Carro" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%/*
    // obtener el total, impuesto y tarifa de envio de la sesion
    Carro carro = (Carro) session.getAttribute("carro");
    double total = carro.getTotal();
    double impuesto = carro.getImpuesto();
    double envio = (double) session.getAttribute("tarifaEnvio");
    double totalPagar = total + impuesto + envio;
*/
%>
<html>
<head>
    <title>Confirmación de Factura</title>
</head>
<body>
<h2>Confirmación de Factura</h2>
<%// Long facturaId = (Long) request.getAttribute("facturaId"); %>
<p>Factura creada con éxito. Número de factura: 1</p>
<a href="index.jsp">Volver al inicio</a>
<table>
    <tr>
        <td>Total</td>
        <td><%= 100 %></td>
    </tr>
    <tr>
        <td>Impuesto</td>
        <td><%= 20 %></td>
    </tr>
    <tr>
        <td>Tarifa de envio</td>
        <td><%= 10 %></td>
    </tr>
    <tr>
        <td>Total a pagar</td>
        <td><%= 130 %></td>
    </tr>
</table>

<form action="" method="post">
    <input type="submit" value="aceptar">
    <input type="hidden" value="cancelar">
</form>
</body>
</html>

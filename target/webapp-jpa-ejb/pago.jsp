<%@page contentType="text/html" pageEncoding="UTF-8" import="org.aguzman.apiservlet.webapp.headers.models.*"%>
<%
    Carro carro = (Carro) session.getAttribute("carro");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Página de Pago</title>
</head>
<body>
<h1>Pagina de Pago</h1>

<%
    //success
    String success = request.getParameter("success");
    if (success != null && success.equals("1")) {
%>
<script>
    alert("Pago realizado. Total: " + <%= carro.getTotal() %>);
</script>
<% } %>

<%
    //error
    String error = request.getParameter("error");
    if (error != null && error.equals("1")) {
%>
<p style="color: red;">Pago rechazado</p>
<% } %>

<form action="/webapp-jpa-ejb/pago" method="post">
    <label for="nombre">Nombre en la tarjeta:</label>
    <input type="text" id="nombre" name="nombre" required><br><br>

    <label for="numero">Numero de tarjeta:</label>
    <input type="text" id="numero" name="numero" required><br><br>

    <label for="fecha">Fecha de expiracion:</label>
    <input type="text" id="fecha" name="fecha" placeholder="MM/YY" required><br><br>

    <label for="cvv">CVV:</label>
    <input type="text" id="cvv" name="cvv" maxlength="3" required><br><br>

    <label for="correo">Correo electrónico:</label>
    <input type="email" id="correo" name="correo" required><br><br>

    <input type="submit" value="Pagar">
</form>
</body>
</html>

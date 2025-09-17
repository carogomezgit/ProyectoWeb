<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> .
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<body>
<h2>Formulario de Cliente</h2>

<form action="seCliente" method="GET">

<index type="hidden" name="txtId" id="txtId" value="-1" />
<index type="hidden" name="Operacion" id="operacion" value="nuevo" />

<label for="txtNombre"> Nombre </label>
<input type="text" name="txtNombre" id="txtNombre" placeholder="Nombre" required />
<label for="txtApellido"> Apellido </label>
<input type="text" name="txtApellido" id="txtApellido" placeholder="Apellido" required />
<label for="txtTelefono"> Telefono </label>
<input type="text" name="txtTelefono" id="txtTelefono" placeholder="Telefono" required />

<input type="submit" value="Enviar" />

</form>

<a href="index.jsp"> Ir a Inicio </a>

</body>

</html>
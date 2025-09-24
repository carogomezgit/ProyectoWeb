<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> .
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<jsp:useBean id="cliente" class="org.progI.entities.Cliente" scope="request" />
<jsp:useBean id="clienteDao" class="org.progI.dao.ClienteImpl" scope="page" />

<c:if test="${param.operacion == 'editar'}">
    <c:set var="idCliente" value="${Integer.parseInt(param.id)}"> />
    <c:set var="clienteEditar" value="${clienteDao.getById(idCliente)}" />
</c:if>

<c:choose>
    <c:when test="${param.operacion == 'editar'}" > Editar Cliente </c:when>
    <c:when test="${param.operacion == 'eliminar'}" > Eliminar Cliente </c:when>
    <c:otherwise> Nuevo Cliente </c:otherwise>
</c:choose>

<h2>Formulario de Nuevo Cliente</h2>

<form action="seCliente" method="GET">

<input type="hidden" name="txtId" id="txtId"
    value="${not empty clienteEditar.id ? clienteEditar.id : -1}"
/>

<input type="hidden" name="operacion" id="operacion"
    value="${param.operacion =='editar' ? 'editar' : 'nuevo' }"
    />

<label for="txtNombre"> Nombre </label>
<input type="text" name="txtNombre" id="txtNombre" placeholder="Nombre"
    value="${not empty clienteEditar.nombre ? clienteEditar.nombre : ''}"
required />
<br>
<label for="txtApellido"> Apellido </label>
<input type="text" name="txtApellido" id="txtApellido" placeholder="Apellido"
    value="${not empty clienteEditar.apellido ? clienteEditar.apellido : ''}"
required />
<br>
<label for="txtTelefono"> Telefono </label>
<input type="text" name="txtTelefono" id="txtTelefono" placeholder="Telefono"
    value="${not empty clienteEditar.telefono ? clienteEditar.telefono : ''}"
required />
<br>
<input type="submit" value="Enviar" />

</form>

<a href="index.jsp"> Ir a Inicio </a>


<%@ include file="footer.jsp" %>
</body>

</html>
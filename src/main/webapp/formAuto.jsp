<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> .
<%@ taglib prefix="a" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<jsp:useBean id="auto" class="org.progI.entities.Auto" scope="request" />
<jsp:useBean id="autoDao" class="org.progI.dao.AutoImpl" scope="page" />

<a:if test="${param.operacion == 'editar'}">
    <a:set var="idAuto" value="${Integer.parseInt(param.id)}" />
    <a:set var="autoEditar" value="${autoDao.getById(idAuto)}" />
</a:if>

<a:choose>
    <a:when test="${param.operacion == 'editar'}" > Editar Auto </a:when>
    <a:when test="${param.operacion == 'eliminar'}" > Eliminar Auto </a:when>
    <a:otherwise> Nuevo Auto </a:otherwise>
</a:choose>

<html>

<body>
<h2>Formulario de Nuevo Auto</h2>

<form action="seAuto" method="GET">

<input type="hidden" name="txtId" id="txtId"
    value="${not empty autoEditar.id ? autoEditar.id : -1}"
/>

<input type="hidden" name="operacion" id="operacion"
    value="${param.operacion =='editar' ? 'editar' : 'nuevo' }"
    />

<label for="txtPatente"> Patente </label>
<input type="text" name="txtPatente" id="txtPatente" placeholder="Patente"
    value="${not empty autoEditar.patente ? autoEditar.patente : ''}"
required />
<br>
<label for="txtColor"> Color </label>
<input type="text" name="txtColor" id="txtColor" placeholder="Color"
    value="${not empty autoEditar.color ? autoEditar.color : ''}"
required />
<br>
<label for="txtApellido"> Año </label>
<input type="text" name="txtAnio" id="txtAnio" placeholder="Año"
    value="${not empty autoEditar.anio ? autoEditar.anio : ''}"
required />
<br>
<label for="txtApellido"> Kilometraje </label>
<input type="text" name="txtKilometraje" id="txtKilometraje" placeholder="Apellido"
    value="${not empty autoEditar.kilometraje ? autoEditar.kilometraje : ''}"
required />
<br>
<label for="txtMarca"> Marca </label>
<input type="text" name="txtMarca" id="txtMarca" placeholder="Marca"
    value="${not empty autoEditar.marca ? autoEditar.marca : ''}"
required />
<br>
<label for="txtModelo"> Modelo </label>
<input type="text" name="txtModelo" id="txtModelo" placeholder="Modelo"
    value="${not empty autoEditar.modelo ? autoEditar.modelo : ''}"
required />
<br>
<label for="txtCliente"> Cliente </label>
<input type="text" name="txtCliente" id="txtCliente" placeholder="Cliente"
    value="${not empty autoEditar.cliente ? autoEditar.cliente : ''}"
required />
<br>
<label for="txtSeguro"> Seguro </label>
<input type="text" name="txtSeguro" id="txtSeguro" placeholder="Seguro"
    value="${not empty autoEditar.seguro ? autoEditar.seguro : ''}"
required />
<br>

<input type="submit" value="Enviar" />

</form>

<a href="index.jsp"> Ir a Inicio </a>

</body>

</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> .
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<%@ page import="org.progI.dao.ClienteImpl" %>
<%@ page import="org.progI.entities.Cliente" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!-- abro bloque de declaracion java -->
<%!
    ClienteImpl clienteDao = new ClienteImpl();
    Cliente cliente = new Cliente();
    List<Cliente> listaClientes = clienteDao.getAll();
%>

<h2>Listado de Clientes</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Telefono</th>
        <th>Editar</th>
        <th>Borrar</th>
    </tr>
    </thead>
    <tbody>


        <c:forEach items="${listaClientes}" var="c">
        <tr>${c.getId()}</tr>
        <tr>${c.getNombre()}</tr>
        <tr>${c.getApellido()}</tr>
        <tr>${c.getTelefono()}</tr>
        <tr></tr>
        <tr></tr>
        </c:forEach>
    </tbody>
</table>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %> .
<%@ taglib prefix="a" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<%@ page import="org.progI.dao.AutoImpl" %>
<%@ page import="org.progI.entities.Auto" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%!
    AutoImpl autoDao = new AutoImpl();
    Auto auto = new Auto();
    List<Auto> listaAutos = autoDao.getAll();
%>

<h2>Listado de Autos</h2>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Patente</th>
        <th>Color</th>
        <th>Año</th>
        <th>Kilometraje</th>
        <th>Marca</th>
        <th>Modelo</th>
        <th>Borrar</th>
    </tr>
    </thead>
    <tbody>

    <tr>
    <% for(Auto a : listaAutos) { %>

        <td> <%=a.getPatente() %> </td>
        <td> <%=a.getColor() %> </td>
        <td> <%=a.getAnio() %> </td>
        <td> <%=a.getKilometraje() %> </td>
        <td> <%=a.getMarca() %> </td>
        <td> <%=a.getModelo() %> </td>
        <td> <a href="formAuto.jsp?operacion=editar&id=<%=a.getIdAuto() %>">Editar</a></td>
        <td> <a href="seAuto?operacion=eliminar&id=<%=a.getIdAuto() %>">Borrar</a></td>
        </tr>
     <% } %>
    </tbody>
</table>


package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.ClienteImpl;
import org.progI.entities.Cliente;

import java.io.IOException;
import java.util.Date;

public class seCliente extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setAttribute("mensaje", "Hola desde el Servlet ProgI IES63");
    req.setAttribute("fecha", new Date());

    String operacion = "nuevo";
    String nombre = "";
    String apellido = "";
    String telefono = "";
    int id = -1;

    operacion = req.getParameter("operacion");

    if (operacion == "editar" || operacion == "nuevo") {
      nombre = req.getParameter("txtNombre");
      apellido = req.getParameter("txtApellido");
      telefono = req.getParameter("txtTelefono");
      id = Integer.parseInt(req.getParameter("txtId"));
    }
    else {id = Integer.parseInt(req.getParameter("id"));}

    // para guardar el cliente
    ClienteImpl clienteDAO = new ClienteImpl();
    if (operacion == "nuevo") { // si es nuevo
      Cliente clienteNuevo = new Cliente(id, nombre, apellido, telefono);
      clienteDAO.insert(clienteNuevo);
    }

    // para editar el cliente
    if (operacion == "editar") { // si es editar
      Cliente clienteEditar = clienteDAO.getById(id);
      clienteEditar.setNombre(nombre);
      clienteEditar.setApellido(apellido);
      clienteEditar.setTelefono(telefono);
      clienteDAO.update(clienteEditar);
    }

    // para borrar el cliente
    if (operacion == "eliminar") { // si es borrar
      clienteDAO.delete(id);
    }

    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}
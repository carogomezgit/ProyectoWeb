package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    nombre = req.getParameter("txtNombre");
    apellido = req.getParameter("txtApellido");
    telefono = req.getParameter("txtTelefono");
    operacion = req.getParameter("operacion");
    id = Integer.parseInt(req.getParameter("txtId"));

    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}
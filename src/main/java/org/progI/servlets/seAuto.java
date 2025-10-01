package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.AutoImpl;
import org.progI.entities.Auto;
import org.progI.entities.Cliente;
import org.progI.entities.Marca;
import org.progI.entities.Seguro;

import java.io.IOException;
import java.util.Date;

public class seAuto extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setAttribute("mensaje", "Hola desde el Servlet ProgI IES63");
    req.setAttribute("fecha", new Date());

    String operacion = "nuevo";

    String patente = "";
    String color = "";
    int anio = 0;
    int kilometraje = 0;
    Marca marca = null;
    String modelo = "";
    // Cliente cliente;
    // Seguro seguro;
    int idAuto = -1;

    operacion = req.getParameter("operacion");

    if (operacion == "editar" || operacion == "nuevo") {
      patente = req.getParameter("txtPatente");
      color = req.getParameter("txtColor");
      anio = Integer.parseInt(req.getParameter("txtAnio"));
      kilometraje = Integer.parseInt(req.getParameter("txtKilometraje"));
      marca = Marca.valueOf(req.getParameter("txtMarca"));
      modelo = req.getParameter("txtModelo");
      // cliente = req.getParameter("txtCliente");
      // seguro = req.getParameter("txtSeguro");
      idAuto = Integer.parseInt(req.getParameter("txtIdAuto"));
    } else {
      idAuto = Integer.parseInt(req.getParameter("idAuto"));
    }

    // para guardar el auto
    AutoImpl autoDAO = new AutoImpl();
    if (operacion == "nuevo") { // si es nuevo
      Auto autoNuevo = new Auto(idAuto, patente, color, anio,
          kilometraje, marca, modelo);
      autoDAO.insert(autoNuevo);
    }

    // para editar el auto
    if (operacion == "editar") { // si es editar
      Auto autoEditar = autoDAO.getById(idAuto);
      autoEditar.setPatente(patente);
      autoEditar.setColor(color);
      autoEditar.setAnio(anio);
      autoEditar.setKilometraje(kilometraje);
      autoEditar.setMarca(marca);
      autoEditar.setModelo(modelo);
      autoDAO.update(autoEditar);
    }

    // para borrar el cliente
    if (operacion == "eliminar") { // si es borrar
      autoDAO.delete(idAuto);
    }

    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}


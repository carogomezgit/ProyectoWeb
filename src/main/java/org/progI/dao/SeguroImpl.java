package org.progI.dao;

import org.progI.entities.Seguro;
import org.progI.interfaces.AdmConexion;
import org.progI.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroImpl implements DAO<Seguro, Integer>, AdmConexion {

  private Connection conn = null;

  private static String SQL_INSERT =
      "INSERT INTO seguros (tipo,costoMensual,compania) " +
          "VALUES (?, ?, ?)";

  private static String SQL_UPDATE =
      "UPDATE seguros SET" +
          "tipo = ?," +
          "costoMensual = ?," +
          "compañia = ?" +
          "WHERE id = ?";

  private static String SQL_DELETE = "DELETE FROM seguros WHERE id = ?";
  private static String SQL_GETALL = "SELECT * FROM seguros ORDER BY tipo";
  private static String SQL_GETBYID = "SELECT * FROM seguros WHERE id = ?";
  private static String SQL_EXISTSBYID = "SELECT * FROM seguros WHERE id = ?";

  @Override
  public List<Seguro> getAll() {
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Seguro> listaSeguros = new ArrayList<>();

    try {
      pst = conn.prepareStatement(SQL_GETALL);
      rs = pst.executeQuery();

      while (rs.next()) {
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(rs.getInt("idSeguro"));
        seguro.setTipo(rs.getString("tipo"));
        seguro.setCostoMensual(rs.getDouble("costoMensual"));
        seguro.setCompania(rs.getString("compania"));

        listaSeguros.add(seguro);
      }

      pst.close();
      rs.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return listaSeguros;
  }

  @Override
  public void insert(Seguro objeto) {
    Seguro seguro = objeto;
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
      pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1, seguro.getTipo());
      pst.setDouble(2, seguro.getCostoMensual());
      pst.setString(3, seguro.getCompania());

      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Seguro agregado correctamente.");
      } else {
        System.out.println("No se pudo agregar el seguro.");
      }

      rs = pst.getGeneratedKeys();

      if (rs.next()) {
        seguro.setIdSeguro(rs.getInt(1));
        System.out.println("El id asignado es: " + seguro.getIdSeguro());
      }

      pst.close();
      rs.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Seguro objeto) {
    conn = obtenerConexion();

    if (this.existsById(objeto.getIdSeguro())) {
      try {
        PreparedStatement pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, objeto.getTipo());
        pst.setDouble(2, objeto.getCostoMensual());
        pst.setString(3, objeto.getCompania());
        pst.setInt(4, objeto.getIdSeguro());

        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Seguro actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el seguro");
        }

        pst.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
      }
    }
  }

  @Override
  public void delete(Integer id) {
    conn = obtenerConexion();

    try {
      PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
      pst.setInt(1, id);
      int resultado = pst.executeUpdate();

      if (resultado == 1) {
        System.out.println("Seguro eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el seguro");
      }

      pst.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el seguro. Error: " + e.getMessage());
    }
  }

  @Override
  public Seguro getById(Integer id) {
    Seguro seguro = null;

    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
      pst = conn.prepareStatement(SQL_EXISTSBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery();

      if (rs.next()) {
        seguro = new Seguro();
        seguro.setIdSeguro(rs.getInt("idSeguro"));
        seguro.setTipo((rs.getString("tipo")));
        seguro.setCostoMensual(rs.getDouble("costoMensual"));
        seguro.setCompania(rs.getString("compañia"));
      }

      pst.close();
      rs.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return seguro;
    // return null;
  }

  @Override
  public boolean existsById(Integer id) {
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      pst = conn.prepareStatement(SQL_GETBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery();

      if (rs.next()) {
        existe = true;
      }

      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return existe;
    // return false;
  }
}

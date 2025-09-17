package org.progI.dao;

import org.progI.entities.Cliente;
import org.progI.interfaces.AdmConexion;
import org.progI.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteImpl implements DAO<Cliente, Integer>, AdmConexion{

  private Connection conn = null;

  private static final String SQL_INSERT =
      "INSERT INTO clientes (nombre,apellido,telefono) " +
      "VALUES (?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE clientes SET " +
          "nombre = ? , apellido = ? , telefono = ? " +
          "WHERE idCliente = ?";

  private static final String SQL_DELETE = "DELETE FROM clientes WHERE idCliente = ?";
  private static final String SQL_GETALL = "SELECT * FROM clientes";
  private static final String SQL_GETBYID = "SELECT * FROM clientes WHERE idCliente = ? ";

  @Override
  public List<Cliente> getAll() {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    List<Cliente> listaClientes = new ArrayList<>();

    try {
      pst = conn.prepareStatement(SQL_GETALL);
      rs = pst.executeQuery();

      while (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setTelefono(rs.getString("telefono"));

        listaClientes.add(cliente);
      }

      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      System.out.println("Error al obtener todos los clientes");
      throw new RuntimeException(e);
    }

    return listaClientes;
  }

  @Override
  public void insert(Cliente objeto) {
    Cliente cliente = objeto;
    conn = obtenerConexion();

    PreparedStatement pst = null;

    try {
      pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1, cliente.getNombre());
      pst.setString(2, cliente.getApellido());
      pst.setString(3, cliente.getTelefono());

      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Cliente insertado correctamente");
      } else {
        System.out.println("No se pudo insertar el cliente");
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public void update(Cliente objeto) {
    Cliente cliente = objeto;

    if (this.existsById(cliente.getIdCliente())) {
      conn = obtenerConexion();
      PreparedStatement pst = null;

      try {
        pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1,cliente.getNombre());
        pst.setString(2,cliente.getApellido());
        pst.setString(3,cliente.getTelefono());
        pst.setInt(4,cliente.getIdCliente());

        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Cliente actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el cliente");
        }

        pst.close();
        conn.close();

      } catch (SQLException e) {
        throw new RuntimeException(e);
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
        System.out.println("Cliente eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el cliente");
      }

      pst.close();
      conn.close();

    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el cliente. Error: " + e.getMessage());
      throw new RuntimeException(e);
    }

  }

  @Override
  public Cliente getById(Integer id) {
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;
    Cliente cliente = null;

    try {
      pst = conn.prepareStatement(SQL_GETBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery();

      if (rs.next()) {
        cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setTelefono(rs.getString("telefono"));
      }

      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return cliente;
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

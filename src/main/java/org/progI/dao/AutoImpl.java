package org.progI.dao;

import org.progI.entities.Auto;
import org.progI.entities.Marca;
import org.progI.interfaces.AdmConexion;
import org.progI.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoImpl implements DAO<Auto, Integer>, AdmConexion {

  private Connection conn = null;

  private static final String SQL_INSERT =
      "INSERT INTO autos (idAuto,patente,color,anio,kilometraje,marca,modelo,idCliente,idSeguro) " +
      "VALUES            (?,   ?,   ?,   ?,   ?,    ?,   ?,   ?,   ?)";

  private static final String SQL_UPDATE =
      "UPDATE autos SET " +
          "patente = ? , color = ? , anio = ? , kilometraje = ? " +
          ", marca = ? , modelo = ? " +
          "WHERE idAuto = ?";

  private static  final String  SQL_DELETE= "DELETE FROM autos WHERE idAuto = ? ";
  private static final String SQL_GETALL = "SELECT * FROM autos ORDER BY patente";
  private static final String SQL_GETBYID = "SELECT * FROM autos WHERE idAuto = ?";

  @Override
  public List<Auto> getAll() {

    conn = obtenerConexion();

    String sql = "SELECT * FROM autos order by patente";

    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Auto> listaAutos = new ArrayList<>();

    try {
      pst = conn.prepareStatement(SQL_GETALL);

      // ejecutar consulta y guarda el resultado en resultset
      rs = pst.executeQuery();

      // recorrer el resultset y guardar los autos en una lista
      while (rs.next()) {
        Auto auto = new Auto();
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setAnio(rs.getInt("anio"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setModelo(rs.getString("modelo"));

        listaAutos.add(auto);
      }

      // cerrar el resultset y statement
      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
      throw new RuntimeException(e);
    }

    return listaAutos;
  }

  @Override
  public void insert(Auto objeto) {
    Auto auto = objeto;
    conn = obtenerConexion();

    // paso 2 crear string consulta SQL
    /*
    String sql =
        "INSERT INTO autos (idAuto,patente,color,anio,kilometraje,marca,modelo) " +
            "VALUES            (" + auto.getIdAuto() + "," +
            "'" + auto.getPatente() + "'," +
            "'" + auto.getColor() + "'," +
            +auto.getAnio() + "," +
            +auto.getKilometraje() + "," +
            "'" + auto.getMarca() + "'," +
            "'" + auto.getModelo() + "')";
     */

    ClienteImpl clienteImpl = new ClienteImpl();
    SeguroImpl seguroImpl = new SeguroImpl();
    boolean existeCliente = clienteImpl.existsById(auto.getCliente().getId());
    boolean existeSeguro = seguroImpl.existsById(auto.getSeguro().getIdSeguro());
    // solo guardo si existe el cliente y el seguro en la base de datos
    if (existeCliente && existeSeguro) {

      PreparedStatement pst = null;

      try {
        // con la conexion llamo al prepareStatement pasandole la consulta SQL
        pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, auto.getPatente());
        pst.setString(2, auto.getColor());
        pst.setInt(3, auto.getAnio());
        pst.setInt(4, auto.getKilometraje());
        pst.setString(5, auto.getMarca().toString());
        pst.setString(6, auto.getModelo());

        pst.setInt(7, auto.getCliente().getId());

        pst.setInt(8, auto.getSeguro().getIdSeguro());

        // paso 4 ejecutar instruccion
        // executeUpdate devuelve 1 si ejecuto correctamente, 0 caso contrario
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto insertado correctamente");
        } else {
          System.out.println("No se pudo insertar el auto");
        }

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
          auto.setIdAuto(rs.getInt(1));
          System.out.println("El id asignado es " + auto.getIdAuto());
        }

        // paso 5 cerrar conexion
        pst.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
    else {
      System.out.println("No se puede insertar el auto. No existe el cliente con id: " + auto.getCliente().getId());
      System.out.println("No se puede insertar el seguro. No existe el seguro con id: " + auto.getSeguro().getIdSeguro());
    }
  }

  @Override
  public void update(Auto objeto) {
    Auto auto = objeto;
    // establecer conexion

    // solo si el auto existe lo modifico
    if (this.existsById(auto.getIdAuto())) {
      /*
      String sql = "UPDATE autos SET " +
          "patente = '" + auto.getPatente() + "', " +
          "color = '" + auto.getColor() + "', " +
          "anio = " + auto.getAnio() + ", " +
          "kilometraje = " + auto.getKilometraje() + ", " +
          "marca = '" + auto.getMarca() + "', " +
          "modelo = '" + auto.getModelo() + "' " +
          "WHERE idAuto = " + auto.getIdAuto();
       */
      conn = obtenerConexion();

      // Se crea un statement
      PreparedStatement pst = null;

      try {
        // ejecuto
        pst = conn.prepareStatement(SQL_UPDATE); // CREO STATEMENT

        pst.setString(1, auto.getPatente());
        pst.setString(2, auto.getColor());
        pst.setInt(3, auto.getAnio());
        pst.setInt(4, auto.getKilometraje());
        pst.setString(5, auto.getMarca().toString());
        pst.setString(6, auto.getModelo());

        // executeUpdate devuelve 1 si ejecuto correctamente, 0 caso contrario
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el auto");
        }

        //cierro
        pst.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error al crear el prepareStatement");
      }
    }
  }

  @Override
  public void delete(Integer id) {
    conn = obtenerConexion();

    // String sql = "DELETE FROM autos WHERE idAuto = " + idauto;

    // crear PreparedStatement
    try {
      PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
      pst.setInt(1,id);
      int resultado = pst.executeUpdate();

      if (resultado == 1) {
        System.out.println("Auto eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el auto");
      }

      pst.close();
      conn.close();

    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el auto. Error: " + e.getMessage());
    }
  }

  @Override
  public Auto getById(Integer id) {
    // establecer conexion
    conn = obtenerConexion();

    // String sql = "SELECT * FROM autos WHERE idAuto = " + id;

    // se crea statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;
    Auto auto = null;

    try {
      pst = conn.prepareStatement(SQL_GETBYID); // CREO STATEMENT
      pst.setInt(1,id);
      rs = pst.executeQuery(); // EJECUTO CONSULTA

      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
        auto = new Auto();
        // asigno los datos a auto
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setMarca(Marca.valueOf( rs.getString("marca")));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setModelo(rs.getString("modelo"));
      }

      // CIERRO RESULTSET Y STATEMENT
      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return auto;
    // return null;
  }

  @Override
  public boolean existsById(Integer id) {

    conn = obtenerConexion();

    // se crea statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      pst = conn.prepareStatement(SQL_GETBYID); // CREO STATEMENT
      pst.setInt(1,id);
      rs = pst.executeQuery(); //EJECUTO CONSULTA
      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
        existe = true;
      }

      // cierro resultset, statement y conexion
      rs.close();
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return existe;
  }
}

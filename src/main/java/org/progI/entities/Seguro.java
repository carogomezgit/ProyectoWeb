package org.progI.entities;

import java.util.Objects;

public class Seguro implements Comparable<Seguro>{
  private int idSeguro;
  private String tipo;
  private double costoMensual;
  private String compania;

  public Seguro(){
  }

  public Seguro(String tipo, double costoMensual, String compania) {
    this.tipo = tipo;
    this.costoMensual = costoMensual;
    this.compania = compania;
  }

  public int getIdSeguro() {
    return idSeguro;
  }

  public void setIdSeguro(int idSeguro) {
    this.idSeguro = idSeguro;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public double getCostoMensual() {
    return costoMensual;
  }

  public void setCostoMensual(double costoMensual) {
    this.costoMensual = costoMensual;
  }

  public String getCompania() {
    return compania;
  }

  public void setCompania(String compania) {
    this.compania = compania;
  }


  @Override
  public String toString() {
    return "Seguro{" +
        "idSeguro=" + idSeguro +
        ", tipo='" + tipo + '\'' +
        ", costoMensual=" + costoMensual +
        ", compania='" + compania + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Seguro seguro = (Seguro) o;
    return idSeguro == seguro.idSeguro &&
        Objects.equals(tipo, seguro.tipo) &&
        Objects.equals(costoMensual, seguro.costoMensual) &&
        Objects.equals(compania, seguro.compania);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idSeguro, tipo, costoMensual, compania);
  }

  @Override
  public int compareTo(Seguro o) {
    Seguro otroSeguro = (Seguro) o;
    int resultadoValor = Double.compare(this.costoMensual, otroSeguro.costoMensual);
    if (resultadoValor != 0) {
      return resultadoValor;
    }
    int resultadoTipo = this.tipo.compareTo(otroSeguro.tipo);
    if (resultadoTipo != 0) {
      return resultadoTipo;
    }
    return this.compania.compareTo(otroSeguro.compania);
  }
}

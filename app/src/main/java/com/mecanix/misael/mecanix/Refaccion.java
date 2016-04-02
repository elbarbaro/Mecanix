package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 25/11/2015.
 */
public class Refaccion {

    private int idarticulo;
    private int idproveedor;
    private String descripcion;
    private int existencia;
    private double costo;

    public Refaccion(int idarticulo, int idproveedor, String descripcion, int existencia, double costo){

        this.idarticulo = idarticulo;
        this.idproveedor = idproveedor;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.costo = costo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public int getIdproveedor() {
        return idproveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public double getCosto() {
        return costo;
    }
}

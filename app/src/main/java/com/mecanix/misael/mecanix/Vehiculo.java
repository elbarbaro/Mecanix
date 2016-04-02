package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 23/11/2015.
 */
public class Vehiculo {

    private int id_vehiculo;
    private int num_cliente;
    private String placas;
    private String marca;
    private String submarca;
    private String modelo;

    public Vehiculo(int id, int num, String pla, String mar, String subm, String mod){
        this.id_vehiculo = id;
        this.num_cliente = num;
        this.placas = pla;
        this.marca = mar;
        this.submarca = subm;
        this.modelo = mod;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public void setNum_cliente(int num_cliente) {
        this.num_cliente = num_cliente;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setSubmarca(String submarca) {
        this.submarca = submarca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public int getNum_cliente() {
        return num_cliente;
    }

    public String getPlacas() {
        return placas;
    }

    public String getMarca() {
        return marca;
    }

    public String getSubmarca() {
        return submarca;
    }

    public String getModelo() {
        return modelo;
    }
}

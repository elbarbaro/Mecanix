package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 24/11/2015.
 */
public class Servicio {

    private int id;
    private String descripcion;
    private float costo;

    public Servicio(int id, String descripcion, float costo){
        this.id = id;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getCosto() {
        return costo;
    }
}

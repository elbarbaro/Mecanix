package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 25/11/2015.
 */
public class Vale {
    private int numvale;
    private int folio_orden;
    private String tiempo_emision;
    private int surtido;

    public Vale(int numvale, int folio_orden, String tiempo_emision, int surtido) {
        this.numvale = numvale;
        this.folio_orden = folio_orden;
        this.tiempo_emision = tiempo_emision;
        this.surtido = surtido;
    }

    public void setNumvale(int numvale) {
        this.numvale = numvale;
    }

    public void setFolio_orden(int folio_orden) {
        this.folio_orden = folio_orden;
    }

    public void setTiempo_emision(String tiempo_emision) {
        this.tiempo_emision = tiempo_emision;
    }

    public void setSurtido(int surtido) {
        this.surtido = surtido;
    }

    public int getNumvale() {
        return numvale;
    }

    public int getFolio_orden() {
        return folio_orden;
    }

    public String getTiempo_emision() {
        return tiempo_emision;
    }

    public int getSurtido() {
        return surtido;
    }
}


package com.mecanix.misael.mecanix;

import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 23/11/2015.
 */
public class Customer {

    private int numcliente;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String telefono_casa;
    private String telefono_cel;

    public Customer(int n, String nom, String ape, String dom, String telc, String tele_ce){

        this.numcliente = n;
        this.nombre = nom;
        this.apellidos = ape;
        this.domicilio = dom;
        this.telefono_casa = telc;
        this.telefono_cel = tele_ce;
    }

    public void setNumcliente(int num){
        this.numcliente = num;
    }

    public void setNombre(String nom){
        this.nombre = nom;
    }

    public void setApellidos(String ape){
        this.apellidos = ape;
    }
    public void setDomicilio(String dom){
        this.domicilio = dom;
    }
    public void setTelefono_casa(String tel_ca){
        this.telefono_casa = tel_ca;
    }
    public void setTelefono_cel(String tel_ce){
        this.telefono_cel = tel_ce;
    }

    public int getNumcliente() {
        return numcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTelefono_casa() {
        return telefono_casa;
    }

    public String getTelefono_cel() {
        return telefono_cel;
    }

    public String generateJson(){

        org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();

        jsonObject.put("numcliente",getNumcliente());
        jsonObject.put("nombre", getNombre());
        jsonObject.put("apellidos", getApellidos());
        jsonObject.put("domicilio", getDomicilio());
        jsonObject.put("telefono_casa", getTelefono_casa());
        jsonObject.put("telefono_cel", getTelefono_cel());

        List l = new LinkedList();
        l.addAll(Arrays.asList(jsonObject));

        String jsonstring = JSONValue.toJSONString(l);

        System.out.println("JSON GENERADO: " + jsonstring);
        return jsonstring;
    }
}

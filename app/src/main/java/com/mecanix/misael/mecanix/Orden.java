package com.mecanix.misael.mecanix;

import org.json.simple.JSONValue;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 20/11/2015.
 */
public class Orden {

    private int folio;
    private int num_cliente;
    private int id_empleado;
    private int id_vehiculo;
    private String nombreCliente;
    private String fecha_entrada;
    private String fecha_salida;
    private int estatus;
    private int num_factura;

    /*public Orden(int num_cliente, int id_empleado, int id_vehiculo, Date fecha_entrada, int estatus){


    }*/

    public Orden(int folio, int num_cliente, int id_empleado, int id_vehiculo, String fecha_entrada, int estatus){

        this.folio = folio;
        this.num_cliente = num_cliente;
        this.id_empleado = id_empleado;
        this.id_vehiculo = id_vehiculo;
        this.fecha_entrada = fecha_entrada;
        this.estatus = estatus;
    }

    public Orden(int folio, int numcliente, String fecha_in, String nombrefull, int estatus){
        this.folio = folio;
        this.num_cliente = numcliente;
        this.fecha_entrada = fecha_in;
        this.nombreCliente = nombrefull;
        this.estatus = estatus;
    }

    public void setFolio(int folio){

        this.folio = folio;
    }
    public void setNum_cliente(int num_cliente){

        this.num_cliente = num_cliente;
    }
    public void setId_empleado(int id_empleado){

        this.id_empleado = id_empleado;
    }
    public void setId_vehiculo(int id_vehiculo){

        this.id_vehiculo = id_vehiculo;
    }
    public void setFecha_entrada(String fecha_entrada){

        this.fecha_entrada = fecha_entrada;
    }
    public void setFecha_salida(String fecha_salida){

        this.fecha_salida = fecha_salida;
    }
    public void setEstatus(int estatus){

        this.estatus = estatus;
    }
    public void setNum_factura(int num_factura){

        this.num_factura = num_factura;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getFolio(){

        return this.folio;
    }

    public int getNum_cliente(){

        return this.num_cliente;
    }

    public int getId_empleado(){

        return this.id_empleado;
    }

    public int getId_vehiculo(){

        return this.id_vehiculo;
    }

    public String getFecha_entrada(){

        return this.fecha_entrada;
    }

    public String getFecha_salida(){

        return this.fecha_salida;
    }

    public int getEstatus(){

        return this.estatus;
    }

    public int getNum_factura(){

        return this.num_factura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String toString(){

        return Integer.toString(folio)+"," + Integer.toString(num_cliente) + ","
                + fecha_entrada + "," + Integer.toString(estatus);
    }

    public String generateJson(){

        /*org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();

        jsonObject.put("folio",getFolio());
        jsonObject.put("nombre", getNombre());
        jsonObject.put("apellidos", getApellidos());
        jsonObject.put("domicilio", getDomicilio());
        jsonObject.put("telefono_casa", getTelefono_casa());
        jsonObject.put("telefono_cel", getTelefono_cel());

        List l = new LinkedList();
        l.addAll(Arrays.asList(jsonObject));

        String jsonstring = JSONValue.toJSONString(l);

        System.out.println("JSON GENERADO: " + jsonstring);
        return jsonstring;*/
        return null;
    }
}

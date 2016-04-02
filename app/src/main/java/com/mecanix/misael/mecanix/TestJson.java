package com.mecanix.misael.mecanix;

import android.app.Application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MISAEL on 15/11/2015.
 */
public class TestJson extends Application{

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVER_PATH = "http://mandt.com.mx/mecanix/";

    @Override
   public void onCreate(){
        super.onCreate();
        printJsonData();
   }

    public void printJsonData(){
        //Obtenemos el JSON
        String json = getJSON();
        //Lo mostramos
        showJSON(json);
    }

    private static String getJSON(){

        StringBuffer response = null;

        try {
            //Generar la URL
            String url = SERVER_PATH+"getDataClienteJson.php";
            //Creamos un nuevo objeto URL con la url donde pedir el JSON
            URL obj = new URL(url);
            //Creamos un objeto de conexión
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //Añadimos la cabecera
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            // Enviamos la petición por POST
            con.setDoOutput(true);
            //Capturamos la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            //Mostramos la respuesta del servidor por consola
            System.out.println("Respuesta del servidor: "+response);
            System.out.println();
            //cerramos la conexión
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    private static void showJSON(String json){
        System.out.println("INFORMACIÓN OBTENIDA DE LA BASE DE DATOS:");
        //Crear un Objeto JSON a partir del string JSON
        Object jsonObject = JSONValue.parse(json.toString());
        //Convertir el objeto JSON en un array
        JSONArray array=(JSONArray)jsonObject;
        //Iterar el array y extraer la información
        for(int i=0;i<array.size();i++){
            JSONObject row =(JSONObject)array.get(i);
            String nombre = row.get("NOMBRE").toString();
            String apellidos = row.get("APELLIDO").toString();
            String domicilio = row.get("DOMICILIO").toString();
            String email = row.get("EMAIL").toString();
            String telefono = row.get("TELEFONO").toString();

            //Mostrar la información en pantalla
            System.out.println("Nombre: " + nombre + "|| Apellidos: " + apellidos + "|| Domicilio: " + domicilio + "|| Email: " +
            email + "|| Telefono: " + telefono );
        }
    }
}


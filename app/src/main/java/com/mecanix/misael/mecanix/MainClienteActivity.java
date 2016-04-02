package com.mecanix.misael.mecanix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MainClienteActivity extends AppCompatActivity {

    ListView list_cliente;
    List<Customer> list_clientev = new ArrayList<Customer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaclientes_listview);

        list_cliente = (ListView) findViewById(R.id.listv_cliente);

        if(conexionDisponible()) {
            DataCliente dtc = new DataCliente();
            dtc.execute();
        }

        list_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Lister Numbre: " + (position + 1) , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayAdapter<String> fill_customers(){

        String[] customers = new String[] {"Brayan" , "Fernando", "Leopoldo", "Sergio", "Jaime",
                "Ricardo", "Ivan", "Juan", "Pablo", "Jesus"};
        String[] cell_phones = new String[] {"3324135646","3312345676","3345678923","3387642345",
                "3312567676","3356781256","3356423456","3390867087","3342145678","3360708090"};

        List<Cliente> listCliente = new ArrayList<Cliente>();

        for(int i = 0; i < 10; i++){
            listCliente.add(new Cliente(customers[i], cell_phones[i]));
        }

        ArrayAdapter adapter = new ClienteArrayAdapter<Cliente>(this,listCliente);

        return adapter;
    }

    public ArrayAdapter<String> fillJsonCustomers(String json) throws JSONException {

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_clientev.add(new Customer(Integer.parseInt(row.get("numcliente").toString()),
                    row.get("nombre").toString(), row.get("apellidos").toString(), row.get("domicilio").toString(),
                    row.get("telefono_casa").toString(), row.get("telefono_cel").toString()));
        }

        ArrayAdapter adapter = new CustomerArrayAdapter(this, list_clientev);
        return adapter;
    }

    private class DataCliente extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(MainClienteActivity.this, "Por favor espera...",null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String respuesta = "";
                Hermes connection = new Hermes();
                respuesta = connection.talkToOlimpus("getDataClienteJson.php", "clave=roja");
                return respuesta;

            } catch (Exception e) {
                Toast.makeText(MainClienteActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();

            try {
                ArrayAdapter<String> adapter = fillJsonCustomers(result);
                list_cliente.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected Boolean conexionDisponible(){
        if(conexionWifi()){
            Toast.makeText(this,"Conexion Wifi disponible",Toast.LENGTH_LONG).show();
            return true;
        }
        else if(conexionDatosMoviles()){
            Toast.makeText(this, "Conexion Datos Moviles disponible", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(this, "Conexion no disponible",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected Boolean conexionWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(info != null){
                if (info.isConnected()){
                   return true;
                }
            }
        }
        return false;
    }

    protected Boolean conexionDatosMoviles(){
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(info != null){
                if(info.isConnected()){
                    return true;
                }
            }
        }
        return false;
    }
}

package com.mecanix.misael.mecanix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 22/11/2015.
 */
public class OpenOrdenActivity extends AppCompatActivity {

    EditText edit_cliente;
    EditText edit_vehiculo;
    List<Customer> list_clientev;
    List<Vehiculo> list_vehiculov;
    ListView list_dialog;
    Customer seleccionado = null;
    Vehiculo vehiculo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_orden);

        edit_cliente = (EditText) findViewById(R.id.ord_cliente);
        edit_vehiculo = (EditText) findViewById(R.id.edit_ord_vehiculo);

        ImageButton button_searchc = (ImageButton) findViewById(R.id.button_search_c);
        ImageButton button_searchv = (ImageButton) findViewById(R.id.button_search_v);

        Button button_addorden = (Button) findViewById(R.id.button_ord_add);
        Button button_cancelord = (Button) findViewById(R.id.button_ord_cancel);

        button_addorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrden();
            }
        });

        button_searchc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp(v);
            }
        });

        button_searchv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_cliente.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
                } else {
                    popUpV(v);
                }
            }
        });


    }

    public void popUp(View view){

        final Dialog dialog = new Dialog(OpenOrdenActivity.this);
        dialog.setContentView(R.layout.listaclientes_listview);
        dialog.setTitle("Clientes");
        list_dialog = (ListView) dialog.findViewById(R.id.listv_cliente);

        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            ClienteDialogTask clienteDialogTask = new ClienteDialogTask();
            clienteDialogTask.execute();
        }

        list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView name_c = (TextView) view.findViewById(R.id.firstLine);
                seleccionado = list_clientev.get(position);
                edit_cliente.setText(seleccionado.getNombre() + " " + seleccionado.getApellidos());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void popUpV(View view){

        final Dialog dialog = new Dialog(OpenOrdenActivity.this);
        dialog.setContentView(R.layout.listaclientes_listview);
        dialog.setTitle("Vehiculos");
        list_dialog = (ListView) dialog.findViewById(R.id.listv_cliente);

        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            VehiculoDialogTask vehiculoDialogTask = new VehiculoDialogTask();
            vehiculoDialogTask.execute();
        }

        list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView name_c = (TextView) view.findViewById(R.id.firstLine);
                vehiculo = list_vehiculov.get(position);
                edit_vehiculo.setText(vehiculo.getMarca());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void addOrden(){
        if(edit_cliente.getText().toString().equals("") || edit_vehiculo.getText().toString().equals("")){
            Toast.makeText(this, "LLENA TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }
        else{
            if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
                OrdenAddTask ordenAddTask = new OrdenAddTask();
                ordenAddTask.execute();
            }
        }
    }
    public class ClienteDialogTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                Hermes request = new Hermes();
                result = request.talkToOlimpus("getDataClienteJson.php", "clave=roja");
                return result;
            }
            catch (Exception ex){
                System.out.println(ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                ArrayAdapter<String> adapter = fillJsonCustomers(s);
                list_dialog.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public class VehiculoDialogTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                String json = URLEncoder.encode(seleccionado.generateJson(),"UTF-8");
                Hermes request = new Hermes();
                result = request.talkToOlimpus("getDataVehiculoClienteJson.php", "json=" + json);
                return result;
            }
            catch (Exception ex){
                System.out.println(ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                ArrayAdapter<String> adapter = fillJsonCars(s);
                list_dialog.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class OrdenAddTask extends AsyncTask<String, Void, String>{

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(OpenOrdenActivity.this,"Generando Orden...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String response = "";
                String json = URLEncoder.encode(generateJsonOrden(),"UTF-8");
                Hermes request = new Hermes();
                response = request.talkToOlimpus("requestOrden.php","json=" + json);
                return response;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            cleanEditOrden(s);
        }
    }
    public ArrayAdapter<String> fillJsonCustomers(String json) throws JSONException {

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        list_clientev = new ArrayList<Customer>();

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_clientev.add(new Customer(Integer.parseInt(row.get("numcliente").toString()),
                    row.get("nombre").toString(),row.get("apellidos").toString(), row.get("domicilio").toString(),
                    row.get("telefono_casa").toString(),row.get("telefono_cel").toString()));
        }

        ArrayAdapter adapter = new CustomerArrayAdapter(this, list_clientev);
        return adapter;
    }

    public ArrayAdapter<String> fillJsonCars(String json){

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        list_vehiculov = new ArrayList<Vehiculo>();

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_vehiculov.add(new Vehiculo(Integer.parseInt(row.get("id_vehiculo").toString()),
                    Integer.parseInt(row.get("num_cliente").toString()), row.get("placas").toString(), row.get("marca").toString(),
                    row.get("submarca").toString(), row.get("modelo").toString()));
        }

        ArrayAdapter adapter = new VehiculoArrayAdapter(this, list_vehiculov);
        return adapter;
    }

    public String generateJsonOrden(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("numcliente",seleccionado.getNumcliente());
        jsonObject.put("id_vehiculo",vehiculo.getId_vehiculo());
        jsonObject.put("id_empleado", getIntent().getStringExtra("session"));

        List l = new LinkedList();
        l.addAll(Arrays.asList(jsonObject));

        String jsonString = JSONValue.toJSONString(l);
        System.out.println("JSON GENERADO: " + jsonString);
        return jsonString;
    }

    public void cleanEditOrden(String result){
        if(result.equals("INSERTADO CON EXITO")){
            edit_cliente.setText("");
            edit_vehiculo.setText("");
            Toast.makeText(OpenOrdenActivity.this,"ORDEN GENERADA", Toast.LENGTH_SHORT).show();
        }
    }
}

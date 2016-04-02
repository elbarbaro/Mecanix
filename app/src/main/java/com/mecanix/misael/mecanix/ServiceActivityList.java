package com.mecanix.misael.mecanix;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 24/11/2015.
 */
public class ServiceActivityList extends AppCompatActivity {

    ListView listService;
    List<Servicio> list_servicio = new ArrayList<Servicio>();
    ArrayAdapter<String> adapter;
    String[] sessionOrden;
    String JSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service);

        sessionOrden = getIntent().getStringArrayExtra("orden");

        listService = (ListView)findViewById(R.id.listVItems);
        registerForContextMenu(listService);
        EditText edit_search = (EditText)findViewById(R.id.edit_serach);
        edit_search.setVisibility(View.VISIBLE);

        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            DataService dataService = new DataService();
            dataService.execute();
        }

        listService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), list_servicio.get(position).getDescripcion(), Toast.LENGTH_SHORT).show();
            }
        });

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_servicio,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_add_servicio:
                JSON = generateDetOrdenJson(info.position);
                if (CheckConnectionServices.connectionAvailable(getApplicationContext())) {
                    DataDetOrdenTask dataDetOrdenTask = new DataDetOrdenTask();
                    dataDetOrdenTask.execute();
                }
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return false;
    }

    public class DataService extends AsyncTask<String, Void,String>{

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ServiceActivityList.this, "Cargando Servicios...",null, true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String response = "";
                Hermes request = new Hermes();
                response = request.talkToOlimpus("getDataService.php","clave=roja");
                return response;
            }
            catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            try{
                adapter = fillJsonService(s);
                listService.setAdapter(adapter);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class DataDetOrdenTask extends AsyncTask<String,Void,String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ServiceActivityList.this,"Agregando servicio...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String response = "";
                String jsonString = URLEncoder.encode(JSON,"UTF-8");
                Hermes request = new Hermes();
                response = request.talkToOlimpus("requestDetOrden.php","json=" + jsonString);
                return response;
            }
            catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            checkPeticionDetOrden(s);
        }
    }

    public void checkPeticionDetOrden(String result){
        if(result.equals("INSERTADO CON EXITO")){
            Toast.makeText(getApplicationContext(),"SERVICIO AGREGADO",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"OCURRIO UN PROBLEMA",Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayAdapter<String> fillJsonService(String json) throws JSONException {

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_servicio.add(new Servicio(Integer.parseInt(row.get("id_servicio").toString()),
                    row.get("descripcion").toString(), Float.parseFloat(row.get("costo").toString())));
        }

        ArrayAdapter adapter = new ServiceArrayAdapter(this, list_servicio);
        return adapter;
    }

    public String generateDetOrdenJson(int position){

        JSONObject object = new JSONObject();

        object.put("folio", sessionOrden[0]);
        object.put("id_empleado", sessionOrden[1]);
        object.put("id_servicio", list_servicio.get(position).getId());

        List l = new LinkedList();
        l.addAll(Arrays.asList(object));

        String jsonString = JSONValue.toJSONString(l);
        System.out.println("JSON GENERADO: " + jsonString);
        return jsonString;
    }
}

package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 25/11/2015.
 */
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
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
public class ValeActivityList extends AppCompatActivity {

    ListView listvale;
    ListView list_dialog;
    List<Vale> list_vale = new ArrayList<Vale>();
    List<Refaccion> list_refas = new ArrayList<Refaccion>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter_dialog;
    String[] sessionOrden;
    String JSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_service);

        sessionOrden = getIntent().getStringArrayExtra("orden");

        listvale = (ListView)findViewById(R.id.listVItems);
        registerForContextMenu(listvale);
        EditText edit_search = (EditText)findViewById(R.id.edit_serach);
        edit_search.setVisibility(View.VISIBLE);

        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            DataVale dataVale = new DataVale();
            dataVale.execute();
        }

        listvale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(list_vale.get(position).getNumvale()), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_vale,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_addVale:
                JSON = generateValeJson();
                if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
                    DataValeTask dataValeTask = new DataValeTask();
                    dataValeTask.execute();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vale, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_add_refa:
                popUp(info.position);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return false;
    }

    public void popUp(final int positionv){

        final Dialog dialog = new Dialog(ValeActivityList.this);
        dialog.setContentView(R.layout.listaclientes_listview);
        dialog.setTitle("Refacciones");
        list_dialog = (ListView) dialog.findViewById(R.id.listv_cliente);

        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            RefaccionDialogTask refaccionDialogTask = new RefaccionDialogTask();
            refaccionDialogTask.execute();
        }

        list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView name_c = (TextView) view.findViewById(R.id.firstLine);
                dialog.dismiss();
                JSON = generateDetValeJson(positionv,position);
                if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
                    DataDetValeTask dataDetValeTask = new DataDetValeTask();
                    dataDetValeTask.execute();
                }
            }
        });
        dialog.show();
    }
    public class DataVale extends AsyncTask<String, Void,String>{

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ValeActivityList.this, "Cargando...",null, true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String response = "";
                Hermes request = new Hermes();
                response = request.talkToOlimpus("getDataValeJson.php","clave=roja");
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
                adapter = fillJsonVale(s);
                listvale.setAdapter(adapter);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class DataDetValeTask extends AsyncTask<String,Void,String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ValeActivityList.this,"Agregando refaccion...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String response = "";
                String jsonString = URLEncoder.encode(JSON,"UTF-8");
                Hermes request = new Hermes();
                response = request.talkToOlimpus("requestDetVale.php","json=" + jsonString);
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
            checkPeticionDetVale(s);
        }
    }

    public class DataValeTask extends AsyncTask<String,Void,String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ValeActivityList.this,"Agregando...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String response = "";
                String jsonString = URLEncoder.encode(JSON,"UTF-8");
                Hermes request = new Hermes();
                response = request.talkToOlimpus("requestVale.php","json=" + jsonString);
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
            checkRequestVale(s);
        }
    }

    public void checkPeticionDetVale(String result){
        if(result.equals("INSERTADO CON EXITO")){
            Toast.makeText(getApplicationContext(),"REFACCION AGREGADO",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"OCURRIO UN PROBLEMA",Toast.LENGTH_SHORT).show();
        }
    }

    public void checkRequestVale(String result){
        if(result.equals("INSERTADO CON EXITO")){
            Toast.makeText(getApplicationContext(),"VALE GENERADO",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"OCURRIO UN PROBLEMA",Toast.LENGTH_SHORT).show();
        }
    }

    public class RefaccionDialogTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                Hermes request = new Hermes();
                result = request.talkToOlimpus("getDataRefaccionJson.php", "clave=roja");
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
                ArrayAdapter<String> adapter = fillJsonRefaccion(s);
                list_dialog.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public ArrayAdapter<String> fillJsonVale(String json) throws JSONException {

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_vale.add(new Vale(Integer.parseInt(row.get("numvale").toString()),
                    Integer.parseInt(row.get("folio_orden").toString()), row.get("tiempo_emision").toString(), Integer.parseInt(row.get("surtido").toString())));
        }

        ArrayAdapter adapter = new ValeArrayAdapter(this, list_vale);
        return adapter;
    }

    public String generateDetValeJson(int position, int positon_refa){

        JSONObject object = new JSONObject();

        object.put("numvale", list_vale.get(position).getNumvale());
        object.put("id_articulo", list_refas.get(positon_refa).getIdarticulo());

        List l = new LinkedList();
        l.addAll(Arrays.asList(object));

        String jsonString = JSONValue.toJSONString(l);
        System.out.println("JSON GENERADO: " + jsonString);
        return jsonString;
    }

    public String generateValeJson(){

        JSONObject object = new JSONObject();

        object.put("folio_orden", sessionOrden[0]);

        List l = new LinkedList();
        l.addAll(Arrays.asList(object));

        String jsonString = JSONValue.toJSONString(l);
        System.out.println("JSON GENERADO: " + jsonString);
        return jsonString;
    }

    public ArrayAdapter<String> fillJsonRefaccion(String json) throws JSONException {

        Object jsonObject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonObject;

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject) array.get(i);
            list_refas.add(new Refaccion(Integer.parseInt(row.get("id_articulo").toString()),
                    Integer.parseInt(row.get("id_proveedor").toString()),row.get("descripcion").toString(),
                    Integer.parseInt(row.get("existencia").toString()),Double.parseDouble(row.get("costo").toString())));
        }

        ArrayAdapter adapter = new RefaccionArrayAdapter(this, list_refas);
        return adapter;
    }
}


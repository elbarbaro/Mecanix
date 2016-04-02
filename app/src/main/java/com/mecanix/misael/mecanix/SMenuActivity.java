package com.mecanix.misael.mecanix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String JSON;
    private String session;
    private List<Orden> list_ord = new ArrayList<Orden>();
    ListView list_orden;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smenu);

        String json = getIntent().getStringExtra("json");

        list_orden = (ListView) findViewById(R.id.listViewItems);
        registerForContextMenu(list_orden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //TextView text_username = (TextView) findViewById(R.id.text_username);
        //text_username.setText("Nombre de Usuario");

        //TextView text_funcion = (TextView) findViewById(R.id.text_funcion);
        //text_funcion.setText("Funcion");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = LayoutInflater.from(this).inflate(R.layout.nav_header_smenu, null);
        navigationView.addHeaderView(view);
        TextView text_username = (TextView) view.findViewById(R.id.text_username);
        text_username.setText(setDataSession(json));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orden_item,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_add_ticket:
                Intent intent1 = new Intent(this,ValeActivityList.class);
                intent1.putExtra("orden", new String[]{Integer.toString(list_ord.get(info.position).getFolio()),session});
                startActivity(intent1);
                break;
            case R.id.menu_add_service:
                Intent intent = new Intent(this,ServiceActivityList.class);
                intent.putExtra("orden", new String[]{Integer.toString(list_ord.get(info.position).getFolio()),session});
                startActivity(intent);
                break;
            case R.id.menu_change_status:
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.add_cliente){
            Intent intent = new Intent(this, RegisterClienteActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.add_orden){
            Intent intent = new Intent(this, OpenOrdenActivity.class);
            intent.putExtra("session",session);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_orden) {
            // Handle the camera action
            setListViewOrden();
        } else if (id == R.id.nav_cliente) {
            Intent i = new Intent(this, MainClienteActivity.class);
            startActivity(i);
        }  else if (id == R.id.nav_cerrar_sesion) {

        } else if (id == R.id.nav_ayuda) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setMyjson(String value){
        JSON = value;
    }


    public void setListViewOrden(){

        EditText edit_search = (EditText) findViewById(R.id.edit_serach);
        edit_search.setVisibility(View.VISIBLE);
        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            DataOrden dataOrden = new DataOrden();
            dataOrden.execute();
        }
        /*list_orden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView texto = (TextView) view.findViewById(R.id.folio_orden);
                Toast.makeText(getApplicationContext(), "Folio de Orden: " + texto.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });*/

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

    public class DataOrden extends AsyncTask<String, Void, String>{

        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(SMenuActivity.this,"Cargando Ordenes...",null,true,true);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String response = "";
                Hermes request = new Hermes();
                response = request.talkToOlimpus("getDataOrdenJson.php","clave=roja");
                return response;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            try {
                adapter = fillOrdeJson(s);
                list_orden.setAdapter(adapter);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public ArrayAdapter<String> fill_Orden(){

        String [] folios = new String[]{"123456","134144", "2424525","4242452","4414121","5546462"};
        String [] numCliente = new String []{"112","1213","1213","2131","2424","54536"};
        String [] idEmpleado = new String[]{"1","2","3","4","5","6"};
        String [] idVehiculo = new String[]{"100","200","300","400","500","600"};
        String [] fechasEntrada = new String[]{"20-11-15","10-09-15","11-11-15","10-10-15","19-11-15","18-11-15"};
        String [] estatus = new String[]{"1","2","3","1","4","4"};

        List<Orden> listOrdenes = new ArrayList<Orden>();

        for(int i = 0; i < 6; i++){
            listOrdenes.add(new Orden(Integer.parseInt(folios[i]),Integer.parseInt(numCliente[i]),
                    Integer.parseInt(idEmpleado[i]), Integer.parseInt(idVehiculo[i]), fechasEntrada[i],Integer.parseInt(estatus[i])));
        }
        ArrayAdapter  adapter = new OrdenArrayAdapter(this, listOrdenes );

        return adapter;
    }

    public ArrayAdapter<String> fillOrdeJson(String json){

        Object jsonobject = JSONValue.parse(json.toString());
        JSONArray array = (JSONArray)jsonobject;

        for(int i = 0; i < array.size(); i++){
            JSONObject row = (JSONObject)array.get(i);
            list_ord.add(new Orden(Integer.parseInt(row.get("folio").toString()),
                    Integer.parseInt(row.get("numcliente").toString()),row.get("fecha_entrada").toString(),
                    row.get("nombre").toString() + " " + row.get("apellidos").toString(),Integer.parseInt(row.get("estatus").toString())));
        }

        ArrayAdapter adapter = new OrdenArrayAdapter(this,list_ord);
        return adapter;

    }

    public String setDataSession(String json) {

        try {
            boolean flag = false;

            if(json.equals("")){
                Toast.makeText(getApplicationContext(),"cadena vacia",Toast.LENGTH_LONG).show();
                return null;
            }

            Object jsonObject = JSONValue.parse(json);
            JSONArray array = (JSONArray) jsonObject;

            if (array.size() > 0) {
                JSONObject item = (JSONObject) array.get(0);

                if(json.contains("numcliente")){
                   session = item.get("numcliente").toString();
                }
                else{
                    session = item.get("id_empleado").toString();
                }
                return (item.get("nombre").toString() + " " + item.get("apellidos").toString());
            }
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
        return null;
    }
}

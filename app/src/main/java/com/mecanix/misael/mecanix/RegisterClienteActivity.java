package com.mecanix.misael.mecanix;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 16/11/2015.
 */
public class RegisterClienteActivity extends AppCompatActivity implements View.OnClickListener{

    private String jsonString = null;
    EditText editnombre;
    EditText editapellidos;
    EditText editDomicilio;
    EditText edittelefono_casa;
    EditText edittelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cliente);

        Button btnRegistrar = (Button) findViewById(R.id.button_register);
        btnRegistrar.setOnClickListener(this);
    }


    public void sendGET(){

        editnombre = (EditText) findViewById(R.id.text_name);
        editapellidos = (EditText) findViewById(R.id.text_lastname);
        editDomicilio = (EditText) findViewById(R.id.text_ciudad);
        edittelefono_casa = (EditText) findViewById(R.id.text_telefono_casa);
        edittelefono = (EditText) findViewById(R.id.text_telefonof);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("nombre",editnombre.getText().toString());
        jsonObject.put("apellidos", editapellidos.getText().toString());
        jsonObject.put("domicilio", editDomicilio.getText().toString());
        jsonObject.put("telefono_casa",edittelefono_casa.getText().toString());
        jsonObject.put("telefono_cel",edittelefono.getText().toString());

        List l = new LinkedList();
        l.addAll(Arrays.asList(jsonObject));

        jsonString = JSONValue.toJSONString(l);
        System.out.println("JSON GENERADO: \n " + jsonString);
    }

    @Override
    public void onClick(View v) {
        sendGET();
        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
            DataCliente dtc = new DataCliente();
            dtc.execute();
        }
    }

    private class DataCliente extends AsyncTask<String, Void, String> {

        ProgressDialog loading;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(RegisterClienteActivity.this, "Por favor espera...",null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                jsonString = URLEncoder.encode(jsonString,"UTF-8");

                String respuesta = "";
                Hermes connection = new Hermes();
                respuesta = connection.talkToOlimpus("requestCliente.php","json=" + jsonString);
                return respuesta;

            } catch (Exception e) {
                Toast.makeText(RegisterClienteActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            Toast.makeText(RegisterClienteActivity.this, result, Toast.LENGTH_LONG).show();
            CleanFieldsCliente(result);
        }
    }

    public void CleanFieldsCliente(String response){

        if(response.equals("INSERTADO CON EXITO")) {
            editnombre.setText("");
            editapellidos.setText("");
            editDomicilio.setText("");
            edittelefono_casa.setText("");
            edittelefono.setText("");
        }
    }
}

package com.mecanix.misael.mecanix;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MISAEL on 21/11/2015.
 */
public class UserRegisterActivity extends AppCompatActivity {

    EditText edit_namefull;
    EditText edit_usernamer;
    EditText edit_passwordr;
    EditText edit_dupassword;
    private String stringjson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        edit_namefull = (EditText) findViewById(R.id.edit_namefull);
        edit_usernamer = (EditText) findViewById(R.id.edit_usernamer);
        edit_passwordr = (EditText) findViewById(R.id.edit_passwordr);
        edit_dupassword = (EditText) findViewById(R.id.edit_dupassword);

        Button button_registeru = (Button) findViewById(R.id.button_registeru);
        Button button_cancelu = (Button) findViewById(R.id.button_cancelu);

        button_registeru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (jsonStringUserRegister() == null) {
                        Toast.makeText(getApplicationContext(), "LLENA TODO LOS CAMPOS", Toast.LENGTH_SHORT).show();
                    } else {
                        if(CheckConnectionServices.connectionAvailable(getApplicationContext())) {
                            UserRegisterTask userTask = new UserRegisterTask();
                            userTask.execute();
                        }
                    }
                }
                catch (Exception ex){

                }
            }
        });
    }

    public class UserRegisterTask extends AsyncTask<String, Void, String>{

        ProgressDialog loading;

        @Override
        protected String doInBackground(String... params) {

            try {

                stringjson = URLEncoder.encode(stringjson, "UTF-8");

                String respuesta = "";

                Hermes request = new Hermes();
                respuesta = request.talkToOlimpus("requestUsuario.php","json=" + stringjson);
                return respuesta;
            }
            catch (Exception ex){
                Toast.makeText(UserRegisterActivity.this,ex.toString(), Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(UserRegisterActivity.this, "Por favor espera...",null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loading.dismiss();
            Toast.makeText(UserRegisterActivity.this, result, Toast.LENGTH_LONG).show();
            CleanFieldsCliente(result);
        }
    }

    public void CleanFieldsCliente(String response){
        if(response.equals("INSERTADO CON EXITO")){
            edit_namefull.setText("");
            edit_usernamer.setText("");
            edit_passwordr.setText("");
            edit_dupassword.setText("");
        }
    }

    public String jsonStringUserRegister() throws UnsupportedEncodingException {

        if(edit_namefull.getText().toString().equals("") || edit_usernamer.getText().toString().equals("")
                || edit_passwordr.getText().toString().equals("") || edit_dupassword.getText().toString().equals("")){
            return null;
        }
        if(edit_dupassword.getText().toString().equals(edit_passwordr.getText().toString())){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", edit_namefull.getText().toString().split(" ")[0]);
            jsonObject.put("lastname", edit_namefull.getText().toString().split(" ")[1]);
            jsonObject.put("username", edit_usernamer.getText().toString());
            jsonObject.put("password", edit_passwordr.getText().toString());

            List l = new LinkedList();
            l.addAll(Arrays.asList(jsonObject)); //l.add()

            stringjson = JSONValue.toJSONString(l);

            System.out.println("JSON GENERADO: " + stringjson);

            return stringjson;
        }
        return null;
    }
}

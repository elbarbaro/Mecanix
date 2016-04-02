package com.mecanix.misael.mecanix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MISAEL on 15/11/2015.
 */
public class ClienteArrayAdapter<T> extends ArrayAdapter<T>{

    public ClienteArrayAdapter(Context context, List<T> objects){
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //obteniendo una instancia de inflater
        LayoutInflater layoutInflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del view de la fila
        View listItemView = convertView;

        //Comprobando si el view no existe
        if(listItemView == null){
            //Si no existe, entonces inflarlo con two_line_list_item.xml
            listItemView = layoutInflater.inflate(
                    R.layout.item_listview,
                    parent,
                    false);
        }
        //Obteniendo instancias de los componentes del layaout
        TextView textname = (TextView)listItemView.findViewById(R.id.firstLine);
        TextView textphone = (TextView)listItemView.findViewById(R.id.secondLine);
        ImageView image_icon = (ImageView) listItemView.findViewById(R.id.icon);
        //RelativeLayout rela = (RelativeLayout) listItemView.findViewById(R.id.fondoItem);

        //obteniendo la instancia del cliente actual
        T item = (T)getItem(position);

        //Separar la cadena con atributos del objeto

        String cadenaObjeto;
        cadenaObjeto = item.toString();
        textname.setText(cadenaObjeto.split(",")[0]);
        textphone.setText(cadenaObjeto.split(",")[1]);

        String cadena = textname.getText().toString();

        if((cadena.charAt(0) >= 65) && (cadena.charAt(0)<= 70)){
            image_icon.setImageResource(R.mipmap.cat);
            //rela.setBackgroundColor(Color.CYAN);
        }
        else if((cadena.charAt(0) >= 71) && (cadena.charAt(0) <= 76)){
            image_icon.setImageResource(R.mipmap.pig);
            //rela.setBackgroundColor(Color.GREEN);
        }
        else if((cadena.charAt(0) >= 77) && (cadena.charAt(0) <= 82)){
            image_icon.setImageResource(R.mipmap.dog);
            //rela.setBackgroundColor(Color.BLUE);
        }
        else{
            image_icon.setImageResource(R.mipmap.frog);
        }

        return listItemView;
    }
}

package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 23/11/2015.
 */

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

public class CustomerArrayAdapter extends ArrayAdapter<Customer> {

    public CustomerArrayAdapter(Context context, List<Customer> object) {
        super(context, 0, object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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
        Customer item = getItem(position);

        //Separar la cadena con atributos del objeto

        textname.setText(item.getNombre() + " " + item.getApellidos());
        textphone.setText(item.getTelefono_casa());

        String cadena = textname.getText().toString();

        if((cadena.charAt(0) >= 65) && (cadena.charAt(0)<= 70)){
            image_icon.setImageResource(R.drawable.user3);
            //rela.setBackgroundColor(Color.CYAN);
        }
        else if((cadena.charAt(0) >= 71) && (cadena.charAt(0) <= 76)){
            image_icon.setImageResource(R.drawable.user2);
            //rela.setBackgroundColor(Color.GREEN);
        }
        else if((cadena.charAt(0) >= 77) && (cadena.charAt(0) <= 82)){
            image_icon.setImageResource(R.drawable.user4);
            //rela.setBackgroundColor(Color.BLUE);
        }
        else{
            image_icon.setImageResource(R.drawable.user1);
        }

        return listItemView;
    }
}

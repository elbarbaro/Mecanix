package com.mecanix.misael.mecanix;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by MISAEL on 23/11/2015.
 */
public class VehiculoArrayAdapter extends ArrayAdapter<Vehiculo> {

    ImageView image_car;

    public VehiculoArrayAdapter(Context context, List<Vehiculo> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = layoutInflater.inflate(R.layout.item_vehiculo,parent,false);
        }

        image_car = (ImageView)listItemView.findViewById(R.id.icon_car);
        TextView text_marca = (TextView)listItemView.findViewById(R.id.text_marca);
        TextView text_placa = (TextView)listItemView.findViewById(R.id.text_placas);
        TextView text_modelo = (TextView)listItemView.findViewById(R.id.text_modelo);

        Vehiculo vehiculo = getItem(position);

        text_marca.setText(vehiculo.getMarca());
        text_placa.setText(vehiculo.getPlacas());
        text_modelo.setText(vehiculo.getModelo());

        setImageItemCar();

        return listItemView;
    }

    public void setImageItemCar(){

        Random r = new Random();

        int number = (r.nextInt(7) + 1);

        switch(number){
            case 1:
                image_car.setImageResource(R.drawable.car_blue);
                break;
            case 2:
                image_car.setImageResource(R.drawable.car_green);
                break;
            case 3:
                image_car.setImageResource(R.drawable.car_purple);
                break;
            case 4:
                image_car.setImageResource(R.drawable.car_green1);
                break;
            case 5:
                image_car.setImageResource(R.drawable.car_tinto);
                break;
            case 6:
                image_car.setImageResource(R.drawable.car_red);
                break;
            case 7:
                image_car.setImageResource(R.drawable.car_blue1);
                break;
            default:
                break;
        }
    }
}

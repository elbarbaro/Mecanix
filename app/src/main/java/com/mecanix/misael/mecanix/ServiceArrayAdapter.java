package com.mecanix.misael.mecanix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MISAEL on 24/11/2015.
 */
public class ServiceArrayAdapter extends ArrayAdapter<Servicio> implements Filterable {

    ImageView image_serv;
    private List<Servicio> serviceItemsArray;
    private List<Servicio> filteredServiceItemsArray;
    private ServiceFilter filter;

    public ServiceArrayAdapter(Context context, List<Servicio> objects) {
        super(context, 0, objects);
        this.serviceItemsArray = new ArrayList<Servicio>();
        serviceItemsArray.addAll(objects);
        this.filteredServiceItemsArray = new ArrayList<Servicio>();
        filteredServiceItemsArray.addAll(serviceItemsArray);
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ServiceFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_service,parent,false);
        }

        image_serv = (ImageView) view.findViewById(R.id.icon_service);
        TextView text_ids = (TextView)view.findViewById(R.id.text_id_service);
        TextView text_descripcion = (TextView)view.findViewById(R.id.text_descripcion);

        Servicio servicio = getItem(position);

        text_ids.setText(Integer.toString(servicio.getId()));
        text_descripcion.setText(servicio.getDescripcion());

        setImageItemService();

        return view;
    }

    protected class ServiceFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0){
                ArrayList<Servicio> filteredItems = new ArrayList<Servicio>();

                for(int i = 0 ,l = serviceItemsArray.size(); i < l; i++){
                    Servicio ser = serviceItemsArray.get(i);
                    if((Integer.toString(ser.getId()).contains(constraint)) || (ser.getDescripcion().contains(constraint))){
                        filteredItems.add(ser);
                    }
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            }
            else{
                synchronized (this){
                    results.values = serviceItemsArray;
                    results.count = serviceItemsArray.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredServiceItemsArray = (ArrayList<Servicio>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filteredServiceItemsArray.size(); i < l; i++){
                add(filteredServiceItemsArray.get(i));
            }
            notifyDataSetInvalidated();
        }
    }

    public void setImageItemService(){

        Random r = new Random();

        int number = (r.nextInt(10) + 1);

        switch(number){

            case 1:
                image_serv.setImageResource(R.drawable.ic_mecanix10);
                break;
            case 2:
                image_serv.setImageResource(R.drawable.ic_mecanix40);
                break;
            case 3:
                image_serv.setImageResource(R.drawable.ic_mecanix_1);
                break;
            case 4:
                image_serv.setImageResource(R.drawable.ic_mecanix14);
                break;
            case 5:
                image_serv.setImageResource(R.drawable.ic_mecanix29);
                break;
            case 6:
                image_serv.setImageResource(R.drawable.ic_mecanix37);
                break;
            case 7:
                image_serv.setImageResource(R.drawable.ic_mecanix33);
                break;
            case 8:
                image_serv.setImageResource(R.drawable.ic_mecanix16);
                break;
            case 9:
                image_serv.setImageResource(R.drawable.ic_mecanix31);
                break;
            case 10:
                image_serv.setImageResource(R.drawable.ic_mecanix12);
                break;
            default:
                break;
        }
    }
}

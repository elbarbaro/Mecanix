package com.mecanix.misael.mecanix;

/**
 * Created by MISAEL on 25/11/2015.
 */
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
public class ValeArrayAdapter extends ArrayAdapter<Vale> implements Filterable {

    ImageView image_vale;
    private List<Vale> valeItemsArray;
    private List<Vale> filteredItemsArray;
    private ValeFilter filter;

    public ValeArrayAdapter(Context context, List<Vale> objects) {
        super(context, 0, objects);
        this.valeItemsArray = new ArrayList<Vale>();
        valeItemsArray.addAll(objects);
        this.filteredItemsArray = new ArrayList<Vale>();
        filteredItemsArray.addAll(valeItemsArray);
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new ValeFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_vale,parent,false);
        }

        image_vale = (ImageView) view.findViewById(R.id.icon_vale);
        TextView text_idv = (TextView)view.findViewById(R.id.text_id_vale);
        TextView text_foliov = (TextView)view.findViewById(R.id.text_foliov);
        TextView text_tiempoe = (TextView) view.findViewById(R.id.text_tiempoe);

        Vale vale = getItem(position);

        text_idv.setText(Integer.toString(vale.getNumvale()));
        text_foliov.setText(Integer.toString(vale.getFolio_orden()));
        text_tiempoe.setText(vale.getTiempo_emision());

        setImageItemVale();

        return view;
    }

    protected class ValeFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0){
                ArrayList<Vale> filteredItems = new ArrayList<Vale>();

                for(int i = 0 ,l = valeItemsArray.size(); i < l; i++){
                    Vale vale = valeItemsArray.get(i);
                    if(Integer.toString(vale.getNumvale()).contains(constraint)){
                        filteredItems.add(vale);
                    }
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            }
            else{
                synchronized (this){
                    results.values = valeItemsArray;
                    results.count = valeItemsArray.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemsArray = (ArrayList<Vale>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filteredItemsArray.size(); i < l; i++){
                add(filteredItemsArray.get(i));
            }
            notifyDataSetInvalidated();
        }
    }

    public void setImageItemVale(){

        Random r = new Random();

        int number = (r.nextInt(5) + 1);

        switch(number){

            case 1:
                image_vale.setImageResource(R.drawable.t1);
                break;
            case 2:
                image_vale.setImageResource(R.drawable.t2);
                break;
            case 3:
                image_vale.setImageResource(R.drawable.t3);
                break;
            case 4:
                image_vale.setImageResource(R.drawable.t4);
                break;
            case 5:
                image_vale.setImageResource(R.drawable.t5);
                break;
            default:
                break;
        }
    }
}


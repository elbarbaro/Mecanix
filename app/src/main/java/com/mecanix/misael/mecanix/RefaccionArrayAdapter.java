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

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MISAEL on 24/11/2015.
 */
public class RefaccionArrayAdapter extends ArrayAdapter<Refaccion> implements Filterable {

    ImageView image_refaccion;
    private List<Refaccion> refaItemsArray;
    private List<Refaccion> filteredItemsArray;
    private ValeFilter filter;

    public RefaccionArrayAdapter(Context context, List<Refaccion> objects) {
        super(context, 0, objects);
        this.refaItemsArray = new ArrayList<Refaccion>();
        refaItemsArray.addAll(objects);
        this.filteredItemsArray = new ArrayList<Refaccion>();
        filteredItemsArray.addAll(refaItemsArray);
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

        image_refaccion = (ImageView) view.findViewById(R.id.icon_vale);
        TextView text_idv = (TextView)view.findViewById(R.id.text_id_vale);
        TextView text_foliov = (TextView)view.findViewById(R.id.text_foliov);
        TextView text_tiempoe = (TextView) view.findViewById(R.id.text_tiempoe);

        Refaccion refa = getItem(position);

        text_idv.setText(Integer.toString(refa.getIdarticulo()));
        text_foliov.setText(refa.getDescripcion());
        text_tiempoe.setText(Integer.toString(refa.getExistencia()));

        setImageItemRefa();

        return view;
    }

    protected class ValeFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0){
                ArrayList<Refaccion> filteredItems = new ArrayList<Refaccion>();

                for(int i = 0 ,l = refaItemsArray.size(); i < l; i++){
                    Refaccion refaccion = refaItemsArray.get(i);
                    if(Integer.toString(refaccion.getIdarticulo()).contains(constraint)){
                        filteredItems.add(refaccion);
                    }
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            }
            else{
                synchronized (this){
                    results.values = refaItemsArray;
                    results.count = refaItemsArray.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemsArray = (ArrayList<Refaccion>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filteredItemsArray.size(); i < l; i++){
                add(filteredItemsArray.get(i));
            }
            notifyDataSetInvalidated();
        }
    }

    public void setImageItemRefa(){

        Random r = new Random();

        int number = (r.nextInt(5) + 1);

        switch(number){

            case 1:
                image_refaccion.setImageResource(R.drawable.r1);
                break;
            case 2:
                image_refaccion.setImageResource(R.drawable.r2);
                break;
            case 3:
                image_refaccion.setImageResource(R.drawable.r4);
                break;
            case 4:
                image_refaccion.setImageResource(R.drawable.r5);
                break;
            case 5:
                image_refaccion.setImageResource(R.drawable.r3);
                break;
            default:
                break;
        }
    }
}


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
 * Created by MISAEL on 20/11/2015.
 */
public class OrdenArrayAdapter extends ArrayAdapter<Orden> implements Filterable{

    private List<Orden> ordenItemsArray;
    private List<Orden> filteredOrdenItemsArray;
    private OrdenFilter filter;

    ImageView imageOrden;
    TextView  textFolio;
    TextView textNomcliente;
    TextView textFechaOrden;
    TextView textEstus;

    public OrdenArrayAdapter(Context context, List<Orden> objects) {
        super(context, 0, objects);
        this.ordenItemsArray = new ArrayList<Orden>();
        ordenItemsArray.addAll(objects);
        this.filteredOrdenItemsArray = new ArrayList<Orden>();
        filteredOrdenItemsArray.addAll(ordenItemsArray);
        getFilter();
    }

    @Override
    public Filter getFilter() {
        if(filter == null ){
            filter = new OrdenFilter();
        }
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;

        if(listItemView == null){

            listItemView = layoutInflater.inflate(R.layout.item_activity_orden,parent,false);
        }

        imageOrden = (ImageView)listItemView.findViewById(R.id.icon_orden);
        textFolio = (TextView) listItemView.findViewById(R.id.folio_orden);
        textNomcliente = (TextView)listItemView.findViewById(R.id.nombre_cliente);
        textFechaOrden = (TextView)listItemView.findViewById(R.id.fecha_orden);
        textEstus = (TextView)listItemView.findViewById(R.id.estatus_orden);

        Orden item = getItem(position);

        textFolio.setText(Integer.toString(item.getFolio()));
        textNomcliente.setText(item.getNombreCliente());
        textFechaOrden.setText(item.getFecha_entrada());

        setItemEstatusText(item.getEstatus());

        setImageItemOrden();

        return listItemView;
    }

    protected class OrdenFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0){
                ArrayList<Orden> filteredItems = new ArrayList<Orden>();

                for(int i = 0, l = ordenItemsArray.size(); i < l; i++ ){

                    Orden ord = ordenItemsArray.get(i);
                    if((Integer.toString(ord.getFolio()).equals(constraint.toString())) || //compara si el elemento esta dentro de la lista
                            (ord.getNombreCliente().equals(constraint.toString()))){ // y lo asigna a la lista filtrada
                        filteredItems.add(ord);
                    }
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            }
            else{
                synchronized (this){
                    results.values = ordenItemsArray;
                    results.count = ordenItemsArray.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filteredOrdenItemsArray = (ArrayList<Orden>) results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = filteredOrdenItemsArray.size(); i < l; i++){
                add(filteredOrdenItemsArray.get(i));
            }
            notifyDataSetInvalidated();
        }
    }

    public void setItemEstatusText(int estatus){

        switch(estatus){
            case 1:
                textEstus.setText("Pendiente");
                break;
            case 2:
                textEstus.setText("Proceso");
                break;
            case 3:
                textEstus.setText("Control de Calidad");
                break;
            case 4:
                textEstus.setText("Terminado");
                break;
            case 5:
                textEstus.setText("Entregado");
                break;
            case 6:
                textEstus.setText("Cancelado");
                break;
            default:
                break;
        }
    }

    public void setImageItemOrden(){

        Random r = new Random();

        int number = (r.nextInt(10) + 1);

        switch(number){

            case 1:
                imageOrden.setImageResource(R.drawable.ic_mecanix10);
                break;
            case 2:
                imageOrden.setImageResource(R.drawable.ic_mecanix40);
                break;
            case 3:
                imageOrden.setImageResource(R.drawable.ic_mecanix_1);
                break;
            case 4:
                imageOrden.setImageResource(R.drawable.ic_mecanix14);
                break;
            case 5:
                imageOrden.setImageResource(R.drawable.ic_mecanix29);
                break;
            case 6:
                imageOrden.setImageResource(R.drawable.ic_mecanix37);
                break;
            case 7:
                imageOrden.setImageResource(R.drawable.ic_mecanix33);
                break;
            case 8:
                imageOrden.setImageResource(R.drawable.ic_mecanix16);
                break;
            case 9:
                imageOrden.setImageResource(R.drawable.ic_mecanix31);
                break;
            case 10:
                imageOrden.setImageResource(R.drawable.ic_mecanix12);
                break;
            default:
                break;
        }
    }
}

package Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofertasapp.Oferta;
import com.example.ofertasapp.R;

import java.util.List;

/**
 * Adaptador para Listview
 */
public class ListViewAdapterOferta extends ArrayAdapter<Oferta> {

    private List<Oferta> ofertaList;
    private Context mCtx;

    public ListViewAdapterOferta(List<Oferta> ofertaList, Context mCtx){
        super(mCtx, R.layout.list_items, ofertaList);
        this.ofertaList = ofertaList;
        this.mCtx = mCtx;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // obteniendo el layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        // creando una vista con nuestro diseño xml
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //asociamos layout
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = listViewItem.findViewById(R.id.textViewDesc);

        // Obteniendo las ofertas para la posición especificada
        Oferta oferta = ofertaList.get(position);

        //establecemos los valores de ofertas en textViews
        textViewName.setText(oferta.getDescripcion());  //añadimos el nombre
        textViewDesc.setText(oferta.getNombre()); //añadimos la descripcion

        //devolvemos la lista de items
        return listViewItem;
    }
}
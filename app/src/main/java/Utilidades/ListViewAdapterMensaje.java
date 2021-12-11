package Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofertasapp.Mensaje;
import com.example.ofertasapp.R;

import java.util.List;

/**
 * Adaptador para Listview
 */
public class ListViewAdapterMensaje extends ArrayAdapter<Mensaje> {

    private List<Mensaje> mensajeList;
    private Context mCtx;

    public ListViewAdapterMensaje(List<Mensaje> mensajeList, Context mCtx){
        super(mCtx, R.layout.list_items, mensajeList);
        this.mensajeList = mensajeList;
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

        // Obteniendo los mensajes para la posición especificada
        Mensaje mensaje = mensajeList.get(position);

        //establecemos los valores de mensajes en textViews
        textViewName.setText(mensaje.getTitulo());  //añadimos el titulo
        textViewDesc.setText(mensaje.getContenido());  //añadimos el contenido

        //devolvemos la lista de items
        return listViewItem;
    }
}

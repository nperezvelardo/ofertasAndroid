package Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofertasapp.Log;
import com.example.ofertasapp.R;

import java.util.List;

/**
 * Adaptador para Listview
 */
public class ListViewAdapterLog extends ArrayAdapter<Log> {
    private List<Log> logList;
    private Context mCtx;

    public ListViewAdapterLog(List<Log> logList, Context mCtx){
        super(mCtx, R.layout.list_items, logList);
        this.logList = logList;
        this.mCtx = mCtx;

    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // obteniendo el layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        // creando una vista con nuestro diseño xml
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //asociamos layout
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = listViewItem.findViewById(R.id.textViewDesc);

        // Obteniendo los logs para la posición especificada
        Log log = logList.get(position);

        //establecemos los valores de logs en textViews
        textViewName.setText(log.getUsuario());  //añadimos el usuario
        textViewDesc.setText(log.getAccion()); //añadimos la accion

        //devolvemos la lista de items
        return listViewItem;
    }
}

package Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofertasapp.R;
import com.example.ofertasapp.Usuario;

import java.util.List;

public class ListViewAdapterActivar extends ArrayAdapter<Usuario> implements View.OnClickListener {
private List<Usuario> usuarioList;
private Context mCtx;
        private View.OnClickListener listener;

public ListViewAdapterActivar(List<Usuario> usuarioList, Context mCtx){
        super(mCtx, R.layout.list_items2, usuarioList);
        this.usuarioList = usuarioList;
        this.mCtx = mCtx;

        }

@NonNull
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // obteniendo el layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        // creando una vista con nuestro diseño xml
        View listViewItem = inflater.inflate(R.layout.list_items2, null, true);

        //asociamos layout
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = listViewItem.findViewById(R.id.textViewDesc);

        // Obteniendo los logs para la posición especificada
        Usuario user = usuarioList.get(position);

        //establecemos los valores de logs en textViews
        textViewName.setText(user.getNombre());  //añadimos el usuario
        textViewDesc.setText(user.getEmail()); //añadimos la accion

        //devolvemos la lista de items
        return listViewItem;
        }

        public void setOnClickListener(View.OnClickListener listener){
                this.listener = listener;
        }

        @Override
        public void onClick(View v) {
                if(listener != null){
                        listener.onClick(v);
                }
        }
}

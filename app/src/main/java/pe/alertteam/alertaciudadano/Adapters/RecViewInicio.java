package pe.alertteam.alertaciudadano.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import pe.alertteam.alertaciudadano.R;

public class RecViewInicio extends RecyclerView.Adapter<RecViewInicio.ViewHolder> {

    Context context;
    List<DocumentSnapshot> alertas;

    public RecViewInicio(Context context, List<DocumentSnapshot> alertas) {
        this.alertas = alertas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DocumentSnapshot alerta = alertas.get(position);
        holder.list_desc.setText(alerta.getString("descripcion"));
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView timestamp;
        public TextView list_desc;
        public CardView cardview;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inicio_item, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.image_photo);
            timestamp = (TextView) itemView.findViewById(R.id.list_timestamp);
            list_desc = (TextView) itemView.findViewById(R.id.list_desc);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}

package pe.alertteam.alertaciudadano.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import pe.alertteam.alertaciudadano.R;

public class RecViewMisAlertas extends RecyclerView.Adapter<RecViewMisAlertas.ViewHolder> {

    Context context;
    List<DocumentSnapshot> alertas;

    public RecViewMisAlertas(Context context, List<DocumentSnapshot> alertas) {
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
        String str_enlace = alerta.getString("imagen");
        if(str_enlace!=null){
            Picasso.get().load(str_enlace).into(holder.picture);
        }
        String str_enlace_avatar = alerta.getString("fotoUsuario");
        if(str_enlace_avatar!=null){
            Picasso.get().load(str_enlace_avatar).into(holder.avatar);
        }
        else{
            Picasso.get().load(R.drawable.avatar).into(holder.avatar);
        }
        holder.username.setText(alerta.getString("nombreUsuario"));
        holder.timestamp.setText(alerta.getTimestamp("creado").toDate().toString());
        holder.list_desc.setText(alerta.getString("descripcion"));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return alertas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public ImageView picture;
        public TextView username;
        public TextView timestamp;
        public TextView list_desc;
        public CardView cardview;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inicio_item, parent, false));
            avatar = (ImageView) itemView.findViewById(R.id.card_avatar);
            picture = (ImageView) itemView.findViewById(R.id.card_image_photo);
            username = (TextView)  itemView.findViewById(R.id.card_username);
            timestamp = (TextView) itemView.findViewById(R.id.card_timestamp);
            list_desc = (TextView) itemView.findViewById(R.id.card_desc);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}

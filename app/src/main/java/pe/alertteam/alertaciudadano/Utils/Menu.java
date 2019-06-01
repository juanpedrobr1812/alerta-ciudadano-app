package pe.alertteam.alertaciudadano.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Map;

import javax.annotation.Nullable;

import pe.alertteam.alertaciudadano.AlertaSOSActivity;
import pe.alertteam.alertaciudadano.GruposActivity;
import pe.alertteam.alertaciudadano.InicioActivity;
import pe.alertteam.alertaciudadano.MainActivity;
import pe.alertteam.alertaciudadano.MisAlertasActivity;
import pe.alertteam.alertaciudadano.PerfilActivity;
import pe.alertteam.alertaciudadano.R;

public class Menu {

    private FirebaseUser usuario;
    private FirebaseFirestore db;
    private static final String TAG = "Menu";

    public Menu(final NavigationView navigationView) {
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("usuario", usuario.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                final Map<String, Object> info = document.getData();
                                final TextView tvTitulo = navigationView.getHeaderView(0).findViewById(R.id.txt_titulo);
                                TextView tvSub = navigationView.getHeaderView(0).findViewById(R.id.txt_subtitulo);
                                String nombrecompleto = info.get("nombres").toString().toUpperCase().split(" ")[0] + " " + info.get("apellidos").toString().toUpperCase().split(" ")[0];
                                if(nombrecompleto.trim().length()>20){
                                    tvTitulo.setText(nombrecompleto.substring(20));
                                }
                                else{
                                    tvTitulo.setText(nombrecompleto);
                                }
                                if(usuario.getPhotoUrl() != null){
                                    Picasso.get().load(usuario.getPhotoUrl()).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            Drawable drawableImage = new BitmapDrawable(navigationView.getResources(), bitmap);
                                            tvTitulo.setCompoundDrawablesWithIntrinsicBounds(drawableImage, null, null, null);
                                        }

                                        @Override
                                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                                        }
                                    });
                                }
                                tvSub.setText(usuario.getEmail());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void seleccionarOpcionMainMenu(Activity activity, MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()){
            case R.id.inicio:
                i = new Intent(activity, InicioActivity.class);
                activity.startActivity(i);
                break;
            case R.id.reportes:
                i = new Intent(activity, MisAlertasActivity.class);
                activity.startActivity(i);
                break;
            case R.id.grupos:
                i = new Intent(activity, GruposActivity.class);
                activity.startActivity(i);
                break;
            case R.id.sos:
                i = new Intent(activity, AlertaSOSActivity.class);
                activity.startActivity(i);
                break;
            case R.id.perfil:
                i = new Intent(activity, PerfilActivity.class);
                activity.startActivity(i);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                i = new Intent(activity, MainActivity.class);
                activity.startActivity(i);
                break;
            default:
                break;
        }
        activity.finish();
    }
}

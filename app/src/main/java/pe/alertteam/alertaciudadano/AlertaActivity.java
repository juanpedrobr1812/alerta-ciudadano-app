package pe.alertteam.alertaciudadano;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.model.value.ServerTimestampValue;
import com.google.firestore.v1beta1.DocumentTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class AlertaActivity extends AppCompatActivity {
    EditText txtTitulo;
    EditText txtDescripcion;
    //EditText txtUbigeo;
    Button btnAlertar;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        //txtUbigeo = (EditText) findViewById(R.id.txtUbigeo);
        btnAlertar = (Button) findViewById(R.id.btnAlertar);
        btnAlertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //@ServerTimestamp Date time;
                FirebaseUser usuario = mAuth.getCurrentUser();
//                usuario = FirebaseAuth.getInstance().getCurrentUser();
//                db = FirebaseFirestore.getInstance();
//                db.collection("users").whereEqualTo("usuario", usuario.getUid())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        final Map<String, Object> info = document.getData();
//                                        //final TextView tvTitulo = navigationView.getHeaderView(0).findViewById(R.id.txt_titulo);
////                                       TextView tvSub = navigationView.getHeaderView(0).findViewById(R.id.txt_subtitulo);
//                                        nombrecompleto = info.get("nombres").toString().toUpperCase().split(" ")[0] + " " + info.get("apellidos").toString().toUpperCase().split(" ")[0];
//                                    }
//                                } else {
////                                    Log.w(TAG, "Error getting documents.", task.getException());
//                                }
//                            }
//                        });
                Map<String, Object> perfil = new HashMap<>();
                perfil.put("anonimo", true);
                perfil.put("codigo", "TFGHUD");
                perfil.put("creado", FieldValue.serverTimestamp());
                perfil.put("descripcion", txtDescripcion.getText().toString().trim());
                perfil.put("estado", "pendiente");
                perfil.put("idUsuario", usuario.getUid());
                perfil.put("imagen", "https://firebasestorage.googleapis.com/v0/b/alertaciudadano2019.appspot.com/o/storage_reportes%2F722331.jpg?alt=media&token=a5a04edd-97df-4ecc-bf75-348dbe9bdbc3");
                perfil.put("latitud", "12.121221");
                perfil.put("longitud", "13.121221");
                perfil.put("ubigeo", "333333" );
                perfil.put("uidMunicipalidad", "333333");
                perfil.put("nombreUsuario", "test");
                perfil.put("titulo", txtTitulo.getText().toString().trim());
                db.collection("alerta" +
                        "")
                        .add(perfil)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AlertaActivity.this, "Se envió tu alerta..", Toast.LENGTH_SHORT).show();
                                Log.d("Regitro", "DocumentSnapshot added with ID: " + documentReference.getId());
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Regitro", "Error adding document", e);
                            }
                        });
//                mAuth.createUserWithEmailAndPassword(edtCorreo.getText().toString().trim(), edtPassword.getText().toString().trim())
//                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Map<String, Object> perfil = new HashMap<>();
//                                    perfil.put("usuario", user.getUid());
//                                    perfil.put("nombres", edtNombres.getText().toString().trim());
//                                    perfil.put("apellidos", edtApellidos.getText().toString().trim());
//                                    perfil.put("dni", edtNumDocumento.getText().toString().trim());
//                                    perfil.put("ubigeo", edtUbigeo.getText().toString().trim());
//                                    perfil.put("direccion", edtDireccion.getText().toString().trim());
//
//                                    db.collection("users")
//                                            .add(user)
//                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                                @Override
//                                                public void onSuccess(DocumentReference documentReference) {
//                                                    Toast.makeText(AlertaActivity.this, "Se creo el usuario correctaente.", Toast.LENGTH_SHORT).show();
//                                                    Log.d("Regitro", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                                    finish();
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Log.w("Regitro", "Error adding document", e);
//                                                }
//                                            });
//
//                                } else {
//                                    Log.w("Regitro", "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(AlertaActivity.this, "No se registro.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });
    }
}
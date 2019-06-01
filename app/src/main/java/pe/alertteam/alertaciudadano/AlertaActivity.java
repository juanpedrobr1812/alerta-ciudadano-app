package pe.alertteam.alertaciudadano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AlertaActivity extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDescripcion;
    EditText txtUbigeo;
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
        txtUbigeo = (EditText) findViewById(R.id.txtUbigeo);
        btnAlertar = (Button) findViewById(R.id.btnAlertar);

        btnAlertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser user = mAuth.getCurrentUser();
                Map<String, Object> perfil = new HashMap<>();
                perfil.put("idUsuario", user.getUid());
                perfil.put("titulo", txtTitulo.getText().toString().trim());
                perfil.put("descripcion", txtDescripcion.getText().toString().trim());
                perfil.put("codigo", "TFGHUD");
                perfil.put("creado", new Date().toString());
                perfil.put("latitud", "12.121221");
                perfil.put("longitud", "13.121221");
                perfil.put("ubigeo", txtUbigeo.getText().toString().trim());
                perfil.put("uidMunicipalidad", "741852");

                db.collection("users")
                .add(user)
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

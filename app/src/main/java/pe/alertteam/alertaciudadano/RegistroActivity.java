package pe.alertteam.alertaciudadano;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    EditText edtCorreo;
    EditText edtPassword;
    EditText edtNombres;
    EditText edtApellidos;
    EditText edtNumDocumento;
    EditText edtUbigeo;
    EditText edtDireccion;
    Button btnRegistrar;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtCorreo = (EditText) findViewById(R.id.editText);
        edtPassword = (EditText) findViewById(R.id.editText2);
        edtNombres = (EditText) findViewById(R.id.editText3);
        edtApellidos = (EditText) findViewById(R.id.editText4);
        edtNumDocumento = (EditText) findViewById(R.id.editText5);
        edtUbigeo = (EditText) findViewById(R.id.editText6);
        edtDireccion = (EditText) findViewById(R.id.editText7);
        btnRegistrar = (Button) findViewById(R.id.button);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(edtCorreo.getText().toString().trim(), edtPassword.getText().toString().trim())
                        .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Map<String, Object> perfil = new HashMap<>();
                                    perfil.put("usuario", user.getUid());
                                    perfil.put("nombres", edtNombres.getText().toString().trim());
                                    perfil.put("apellidos", edtApellidos.getText().toString().trim());
                                    perfil.put("dni", edtNumDocumento.getText().toString().trim());
                                    perfil.put("ubigeo", edtUbigeo.getText().toString().trim());
                                    perfil.put("direccion", edtDireccion.getText().toString().trim());

                                    db.collection("users")
                                            .add(perfil)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(RegistroActivity.this, "Se creo el usuario correctaente.", Toast.LENGTH_SHORT).show();
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

                                } else {
                                    Log.w("Regitro", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegistroActivity.this, "No se registro.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(this, RegistroActivity.class);
            startActivity(i);
            finish();
        }
    }
}

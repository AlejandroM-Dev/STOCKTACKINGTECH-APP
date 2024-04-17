package com.example.stocktakingtech.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Distribuidores extends AppCompatActivity {

    EditText txtNombreDistribuidor, txtIdentificacionDistribuidor, txtTipoPersonaDistribuidor, txtDireccionDistribuidor, activo;
    CheckBox chkActivoDistribuidor;
    Button btnGuardarDistribuidor, btnEliminarDistribuidor, btnVolverDistribuidorALstDistribuidor;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribuidores);
        Bundle extras = getIntent().getExtras();

        Id = "";

        txtNombreDistribuidor = findViewById(R.id.txtNombreDistribuidor);
        txtIdentificacionDistribuidor = findViewById(R.id.txtIdentificacionDistribuidor);
        txtTipoPersonaDistribuidor = findViewById(R.id.txtTipoPersonaDistribuidor);
        txtDireccionDistribuidor = findViewById(R.id.txtDireccionDistribuidor);
        chkActivoDistribuidor = findViewById(R.id.chkActivoDistribuidor);

        btnGuardarDistribuidor = findViewById(R.id.btnGuardarDistribuidor);
        btnEliminarDistribuidor = findViewById(R.id.btnEliminarDistribuidor);
        btnVolverDistribuidorALstDistribuidor = findViewById(R.id.btnVolverDistribuidorALstDistribuidor);
        inicializarFirebase();

        if(extras != null){
            Id = extras.getString("IdD");
            txtNombreDistribuidor.setText(extras.getString("nombreD"));
            txtIdentificacionDistribuidor.setText(extras.getString("identificionD"));
            txtTipoPersonaDistribuidor.setText(extras.getString("tipoPersonaD"));
            txtDireccionDistribuidor.setText(extras.getString("direccionD"));
            chkActivoDistribuidor.setText(extras.getString("activoD"));

            String activo = chkActivoDistribuidor.getText().toString();
            if (activo.equals("Activo")){
                chkActivoDistribuidor.setChecked(true);
            }else{
                chkActivoDistribuidor.setChecked(false);
            }

            btnEliminarDistribuidor.setVisibility(View.VISIBLE);
        }

        btnVolverDistribuidorALstDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Distribuidores.this, LstDistribuidor.class);
                startActivity(i);
            }
        });

        btnGuardarDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNombreDistribuidor.getText().toString() == "" || txtIdentificacionDistribuidor.getText().toString().isEmpty() || txtTipoPersonaDistribuidor.getText().toString().isEmpty() || txtDireccionDistribuidor.getText().toString().isEmpty() ){
                    Toast.makeText(Distribuidores.this, "ERRROR CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                    return;
                }
                String nombreDistri = txtNombreDistribuidor.getText().toString();
                String identificacionDistri = txtIdentificacionDistribuidor.getText().toString();
                String tipoPersonaDistri = txtTipoPersonaDistribuidor.getText().toString();
                String direccionDistri = txtDireccionDistribuidor.getText().toString();
                String activo = "";
                if (chkActivoDistribuidor.isChecked()){
                    activo = "Activo";
                }else{
                    activo = "Inactivo";
                }

                DistribuidorModel d = new DistribuidorModel();
                d.setNOMBRE_DISTRIBUIDOR(nombreDistri);
                d.setIDENTIFICACION_DISTRIBUIDOR(identificacionDistri);
                d.setTIPO_PERSONA(tipoPersonaDistri);
                d.setDIRECCION_DISTRIBUIDOR(direccionDistri);
                d.setACTIVO_DISTRIBUIDOR(activo);

                if (Id == ""){
                    d.setCOD_DISTRIBUIDOR(UUID.randomUUID().toString());
                    Toast.makeText(Distribuidores.this, "llego id solo", Toast.LENGTH_LONG).show();
                    databaseReference.child("Distribuidor").child(d.getCOD_DISTRIBUIDOR()).setValue(d);
                    Toast.makeText(Distribuidores.this, "DISTRIBUIDOR GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Distribuidores.this, LstDistribuidor.class);
                    startActivity(intent);
                    finish();
                }else{
                    d.setCOD_DISTRIBUIDOR(Id);
                    Toast.makeText(Distribuidores.this, "llego", Toast.LENGTH_LONG).show();
                    databaseReference.child("Distribuidor").child(d.getCOD_DISTRIBUIDOR()).setValue(d);
                    Toast.makeText(Distribuidores.this, "PRODUCTO ACTUALIZADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Distribuidores.this, LstDistribuidor.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnEliminarDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DistribuidorModel d = new DistribuidorModel();
                d.setCOD_DISTRIBUIDOR(Id);
                databaseReference.child("Distribuidor").child(d.getCOD_DISTRIBUIDOR()).removeValue();
                Toast.makeText(Distribuidores.this, "DISTRIBUIDOR ELIMINADO", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Distribuidores.this, LstDistribuidor.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void limpiar (){
        txtNombreDistribuidor.setText("");
        txtIdentificacionDistribuidor.setText("");
        txtTipoPersonaDistribuidor.setText("");
        txtDireccionDistribuidor.setText("");
        chkActivoDistribuidor.setChecked(false);
    }
}
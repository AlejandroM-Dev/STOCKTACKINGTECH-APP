package com.example.stocktakingtech.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.Model.RolesModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Roles extends AppCompatActivity {

    EditText txtNombreRol;
    CheckBox chkCrearRol, chkEditarRol, chkAnularRol, chkConsultarRol, chkActivoRol;
    Button btnGuardarRol, btnEliminarRol, btnVolverRolesALstRoles;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);

        Bundle extras = getIntent().getExtras();

        Id = "";

        txtNombreRol = findViewById(R.id.txtNombreRol);
        chkCrearRol = findViewById(R.id.chkCrearRol);
        chkEditarRol = findViewById(R.id.chkEditarRol);
        chkAnularRol = findViewById(R.id.chkAnularRol);
        chkConsultarRol = findViewById(R.id.chkConsultarRol);
        chkActivoRol = findViewById(R.id.chkActivoRol);

        btnGuardarRol = findViewById(R.id.btnGuardarRol);
        btnEliminarRol = findViewById(R.id.btnEliminarRol);
        btnVolverRolesALstRoles = findViewById(R.id.btnVolverRolesALstRoles);
        inicializarFirebase();

        if(extras != null){
            Id = extras.getString("IdR");
            txtNombreRol.setText(extras.getString("nombreR"));
            String crear = extras.getString("crearR");
            String editar = extras.getString("editarR");
            String anular = extras.getString("anularR");
            String consultar = extras.getString("consultarR");
            chkActivoRol.setText(extras.getString("activoR"));

            if (crear.equals("Activo")){
                chkCrearRol.setChecked(true);
            }else{
                chkCrearRol.setChecked(false);
            }

            if (editar.equals("Activo")){
                chkEditarRol.setChecked(true);
            }else{
                chkEditarRol.setChecked(false);
            }

            if (anular.equals("Activo")){
                chkAnularRol.setChecked(true);
            }else{
                chkAnularRol.setChecked(false);
            }

            if (consultar.equals("Activo")){
                chkConsultarRol.setChecked(true);
            }else{
                chkConsultarRol.setChecked(false);
            }

            String activo = chkActivoRol.getText().toString();
            if (activo.equals("Activo")){
                chkActivoRol.setChecked(true);
            }else{
                chkActivoRol.setChecked(false);
            }

            btnEliminarRol.setVisibility(View.VISIBLE);
        }

        btnVolverRolesALstRoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Roles.this, LstRoles.class);
                startActivity(i);
            }
        });

        btnGuardarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNombreRol.getText().toString() == ""){
                    Toast.makeText(Roles.this, "ERRROR CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                    return;
                }
                String nombreRol = txtNombreRol.getText().toString();
                String crear = "";
                if (chkCrearRol.isChecked()){
                    crear = "Activo";
                }else{
                    crear = "Inactivo";
                }

                String editar = "";
                if (chkEditarRol.isChecked()){
                    editar = "Activo";
                }else{
                    editar = "Inactivo";
                }

                String anular = "";
                if (chkAnularRol.isChecked()){
                    anular = "Activo";
                }else{
                    anular = "Inactivo";
                }

                String consultar = "";
                if (chkConsultarRol.isChecked()){
                    consultar = "Activo";
                }else{
                    consultar = "Inactivo";
                }

                String activo = "";
                if (chkActivoRol.isChecked()){
                    activo = "Activo";
                }else{
                    activo = "Inactivo";
                }

                RolesModel r = new RolesModel();
                r.setNOMBRE_ROL(nombreRol);
                r.setCREAR(crear);
                r.setEDITAR(editar);
                r.setANULAR(anular);
                r.setCONSULTAR(consultar);
                r.setACTIVO(activo);

                if (Id == ""){
                    r.setID_ROL(UUID.randomUUID().toString());
                    databaseReference.child("Rol").child(r.getID_ROL()).setValue(r);
                    Toast.makeText(Roles.this, "ROL GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Roles.this, LstRoles.class);
                    startActivity(intent);
                    finish();
                }else{
                    r.setID_ROL(Id);
                    databaseReference.child("Rol").child(r.getID_ROL()).setValue(r);
                    Toast.makeText(Roles.this, "ROL ACTUALIZADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Roles.this, LstRoles.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnEliminarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RolesModel r = new RolesModel();
                r.setID_ROL(Id);
                databaseReference.child("Rol").child(r.getID_ROL()).removeValue();
                Toast.makeText(Roles.this, "ROL ELIMINADO", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Roles.this, LstRoles.class);
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
        txtNombreRol.setText("");
        chkCrearRol.setChecked(false);
        chkEditarRol.setChecked(false);
        chkAnularRol.setChecked(false);
        chkConsultarRol.setChecked(false);
        chkActivoRol.setChecked(false);
    }

}
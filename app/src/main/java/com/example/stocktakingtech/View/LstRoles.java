package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.Model.RolesModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LstRoles extends AppCompatActivity {

    private List<RolesModel> listRol = new ArrayList<RolesModel>();
    ArrayAdapter<RolesModel> arrayAdapterRol;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listV_roles;
    RolesModel rolSelected;

    Button btnVolverLstRolesAInicio, btnNuevoRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_roles);

        listV_roles =  findViewById(R.id.lv_datosRoles);

        inicializarFirebase();

        btnVolverLstRolesAInicio = findViewById(R.id.btnVolverLstRolesAInicio);
        btnNuevoRol = findViewById(R.id.btnNuevoRol);

        btnVolverLstRolesAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstRoles.this, Inicio.class);
                startActivity(i);
            }
        });
        btnNuevoRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstRoles.this, Roles.class);
                startActivity(i);
            }
        });

        listarDatos();
        listV_roles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rolSelected = (RolesModel) adapterView.getItemAtPosition(i);
                String IdR = rolSelected.getID_ROL();
                String nombreR = rolSelected.getNOMBRE_ROL();
                String crearR = rolSelected.getCREAR();
                String editarR = rolSelected.getEDITAR();
                String anularR = rolSelected.getANULAR();
                String consultarR = rolSelected.getCONSULTAR();
                String activoR = rolSelected.getACTIVO();

                Intent intent = new Intent(LstRoles.this, Roles.class);
                intent.putExtra("IdR", IdR);
                intent.putExtra("nombreR", nombreR);
                intent.putExtra("crearR", crearR);
                intent.putExtra("editarR", editarR);
                intent.putExtra("anularR", anularR);
                intent.putExtra("consultarR", consultarR);
                intent.putExtra("activoR", activoR);
                startActivity(intent);
                finish();
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Rol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRol.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    RolesModel r = objSnapshot.getValue(RolesModel.class);
                    listRol.add(r);

                    arrayAdapterRol = new ArrayAdapter<RolesModel>(LstRoles.this, android.R.layout.simple_list_item_1, listRol);
                    listV_roles.setAdapter(arrayAdapterRol);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
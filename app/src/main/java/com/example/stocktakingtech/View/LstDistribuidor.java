package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LstDistribuidor extends AppCompatActivity {

    private List<DistribuidorModel> listDistribuidor = new ArrayList<DistribuidorModel>();
    ArrayAdapter<DistribuidorModel> arrayAdapterDistribuidor;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listV_distribuidor;
    DistribuidorModel distribuidorSelected;

    Button btnVolverLstDistribuidorAInicio, btnNuevoDistribuidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_distribuidor);
        listV_distribuidor =  findViewById(R.id.lv_datosDistribuidores);

        inicializarFirebase();

        btnVolverLstDistribuidorAInicio = findViewById(R.id.btnVolverLstDistribuidorAInicio);
        btnNuevoDistribuidor = findViewById(R.id.btnNuevoDistribuidor);

        btnVolverLstDistribuidorAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstDistribuidor.this, Inicio.class);
                startActivity(i);
            }
        });
        btnNuevoDistribuidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstDistribuidor.this, Distribuidores.class);
                startActivity(i);
            }
        });

        listarDatos();
        listV_distribuidor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                distribuidorSelected = (DistribuidorModel) adapterView.getItemAtPosition(i);
                String IdD = distribuidorSelected.getCOD_DISTRIBUIDOR();
                String nombreD = distribuidorSelected.getNOMBRE_DISTRIBUIDOR();
                String identificionD = distribuidorSelected.getIDENTIFICACION_DISTRIBUIDOR();
                String tipoPersonaD = distribuidorSelected.getTIPO_PERSONA();
                String direccionD = distribuidorSelected.getDIRECCION_DISTRIBUIDOR();
                String activoD = distribuidorSelected.getACTIVO_DISTRIBUIDOR();

                Intent intent = new Intent(LstDistribuidor.this, Distribuidores.class);
                intent.putExtra("IdD", IdD);
                intent.putExtra("nombreD", nombreD);
                intent.putExtra("identificionD", identificionD);
                intent.putExtra("tipoPersonaD", tipoPersonaD);
                intent.putExtra("direccionD", direccionD);
                intent.putExtra("activoD", activoD);
                startActivity(intent);
                finish();
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Distribuidor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDistribuidor.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    DistribuidorModel d = objSnapshot.getValue(DistribuidorModel.class);
                    listDistribuidor.add(d);

                    arrayAdapterDistribuidor = new ArrayAdapter<DistribuidorModel>(LstDistribuidor.this, android.R.layout.simple_list_item_1, listDistribuidor);
                    listV_distribuidor.setAdapter(arrayAdapterDistribuidor);
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
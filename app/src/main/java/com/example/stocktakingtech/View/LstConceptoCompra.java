package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stocktakingtech.Model.SalidaModel;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LstConceptoCompra extends AppCompatActivity {

    String idUser;
    private List<SalidaModel> listSalida = new ArrayList<SalidaModel>();
    ArrayAdapter<SalidaModel> arrayAdapterSalida;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView lv_datosSalidas;
    SalidaModel salidaSelected;

    Button btnVolverLstSalidasAInicio, btnNuevaSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_concepto_compra);
        Bundle extras = getIntent().getExtras();
        idUser = "";
        if(extras != null){
            idUser = getIntent().getExtras().getString("idUsuario");
        }
        lv_datosSalidas =  findViewById(R.id.lv_datosSalidas);

        inicializarFirebase();

        btnVolverLstSalidasAInicio = findViewById(R.id.btnVolverLstSalidasAInicio);
        btnNuevaSalida = findViewById(R.id.btnNuevaSalida);

        btnVolverLstSalidasAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstConceptoCompra.this, Inicio.class);
                startActivity(i);
            }
        });
        btnNuevaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstConceptoCompra.this, DetalleSalida.class);
                i.putExtra("idUser", idUser);
                startActivity(i);
            }
        });

        listarDatos();
        lv_datosSalidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                salidaSelected = (SalidaModel) adapterView.getItemAtPosition(i);
                String IdC = salidaSelected.getCOD_SALIDA();
                String fechaC = salidaSelected.getFECHA_COMPRA();
                String totalC = salidaSelected.getTOTAL_COMPRA();
                String nombreClienteC = salidaSelected.getNOMBRE_COMPRADOR();

                //Intent intent = new Intent(LstConceptoCompra.this, Distribuidores.class);
                //intent.putExtra("IdC", IdC);
                //intent.putExtra("fechaC", fechaC);
                //intent.putExtra("totalC", totalC);
                //intent.putExtra("nombreClienteC", nombreClienteC);
                //startActivity(intent);
                finish();
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Salida").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSalida.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    SalidaModel s = objSnapshot.getValue(SalidaModel.class);
                    listSalida.add(s);

                    arrayAdapterSalida = new ArrayAdapter<SalidaModel>(LstConceptoCompra.this, android.R.layout.simple_list_item_1, listSalida);
                    lv_datosSalidas.setAdapter(arrayAdapterSalida);
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
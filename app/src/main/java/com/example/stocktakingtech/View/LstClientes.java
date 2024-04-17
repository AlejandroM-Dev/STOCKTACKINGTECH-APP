package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.Model.Usuario;
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

public class LstClientes extends AppCompatActivity {

    private List<Usuario> listCliente = new ArrayList<Usuario>();
    ArrayAdapter<Usuario> arrayAdapterCliente;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listV_clientes;
    Usuario clienteSelected;

    Button btnVolverLstClienteAInicio, btnNuevoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_clientes);

        listV_clientes =  findViewById(R.id.lv_datosClientes);

        inicializarFirebase();

        btnVolverLstClienteAInicio = findViewById(R.id.btnVolverLstClienteAInicio);
        btnNuevoCliente = findViewById(R.id.btnNuevoCliente);

        btnVolverLstClienteAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstClientes.this, Inicio.class);
                startActivity(i);
            }
        });
        btnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(LstClientes.this, UsuarioReg.class);
                startActivity(i2);
            }
        });

        listarDatos();
        listV_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clienteSelected = (Usuario) adapterView.getItemAtPosition(i);
                String IdU = clienteSelected.getCOD_USUARIO();
                String nombresU = clienteSelected.getNOMBRE_USUARIO();
                String apellidosU = clienteSelected.getAPELLIDOS_USUARIO();
                String emailU = clienteSelected.getEMAIL();
                String direccionU = clienteSelected.getDIRECCION();
                String telefonoU = clienteSelected.getTELEFONO();
                String contraseñaU = clienteSelected.getCONTRASEÑA();
                String activoU = clienteSelected.getACTIVO();
                String idRolU = clienteSelected.getID_ROL();
                String identificacionU = clienteSelected.getIDENTIFICACION_USUARIO();

                Intent intent = new Intent(LstClientes.this, UsuarioReg.class);
                intent.putExtra("IdU", IdU);
                intent.putExtra("nombresU", nombresU);
                intent.putExtra("apellidosU", apellidosU);
                intent.putExtra("emailU", emailU);
                intent.putExtra("direccionU", direccionU);
                intent.putExtra("telefonoU", telefonoU);
                intent.putExtra("contraseñaU", activoU);
                intent.putExtra("activoU", activoU);
                intent.putExtra("idRolU", idRolU);
                intent.putExtra("identificacionU", identificacionU);
                startActivity(intent);
                finish();
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCliente.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Usuario u = objSnapshot.getValue(Usuario.class);
                    listCliente.add(u);

                    arrayAdapterCliente = new ArrayAdapter<Usuario>(LstClientes.this, android.R.layout.simple_list_item_1, listCliente);
                    listV_clientes.setAdapter(arrayAdapterCliente);
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
package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;

import butterknife.internal.Utils;

public class Productos extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombreProducto, txtValorUnidad, txtCantidadProducto, activo;
    CheckBox chkActivoProducto;
    Button btnGuardarProducto, btnEliminarProducto, btnVolverProductoALstProducto;
    Spinner spinnerDistribuidores;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String Id, DistribuidorSelected, NombreDistribuidorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Bundle extras = getIntent().getExtras();

        Id = "";
        DistribuidorSelected = "";
        NombreDistribuidorSelected = "";

        txtNombreProducto = findViewById(R.id.txtNombreProducto);
        txtValorUnidad = findViewById(R.id.txtValorUnidad);
        txtCantidadProducto = findViewById(R.id.txtCantidadProducto);
        spinnerDistribuidores = findViewById(R.id.spinnerDistribuidores);
        chkActivoProducto = findViewById(R.id.chkActivoProducto);

        btnVolverProductoALstProducto = findViewById(R.id.btnVolverProductoALstProducto);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto);
        inicializarFirebase();


        if(extras != null){
            Id = extras.getString("Id");
            txtNombreProducto.setText(extras.getString("nombreP"));
            txtValorUnidad.setText(extras.getString("valorP"));
            txtCantidadProducto.setText(extras.getString("cantidadP"));
            chkActivoProducto.setText(extras.getString("activoP"));

            String activo = chkActivoProducto.getText().toString();
            if (activo.equals("Activo")){
                chkActivoProducto.setChecked(true);
            }else{
                chkActivoProducto.setChecked(false);
            }
            btnEliminarProducto.setVisibility(View.VISIBLE);

            DistribuidorSelected = extras.getString("idDistribuidor");
            Query query = databaseReference.child("Distribuidor").orderByChild("cod_DISTRIBUIDOR").equalTo(DistribuidorSelected);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dist : dataSnapshot.getChildren()) {
                            DistribuidorModel dis = dist.getValue(DistribuidorModel.class);
                            NombreDistribuidorSelected = dis.getNOMBRE_DISTRIBUIDOR();
                        }
                    }else{
                        Toast.makeText(Productos.this, "no existe ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        loadDistribuidor();
        btnVolverProductoALstProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Productos.this, LstProductos.class);
                startActivity(i);
            }
        });

        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNombreProducto.getText().toString() == "" || txtValorUnidad.getText().toString().isEmpty() || txtCantidadProducto.getText().toString().isEmpty()){
                    Toast.makeText(Productos.this, "ERRROR CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                    return;
                }
                String nombreProducto = txtNombreProducto.getText().toString();
                String valorProducto = txtValorUnidad.getText().toString();
                String cantidadProducto = txtCantidadProducto.getText().toString();
                String activo = "";
                if (chkActivoProducto.isChecked()){
                    activo = "Activo";
                }else{
                    activo = "Inactivo";
                }

                ProductosModel p = new ProductosModel();
                p.setNOMBRE_PRODUCTO(nombreProducto);
                p.setVALOR_UNIDAD(valorProducto);
                p.setCANTIDAD_STOCK(cantidadProducto);
                p.setACTIVO(activo);
                p.setCOD_DISTRIBUIDOR(DistribuidorSelected);

                if (Id == ""){
                    p.setCOD_PRODUCTO(UUID.randomUUID().toString());
                    databaseReference.child("Producto").child(p.getCOD_PRODUCTO()).setValue(p);
                    Toast.makeText(Productos.this, "PRODUCTO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Productos.this, LstProductos.class);
                    startActivity(intent);
                    finish();
                }else{
                    p.setCOD_PRODUCTO(Id);
                    databaseReference.child("Producto").child(p.getCOD_PRODUCTO()).setValue(p);
                    Toast.makeText(Productos.this, "PRODUCTO ACTUALIZADO", Toast.LENGTH_LONG).show();
                    limpiar();
                    Intent intent = new Intent(Productos.this, LstProductos.class);
                    startActivity(intent);
                    finish();
                }



            }
        });

        btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductosModel p = new ProductosModel();
                p.setCOD_PRODUCTO(Id);
                databaseReference.child("Producto").child(p.getCOD_PRODUCTO()).removeValue();
                Toast.makeText(Productos.this, "PRODUCTO ELIMINADO", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Productos.this, LstProductos.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadDistribuidor(){
        final List<DistribuidorModel> distribuidores =  new ArrayList<>();
        databaseReference.child("Distribuidor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String idDis = ds.getKey();
                        String nombreDis = ds.child("nombre_DISTRIBUIDOR").getValue().toString();
                        distribuidores.add(new DistribuidorModel(idDis, nombreDis));
                    }
                    ArrayAdapter<DistribuidorModel> arrayAdapter = new ArrayAdapter<>(Productos.this, android.R.layout.simple_list_item_1, distribuidores);
                    spinnerDistribuidores.setAdapter(arrayAdapter);
                    spinnerDistribuidores.setSelection(getIndexSpinner(spinnerDistribuidores, NombreDistribuidorSelected));
                    spinnerDistribuidores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            DistribuidorSelected = distribuidores.get(i).getCOD_DISTRIBUIDOR();
                            NombreDistribuidorSelected = distribuidores.get(i).getNOMBRE_DISTRIBUIDOR();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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

    public int getIndexSpinner(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
            }
        }
        return index;
    }

    private void limpiar (){
        txtNombreProducto.setText("");
        txtValorUnidad.setText("");
        txtCantidadProducto.setText("");
        chkActivoProducto.setChecked(false);
    }

    @Override
    public void onClick(View view) {

    }
}
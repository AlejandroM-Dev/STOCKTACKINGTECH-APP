package com.example.stocktakingtech.View;

import androidx.annotation.Discouraged;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stocktakingtech.Adaptadores.ListaProductosAdapter;
import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class LstProductos extends AppCompatActivity {

    private List<ProductosModel> listProducto = new ArrayList<ProductosModel>();
    ArrayAdapter<ProductosModel> arrayAdapterProducto;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listV_productos;
    ProductosModel productoSelected;

    Button btnVolverLstProductoAInicio, btnNuevoProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst_productos);
        listV_productos =  findViewById(R.id.lv_datosProductos);

        inicializarFirebase();

        btnVolverLstProductoAInicio = findViewById(R.id.btnVolverLstProductoAInicio);
        btnNuevoProducto = findViewById(R.id.btnNuevoProducto);

        btnVolverLstProductoAInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstProductos.this, Inicio.class);
                startActivity(i);
            }
        });
        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LstProductos.this, Productos.class);
                startActivity(i);
            }
        });


        listarDatos();
        listV_productos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                productoSelected = (ProductosModel) adapterView.getItemAtPosition(i);
                String Id = productoSelected.getCOD_PRODUCTO();
                String nombreP = productoSelected.getNOMBRE_PRODUCTO();
                String valorP = productoSelected.getVALOR_UNIDAD();
                String cantidadP = productoSelected.getCANTIDAD_STOCK();
                String activoP = productoSelected.isACTIVO();
                String codDistribuidor = productoSelected.getCOD_DISTRIBUIDOR();
                String nombreDistribuidor = productoSelected.getNOMBRE_DISTRIBUIDOR();

                Intent intent = new Intent(LstProductos.this, Productos.class);
                intent.putExtra("Id", Id);
                intent.putExtra("nombreP", nombreP);
                intent.putExtra("valorP", valorP);
                intent.putExtra("cantidadP", cantidadP);
                intent.putExtra("activoP", activoP);
                intent.putExtra("idDistribuidor", codDistribuidor);
                intent.putExtra("nombreDistribuidor", nombreDistribuidor);
                startActivity(intent);
                finish();
            }
        });



    }

    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    ProductosModel p = objSnapshot.getValue(ProductosModel.class);
                    listProducto.add(p);

                    arrayAdapterProducto = new ArrayAdapter<ProductosModel>(LstProductos.this, android.R.layout.simple_list_item_1, listProducto);
                    listV_productos.setAdapter(arrayAdapterProducto);
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

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_productos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuProducto:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void nuevoRegistro(){

    }

}
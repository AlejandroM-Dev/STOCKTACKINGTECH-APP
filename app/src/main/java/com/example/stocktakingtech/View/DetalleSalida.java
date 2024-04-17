package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocktakingtech.Model.DetalleSalidaModel;
import com.example.stocktakingtech.Model.ProductosModel;
import com.example.stocktakingtech.Model.RolesModel;
import com.example.stocktakingtech.Model.SalidaModel;
import com.example.stocktakingtech.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DetalleSalida extends AppCompatActivity {
    EditText txtCantidadSalida;
    TextView txtValorTotalProducto,txtValorUnidadProducto, txtValorTotalSalida;
    Button btnAgregarProductoSalida, btnVolverDetalleSalidaALstConceptoCompra;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Spinner spinnerProductos;
    String ProductoIdSelected, NombreProductoSelected, ValorProductoSelected, CantidadProductoSelected, idUser;

    public int valorProducto = 0;
    public int cantidadProducto = 0;
    public int valorTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_salida);

        txtCantidadSalida = findViewById(R.id.txtCantidadSalida);
        txtValorTotalProducto = findViewById(R.id.txtValorTotalProducto);
        txtValorUnidadProducto = findViewById(R.id.txtValorUnidadProducto);
        txtValorTotalSalida = findViewById(R.id.txtValorTotalSalida);
        spinnerProductos = findViewById(R.id.spinnerProductos);
        ProductoIdSelected = "";
        NombreProductoSelected = "";
        ValorProductoSelected = "";
        CantidadProductoSelected = "";

        Bundle extras = getIntent().getExtras();
        idUser = "";
        if(extras != null){
            idUser = getIntent().getExtras().getString("idUser");
        }

        btnAgregarProductoSalida = findViewById(R.id.btnAgregarProductoSalida);
        btnVolverDetalleSalidaALstConceptoCompra = findViewById(R.id.btnVolverDetalleSalidaALstConceptoCompra);

        inicializarFirebase();
        loadProducto();


        txtCantidadSalida.addTextChangedListener(new TextWatcher() {

            // Antes de que el texto cambie (no debemos modificar nada aquí)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //Cuando esté cambiando...(no debemos modificar el texto aquí)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /* Aquí el texto ya ha cambiado completamente, tenemos el texto actualizado en pocas palabras
             * Por cierto, aquí sí podemos modificar el texto pero debemos tener cuidado para no caer en
             * un ciclo infinito*/
            @Override
            public void afterTextChanged(Editable txtCantidadSalida) {
                if (!txtCantidadSalida.toString().isEmpty()){
                    TotalProducto();
                }else{
                    txtValorTotalProducto.setText("Total producto: $0");
                }

            }
        });

        btnVolverDetalleSalidaALstConceptoCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetalleSalida.this, LstConceptoCompra.class);
                startActivity(i);
            }
        });


        btnAgregarProductoSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int finalCantidadProducto = cantidadProducto;
                int CantidadStock = Integer.parseInt(CantidadProductoSelected);
                if(CantidadStock < finalCantidadProducto){
                    Toast.makeText(DetalleSalida.this, "La cantidad a seleccionar superar el numero de stock " + CantidadStock + " unidades", Toast.LENGTH_LONG).show();
                    return;
                }
                txtValorTotalSalida = txtValorTotalProducto;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date date = new Date();
                String fechaCompra = dateFormat.format(date);

                DetalleSalidaModel d = new DetalleSalidaModel();
                d.setDESCRIPCION_CONCEPTO(NombreProductoSelected);
                d.setPRECIO("" + Integer.toString(valorTotal));
                d.setCOD_PRODUCTO(ProductoIdSelected);
                d.setCOD_DETALLE_SALIDA(UUID.randomUUID().toString());
                d.setCANTIDAD(txtCantidadSalida.getText().toString());
                Toast.makeText(DetalleSalida.this, " " + idUser + " unidades", Toast.LENGTH_LONG).show();
                databaseReference.child("Usuario").child(idUser).child("carrito").child(d.getCOD_DETALLE_SALIDA()).setValue(d);
            }
        });
    }

    public void loadProducto(){
        final List<ProductosModel> productos =  new ArrayList<>();
        databaseReference.child("Producto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String idProducto = ds.getKey();
                        String nombreProducto = ds.child("nombre_PRODUCTO").getValue().toString();
                        String valorProducto = ds.child("valor_UNIDAD").getValue().toString();
                        String cantidadProducto = ds.child("cantidad_STOCK").getValue().toString();
                        productos.add(new ProductosModel(idProducto, nombreProducto, valorProducto, cantidadProducto));
                    }
                    ArrayAdapter<ProductosModel> arrayAdapter = new ArrayAdapter<>(DetalleSalida.this, android.R.layout.simple_list_item_1, productos);
                    spinnerProductos.setAdapter(arrayAdapter);
                    spinnerProductos.setSelection(getIndexSpinner(spinnerProductos, NombreProductoSelected));
                    spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ProductoIdSelected = productos.get(i).getCOD_PRODUCTO();
                            NombreProductoSelected = productos.get(i).getNOMBRE_PRODUCTO();
                            ValorProductoSelected = productos.get(i).getVALOR_UNIDAD();
                            txtValorUnidadProducto.setText("Valor unidad producto: $" + ValorProductoSelected.toString());
                            CantidadProductoSelected = productos.get(i).getCANTIDAD_STOCK();
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

    public void TotalProducto(){
        if (!ValorProductoSelected.toString().isEmpty()){
            valorProducto = Integer.parseInt(ValorProductoSelected);
        }
        cantidadProducto = Integer.parseInt(txtCantidadSalida.getText().toString());

        if (valorProducto != 0 && cantidadProducto != 0){
            valorTotal = valorProducto * cantidadProducto;
            txtValorTotalProducto.setText("Total producto: $" + Integer.toString(valorTotal));
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

}
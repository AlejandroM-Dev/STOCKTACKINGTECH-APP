package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.Model.Usuario;
import com.example.stocktakingtech.R;
import com.example.stocktakingtech.Styles.FirstFragment;
import com.example.stocktakingtech.Styles.SecondFragment;
import com.example.stocktakingtech.Styles.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    TextView bienvenido;
    String id, email, nombres, apellidos, idUsuario;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    Button btnProductos, btnDistribuidores, btnRoles, btnSalidas, btnClientes, btnRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        inicializarFirebase();
        btnProductos = (Button) findViewById(R.id.btnProductos);
        btnDistribuidores = (Button) findViewById(R.id.btnDistribuidores);
        btnRoles = (Button) findViewById(R.id.btnRoles);
        btnSalidas = (Button) findViewById(R.id.btnSalidas);
        btnClientes = (Button) findViewById(R.id.btnClientes);
        btnRuta = (Button) findViewById(R.id.btnRuta);

        btnProductos.setOnClickListener(this);
        btnDistribuidores.setOnClickListener(this);
        btnRoles.setOnClickListener(this);
        btnSalidas.setOnClickListener(this);
        btnClientes.setOnClickListener(this);
        btnRuta.setOnClickListener(this);

        id = "";
        idUsuario = "";
        email = "";
        nombres = "";
        apellidos = "";
        Bundle extras = getIntent().getExtras();

        bienvenido = (TextView) findViewById(R.id.txtBienvenido);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(extras != null){
            email = getIntent().getExtras().getString("EmailUsuario");
            QueryUser();
        }else{
            if (user != null){
                email = user.getEmail();
                QueryUser();
            }
        }

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        loadUsuario();
    }

    public void QueryUser(){
        Query query = databaseReference.child("Usuario").orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Usuario u = ds.getValue(Usuario.class);
                        id = u.getCOD_USUARIO();
                        nombres = u.getNOMBRE_USUARIO();
                        apellidos = u.getAPELLIDOS_USUARIO();
                    }
                }else{
                    Toast.makeText(Inicio.this, "no existe ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadUsuario(){
        final List<Usuario> usuarios =  new ArrayList<>();
        databaseReference.child("Usuario").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        bienvenido.setText("Bienvenid@ " + nombres +" " + apellidos);
                        idUsuario = id;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation, menu);
        return true;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.firstFragment:
                    Intent i = new Intent(Inicio.this, Inicio.class);
                    startActivity(i);
                    return true;
                case R.id.thirdFragment:
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(Inicio.this, "Sesion cerrada", Toast.LENGTH_LONG).show();

                    Intent i2 = new Intent(Inicio.this, MainActivity.class);
                    startActivity(i2);
                    return true;
            }
            return false;
        }
    };

    private void nuevoRegistro() {
        Intent i = new Intent(this, Inicio.class);
        startActivity(i);
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProductos:

                Intent i2 = new Intent(Inicio.this, LstProductos.class);
                startActivity(i2);
                finish();
                break;

            case R.id.btnDistribuidores:
                Intent i3 = new Intent(Inicio.this, LstDistribuidor.class);
                startActivity(i3);
                finish();
                break;

            case R.id.btnRoles:
                Intent i4 = new Intent(Inicio.this, LstRoles.class);
                startActivity(i4);
                finish();
                break;

            case R.id.btnSalidas:
                Intent i5 = new Intent(Inicio.this, LstConceptoCompra.class);
                i5.putExtra("idUsuario", idUsuario);
                startActivity(i5);
                finish();
                break;

            case R.id.btnClientes:
                Intent i6 = new Intent(Inicio.this, LstClientes.class);
                startActivity(i6);
                finish();
                break;

            case R.id.btnRuta:
                Intent i7 = new Intent(this, RutaMap.class);
                startActivity(i7);
                finish();
                break;
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
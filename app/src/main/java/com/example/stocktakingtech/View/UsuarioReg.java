package com.example.stocktakingtech.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.stocktakingtech.Model.DistribuidorModel;
import com.example.stocktakingtech.Model.RolesModel;
import com.example.stocktakingtech.Model.Usuario;
import com.example.stocktakingtech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioReg extends AppCompatActivity {

    EditText txtIdentificacionUsuarioReg, txtNombresUsuarioReg, txtApellidosUsuarioReg, txtEmailUsuarioReg, txtTelefonoUsuarioReg, txtDireccionUsuarioReg, txtContrasenaUsuarioReg;
    CheckBox chkActivoUsuarioReg;
    Button btnGuardarUsuarioReg, btnEliminarUsuarioReg, btnVolverUsuarioALstUsuario;
    Spinner spinnerRoles;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    String Id, RolIdSelected, NombreRolSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_reg);

        Bundle extras = getIntent().getExtras();
        firebaseAuth = FirebaseAuth.getInstance();
        Id = "";
        RolIdSelected = "";
        NombreRolSelected = "";

        txtIdentificacionUsuarioReg = findViewById(R.id.txtIdentificacionUsuarioReg);
        txtNombresUsuarioReg = findViewById(R.id.txtNombresUsuarioReg);
        txtApellidosUsuarioReg = findViewById(R.id.txtApellidosUsuarioReg);
        txtEmailUsuarioReg = findViewById(R.id.txtEmailUsuarioReg);
        txtTelefonoUsuarioReg = findViewById(R.id.txtTelefonoUsuarioReg);
        txtDireccionUsuarioReg = findViewById(R.id.txtDireccionUsuarioReg);
        txtContrasenaUsuarioReg = findViewById(R.id.txtContrasenaUsuarioReg);
        chkActivoUsuarioReg = findViewById(R.id.chkActivoUsuarioReg);

        spinnerRoles = findViewById(R.id.spinnerRoles);

        btnGuardarUsuarioReg = findViewById(R.id.btnGuardarUsuarioReg);
        btnEliminarUsuarioReg = findViewById(R.id.btnEliminarUsuarioReg);
        btnVolverUsuarioALstUsuario = findViewById(R.id.btnVolverUsuarioALstUsuario);
        inicializarFirebase();

        if(extras != null){
            Id = extras.getString("IdU");
            txtIdentificacionUsuarioReg.setText(extras.getString("identificacionU"));
            txtNombresUsuarioReg.setText(extras.getString("nombresU"));
            txtApellidosUsuarioReg.setText(extras.getString("apellidosU"));
            txtEmailUsuarioReg.setText(extras.getString("emailU"));
            txtTelefonoUsuarioReg.setText(extras.getString("telefonoU"));
            txtDireccionUsuarioReg.setText(extras.getString("direccionU"));
            txtContrasenaUsuarioReg.setText(extras.getString("contraseñaU"));
            chkActivoUsuarioReg.setText(extras.getString("activoU"));
            RolIdSelected = extras.getString("idRolU");

            String activo = chkActivoUsuarioReg.getText().toString();
            if (activo.equals("Activo")){
                chkActivoUsuarioReg.setChecked(true);
            }else{
                chkActivoUsuarioReg.setChecked(false);
            }
            btnEliminarUsuarioReg.setVisibility(View.VISIBLE);

            Query query = databaseReference.child("Rol").orderByChild("id_ROL").equalTo(RolIdSelected);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot roles : dataSnapshot.getChildren()) {
                            RolesModel rol = roles.getValue(RolesModel.class);
                            NombreRolSelected = rol.getNOMBRE_ROL();
                        }
                    }else{
                        Toast.makeText(UsuarioReg.this, "no existe ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        loadRol();
        btnVolverUsuarioALstUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UsuarioReg.this, LstClientes.class);
                startActivity(i);
            }
        });

        btnGuardarUsuarioReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtIdentificacionUsuarioReg.getText().toString() == "" || txtNombresUsuarioReg.getText().toString().isEmpty() || txtApellidosUsuarioReg.getText().toString().isEmpty() || txtEmailUsuarioReg.getText().toString().isEmpty() || txtTelefonoUsuarioReg.getText().toString().isEmpty() || txtDireccionUsuarioReg.getText().toString().isEmpty() || txtContrasenaUsuarioReg.getText().toString().isEmpty()){
                    Toast.makeText(UsuarioReg.this, "ERRROR CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                    return;
                }

                String identificacion = txtIdentificacionUsuarioReg.getText().toString();
                String nombres = txtNombresUsuarioReg.getText().toString();
                String apellidos = txtApellidosUsuarioReg.getText().toString();
                String email = txtEmailUsuarioReg.getText().toString();
                String telefono = txtTelefonoUsuarioReg.getText().toString();
                String direccion = txtDireccionUsuarioReg.getText().toString();
                String contraseña = txtContrasenaUsuarioReg.getText().toString();
                String activo = "";
                if (chkActivoUsuarioReg.isChecked()){
                    activo = "Activo";
                }else{
                    activo = "Inactivo";
                }

                Usuario u = new Usuario();
                u.setIDENTIFICACION_USUARIO(identificacion);
                u.setNOMBRE_USUARIO(nombres);
                u.setAPELLIDOS_USUARIO(apellidos);
                u.setEMAIL(email);
                u.setTELEFONO(telefono);
                u.setDIRECCION(direccion);
                u.setCONTRASEÑA(contraseña);
                u.setACTIVO(activo);
                u.setID_ROL(RolIdSelected);

                if (Id == ""){
                    u.setCOD_USUARIO(UUID.randomUUID().toString());
                    databaseReference.child("Usuario").child(u.getCOD_USUARIO()).setValue(u);
                    Toast.makeText(UsuarioReg.this, "USUARIO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    u.setCOD_USUARIO(Id);
                    databaseReference.child("Usuario").child(u.getCOD_USUARIO()).setValue(u);
                    Toast.makeText(UsuarioReg.this, "USUARIO ACTUALIZADO", Toast.LENGTH_LONG).show();
                    limpiar();
                }

                firebaseAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UsuarioReg.this, "USUARIO AUTENTICADO", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UsuarioReg.this, LstClientes.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(UsuarioReg.this, "USUARIO NO AUTENTICADO", Toast.LENGTH_LONG).show();
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            dameToastdeerror(errorCode);
                        }
                    }
                });

            }
        });

        btnEliminarUsuarioReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario();
                u.setCOD_USUARIO(Id);
                databaseReference.child("Usuario").child(u.getCOD_USUARIO()).removeValue();
                Toast.makeText(UsuarioReg.this, "USUARIO ELIMINADO", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UsuarioReg.this, LstClientes.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void loadRol(){
        final List<RolesModel> roles =  new ArrayList<>();
        databaseReference.child("Rol").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String idRol = ds.getKey();
                        String nombreRol = ds.child("nombre_ROL").getValue().toString();
                        roles.add(new RolesModel(idRol, nombreRol));
                    }
                    ArrayAdapter<RolesModel> arrayAdapter = new ArrayAdapter<>(UsuarioReg.this, android.R.layout.simple_list_item_1, roles);
                    spinnerRoles.setAdapter(arrayAdapter);
                    spinnerRoles.setSelection(getIndexSpinner(spinnerRoles, NombreRolSelected));
                    spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            RolIdSelected = roles.get(i).getID_ROL();
                            NombreRolSelected = roles.get(i).getNOMBRE_ROL();
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void limpiar (){
        txtIdentificacionUsuarioReg.setText("");
        txtNombresUsuarioReg.setText("");
        txtApellidosUsuarioReg.setText("");
        txtEmailUsuarioReg.setText("");
        txtTelefonoUsuarioReg.setText("");
        txtDireccionUsuarioReg.setText("");
        txtContrasenaUsuarioReg.setText("");
        chkActivoUsuarioReg.setChecked(false);
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(UsuarioReg.this, "El formato del token personalizado es incorrecto. Por favor revise la documentacion", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(UsuarioReg.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(UsuarioReg.this, "La credencial de autenticacion proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(UsuarioReg.this, "La direccion de correo electronico esta¡ mal formateada.", Toast.LENGTH_LONG).show();
                txtEmailUsuarioReg.setError("La direccion de correo electronico esta¡ mal formateada.");
                txtEmailUsuarioReg.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(UsuarioReg.this, "La contraseña no es valida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txtContrasenaUsuarioReg.setError("la contraseÃ±a es incorrecta ");
                txtContrasenaUsuarioReg.requestFocus();
                txtContrasenaUsuarioReg.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(UsuarioReg.this, "Las credenciales proporcionadas no corresponden al usuario que inicio sesion anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(UsuarioReg.this,"Esta operacion es sensible y requiere autenticacion reciente. Inicie sesion nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(UsuarioReg.this, "Ya existe una cuenta con la misma direccion de correo electronico pero diferentes credenciales de inicio de sesion. Inicie sesion con un proveedor asociado a esta direccion de correo electronico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(UsuarioReg.this, "La direccion de correo electronico ya esta¡ siendo utilizada por otra cuenta...   ", Toast.LENGTH_LONG).show();
                txtEmailUsuarioReg.setError("La direccion de correo electronico ya esta¡ siendo utilizada por otra cuenta.");
                txtEmailUsuarioReg.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(UsuarioReg.this, "Esta credencial ya esta¡ asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(UsuarioReg.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(UsuarioReg.this, "La credencial del usuario ya no es vÃ¡lida. El usuario debe iniciar sesion nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(UsuarioReg.this, "No hay ningÃºn registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(UsuarioReg.this, "La credencial del usuario ya no es vÃ¡lida. El usuario debe iniciar sesion nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(UsuarioReg.this, "Esta operación no esta¡ permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(UsuarioReg.this, "La contraseña proporcionada no es valida..", Toast.LENGTH_LONG).show();
                txtContrasenaUsuarioReg.setError("La contraseña no es valida, debe tener al menos 6 caracteres");
                txtContrasenaUsuarioReg.requestFocus();
                break;

        }

    }
}
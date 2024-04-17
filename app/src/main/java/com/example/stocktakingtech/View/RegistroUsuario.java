package com.example.stocktakingtech.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.stocktakingtech.Model.Usuario;
import com.example.stocktakingtech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class RegistroUsuario extends AppCompatActivity implements View.OnClickListener {

    EditText edit_identificacion, edit_nombres, edit_apellidos, edit_email, edit_telefono, edit_direccion, edit_contraseña, edit_confirmContraseña;
    Button edit_btnRegresarReg, edit_btnCrearReg;

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        inicializarFirebase();

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.txtEmailReg, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.txtContrasenaReg, ".{6,}", R.string.invalid_password);

        edit_identificacion = (EditText) findViewById(R.id.txtIdentificacionReg);
        edit_nombres = (EditText) findViewById(R.id.txtNombresReg);
        edit_apellidos = (EditText) findViewById(R.id.txtApellidosReg);
        edit_email = (EditText) findViewById(R.id.txtEmailReg);
        edit_telefono = (EditText) findViewById(R.id.txtTelefonoReg);
        edit_direccion = (EditText) findViewById(R.id.txtDireccionReg);
        edit_contraseña = (EditText) findViewById(R.id.txtContrasenaReg);
        edit_confirmContraseña = (EditText) findViewById(R.id.txtConfirmContraseñaReg);

        edit_btnRegresarReg = (Button)findViewById(R.id.btnRegresarReg);
        edit_btnCrearReg = (Button)findViewById(R.id.btnCrearReg);

        edit_btnRegresarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistroUsuario.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        edit_btnCrearReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edit_nombres.getText().toString();
                String apellidos = edit_apellidos.getText().toString();
                String email = edit_email.getText().toString();
                String direccion = edit_direccion.getText().toString();
                String telefono = edit_telefono.getText().toString();
                String contraseña = edit_contraseña.getText().toString();
                String confirmContraseña = edit_confirmContraseña.getText().toString();
                String identificacion = edit_identificacion.getText().toString();

                Usuario u = new Usuario();
                u.setIDENTIFICACION_USUARIO(identificacion);
                u.setNOMBRE_USUARIO(nombre);
                u.setAPELLIDOS_USUARIO(apellidos);
                u.setEMAIL(email);
                u.setTELEFONO(telefono);
                u.setDIRECCION(direccion);
                u.setCONTRASEÑA(contraseña);
                u.setACTIVO("Activo");

                u.setCOD_USUARIO(UUID.randomUUID().toString());
                databaseReference.child("Usuario").child(u.getCOD_USUARIO()).setValue(u);
                Toast.makeText(RegistroUsuario.this, "REGISTRANDO USUARIO...", Toast.LENGTH_LONG).show();

                if(awesomeValidation.validate()){
                    if(edit_contraseña.getText().toString().equals(edit_confirmContraseña.getText().toString())){
                        firebaseAuth.createUserWithEmailAndPassword(email, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegistroUsuario.this, "Usuario creado con exito", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(RegistroUsuario.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                    dameToastdeerror(errorCode);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(RegistroUsuario.this, "Error: Contraseñas no coincidenxxx", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(RegistroUsuario.this, "Error: Completa todos los datos", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegistroUsuario.this, "El formato del token personalizado es incorrecto. Por favor revise la documentacion", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegistroUsuario.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegistroUsuario.this, "La credencial de autenticacion proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegistroUsuario.this, "La direccion de correo electronico esta¡ mal formateada.", Toast.LENGTH_LONG).show();
                edit_email.setError("La direccion de correo electronico esta¡ mal formateada.");
                edit_email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegistroUsuario.this, "La contraseña no es valida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                edit_contraseña.setError("la contraseÃ±a es incorrecta ");
                edit_contraseña.requestFocus();
                edit_contraseña.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegistroUsuario.this, "Las credenciales proporcionadas no corresponden al usuario que inicio sesion anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegistroUsuario.this,"Esta operacion es sensible y requiere autenticacion reciente. Inicie sesion nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegistroUsuario.this, "Ya existe una cuenta con la misma direccion de correo electronico pero diferentes credenciales de inicio de sesion. Inicie sesion con un proveedor asociado a esta direccion de correo electronico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegistroUsuario.this, "La direccion de correo electronico ya esta¡ siendo utilizada por otra cuenta...   ", Toast.LENGTH_LONG).show();
                edit_email.setError("La direccion de correo electronico ya esta¡ siendo utilizada por otra cuenta.");
                edit_email.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegistroUsuario.this, "Esta credencial ya esta¡ asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegistroUsuario.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegistroUsuario.this, "La credencial del usuario ya no es vÃ¡lida. El usuario debe iniciar sesion nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegistroUsuario.this, "No hay ningÃºn registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegistroUsuario.this, "La credencial del usuario ya no es vÃ¡lida. El usuario debe iniciar sesion nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegistroUsuario.this, "Esta operación no esta¡ permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegistroUsuario.this, "La contraseña proporcionada no es valida..", Toast.LENGTH_LONG).show();
                edit_contraseña.setError("La contraseña no es valida, debe tener al menos 6 caracteres");
                edit_contraseña.requestFocus();
                break;

        }

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
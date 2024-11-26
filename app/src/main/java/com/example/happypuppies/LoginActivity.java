package com.example.happypuppies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerLink;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password2);
        loginButton = findViewById(R.id.login_button);
        registerLink = findViewById(R.id.register_link);

        loginButton.setOnClickListener(v -> handleLogin());
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(loginButton, "Por favor, ingresa todos los campos", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.RED)
                    .setTextColor(Color.WHITE)
                    .show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Snackbar.make(loginButton, "Inicio de sesión exitoso", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.GREEN)
                        .setTextColor(Color.WHITE)
                        .show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(loginButton, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED)
                        .setTextColor(Color.WHITE)
                        .show();
            }
        });
    }
}

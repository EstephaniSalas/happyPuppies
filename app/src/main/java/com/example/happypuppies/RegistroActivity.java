package com.example.happypuppies;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    // Variables Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference storageRef;

    // Variables para el dueño
    EditText nameEditText, emailEditText, passwordEditText, phoneEditText, postalCodeEditText;

    // Variables para el perro
    EditText dogNameEditText, dogBirthdateEditText, dogAgeEditText, dogBreedEditText, dogWeightEditText, dogColorEditText;
    Spinner dogGenderSpinner, dogSizeSpinner;
    Button dogImageButton, registerButton;
    ImageButton backToLoginButton;

    // Variables para seleccionar fecha
    int year, month, day;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        // Inicializar Firebase Auth y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("dog_images");

        // Inicializar las vistas
        nameEditText = findViewById(R.id.register_name);
        emailEditText = findViewById(R.id.register_email);
        passwordEditText = findViewById(R.id.register_password);
        phoneEditText = findViewById(R.id.register_phone);
        postalCodeEditText = findViewById(R.id.register_postal_code);

        dogNameEditText = findViewById(R.id.register_dog_name);
        dogBirthdateEditText = findViewById(R.id.register_dog_birthdate);
        dogAgeEditText = findViewById(R.id.register_dog_age);
        dogBreedEditText = findViewById(R.id.register_dog_breed);
        dogWeightEditText = findViewById(R.id.register_dog_weight);
        dogColorEditText = findViewById(R.id.register_dog_color);

        dogGenderSpinner = findViewById(R.id.register_dog_gender);
        dogSizeSpinner = findViewById(R.id.register_dog_size);

        dogImageButton = findViewById(R.id.register_dog_image);
        registerButton = findViewById(R.id.register_button);
        backToLoginButton = findViewById(R.id.btn_BackLogin);

        // Configurar Spinner de Género
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[]{"Femenino", "Masculino"});
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogGenderSpinner.setAdapter(genderAdapter);

        // Configurar Spinner de Tamaño
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[]{"Chico", "Mediano", "Grande"});
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogSizeSpinner.setAdapter(sizeAdapter);

        // Configurar DatePicker para la fecha de nacimiento
        dogBirthdateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegistroActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        dogBirthdateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        calculateDogAge(year, month, dayOfMonth);
                    },
                    year,
                    month,
                    day
            );
            datePickerDialog.show();
        });

        // Manejar clic del botón para seleccionar imagen
        dogImageButton.setOnClickListener(v -> openFileChooser());

        // Manejar clic del botón de registro
        registerButton.setOnClickListener(v -> handleRegister());

        // Manejar clic del botón de regreso
        backToLoginButton.setOnClickListener(v -> goToLogin());
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            String fileName = getFileName(imageUri);
            dogImageButton.setText(fileName);
        }
    }

    private String getFileName(Uri uri) {
        String path = uri.getPath();
        return path != null ? path.substring(path.lastIndexOf("/") + 1) : "Imagen seleccionada";
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> showSnackbar(registerButton, "Imagen subida correctamente", true))
                    .addOnFailureListener(e -> showSnackbar(registerButton, "Error al subir la imagen", false));
        }
    }

    private void handleRegister() {
        // Validaciones
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showSnackbar(registerButton, "Por favor, completa todos los campos", false);
            return;
        }

        // Subir la imagen si está seleccionada
        uploadImage();

        // Aquí puedes agregar el resto de la lógica para registrar los datos del usuario y el perro en Firebase Firestore
    }

    private void showSnackbar(View view, String message, boolean success) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(success ? getColor(R.color.success_green) : getColor(R.color.error_red));
        snackbar.show();
    }

    private void calculateDogAge(int year, int month, int dayOfMonth) {
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int age = currentYear - year;
        dogAgeEditText.setText(String.valueOf(age));
    }

    private void goToLogin() {
        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

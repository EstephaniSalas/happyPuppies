package com.example.happypuppies;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.happypuppies.models.Dog;
import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDogs;
    private DogAdapter dogAdapter;
    private List<Dog> dogList;
    private FirebaseFirestore db;
    private TextView funFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        recyclerViewDogs = findViewById(R.id.recycler_view_dogs);
        funFact = findViewById(R.id.fun_fact);

        recyclerViewDogs.setLayoutManager(new LinearLayoutManager(this));
        dogList = new ArrayList<>();
        dogAdapter = new DogAdapter(this, dogList);
        recyclerViewDogs.setAdapter(dogAdapter);

        db = FirebaseFirestore.getInstance();

        loadDogs();
        displayFunFact();
    }

    private void loadDogs() {
        db.collection("perros")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Dog dog = document.toObject(Dog.class);
                            dogList.add(dog);
                        }
                        dogAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void displayFunFact() {
        // Dato curioso (puedes hacerlo dinámico más adelante)
        funFact.setText("¿Sabías que los perros tienen un sentido del olfato 10,000 veces más desarrollado que los humanos?");
    }
}

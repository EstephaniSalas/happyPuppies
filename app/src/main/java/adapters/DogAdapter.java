package com.example.happypuppies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happypuppies.R;
import com.example.happypuppies.models.Dog;

import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {

    private Context context;
    private List<Dog> dogList;

    // Constructor
    public DogAdapter(Context context, List<Dog> dogList) {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dog_card, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.nameTextView.setText(dog.getName());
        holder.breedTextView.setText(dog.getBreed());
        holder.ageTextView.setText(String.format("%d años", dog.getAge()));

        // Cargar imagen con Glide
        Glide.with(context)
                .load(dog.getImageUrl())
                .placeholder(R.drawable.placeholder_dog) // Reemplaza con tu imagen de placeholder
                .into(holder.dogImageView);
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, breedTextView, ageTextView;
        ImageView dogImageView;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.dog_name);
            breedTextView = itemView.findViewById(R.id.dog_breed);
            ageTextView = itemView.findViewById(R.id.dog_age);
            dogImageView = itemView.findViewById(R.id.dog_image);
        }
    }
}

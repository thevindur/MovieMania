package com.example.moviemania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_menu);
    }

    public void registerMovie(View view) {
        Intent homeIntent = new Intent(this,RegisterMovie.class);
        startActivity(homeIntent);
    }

    public void displayMovies(View view) {
        Intent homeIntent = new Intent(this,DisplayMovies.class);
        startActivity(homeIntent);
    }
}
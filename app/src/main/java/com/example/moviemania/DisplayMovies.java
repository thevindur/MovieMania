package com.example.moviemania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayMovies extends AppCompatActivity {
    private MovieManiaDBHelper movieManiaDBHelper;
    ListView movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_display_movies);

        movieManiaDBHelper = new MovieManiaDBHelper(this);
        movieList = findViewById(R.id.movieListView);

        displayMovieList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void displayMovieList() {
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor = movieManiaDBHelper.getMovieTitle();

        int count = 1;

        if (cursor.getCount() == 0){
            Toast.makeText(DisplayMovies.this,"NO MOVIES ADDED",Toast.LENGTH_LONG).show();
            return;
        }

        while (cursor.moveToNext()){
            arrayList.add(count + " . " + cursor.getString(0));
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,arrayList);
            movieList.setAdapter(listAdapter);
            count++;
        }
    }
}
package com.example.moviemania;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class SearchMovie extends AppCompatActivity {
    private MovieManiaDBHelper movieManiaDBHelper;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    private TextInputLayout searchBar;
    String searchInput;
    Button button;

    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> castList = new ArrayList<>();
    ArrayList<String> directorList = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_search_movie);

        movieManiaDBHelper = new MovieManiaDBHelper(this);
        button = findViewById(R.id.btnLookup);
        button.setText("Lookup");

        searchBar = findViewById(R.id.textInputSearchBar);
    }

    @SuppressLint("SetTextI18n")
    public void searchResult(View view) {
        if (button.getText().equals("Lookup")) {
            searchInput = String.valueOf(Objects.requireNonNull(searchBar.getEditText()).getText());

            Cursor cursor = movieManiaDBHelper.getMovieDetails();

            if (cursor.getCount() == 0) {
                Toast.makeText(SearchMovie.this, "NO MOVIES ADDED", Toast.LENGTH_LONG).show();
                return;
            }
            while (cursor.moveToNext()) {
                nameList.add(cursor.getString(0));
                castList.add(cursor.getString(2));
                directorList.add(cursor.getString(3));
            }

            ArrayList<Integer> index1 = new ArrayList<>();
            ArrayList<Integer> index2 = new ArrayList<>();
            ArrayList<Integer> index3 = new ArrayList<>();

            String lowerSearchInput = searchInput.toLowerCase();

            //Iterating through the NAMES array to search the char values and to display the output.
            for (int i = 0; i < nameList.size(); i++) {
                String lowerDbValue = nameList.get(i).toLowerCase();
                if (lowerDbValue.contains(lowerSearchInput)) {
                    index1.add(i);
                }
            }

            //Iterating through the CAST array to search the char values and to display the output.
            for (int i = 0; i < castList.size(); i++) {
                String lowerDbValue = castList.get(i).toLowerCase();
                if (lowerDbValue.contains(lowerSearchInput)) {
                    index2.add(i);
                }
            }

            //Iterating through the DIRECTOR array to search the char values and to display the output.
            for (int i = 0; i < directorList.size(); i++) {
                String lowerDbValue = directorList.get(i).toLowerCase();
                if (lowerDbValue.contains(lowerSearchInput)) {
                    index3.add(i);
                }
            }

            //Displaying Search Results for movies - TITLE.
            linearLayout = findViewById(R.id.linearSearch1);
            ArrayList<String> search1 = new ArrayList<>();
            for (int i = 0; i < index1.size(); i++) {
                search1.add("Movie: " + nameList.get(index1.get(i)));
            }
            ListView l1 = new ListView(this);
            l1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, search1) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setTextColor(Color.rgb(255, 255, 255));
                    return textView;
                }
            });
            linearLayout.addView(l1);

            //Displaying Search Results for movies - CAST.
            linearLayout1 = findViewById(R.id.linearSearch2);
            ArrayList<String> search2 = new ArrayList<>();
            for (int i = 0; i < index2.size(); i++) {
                search2.add("Cast: " + castList.get(index1.get(i)));
            }
            ListView l2 = new ListView(this);
            l2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, search2) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setTextColor(Color.rgb(255, 255, 255));
                    return textView;
                }
            });
            linearLayout1.addView(l2);

            //Displaying Search Results for movies - DIRECTOR.
            linearLayout2 = findViewById(R.id.linearSearch3);
            ArrayList<String> search3 = new ArrayList<>();
            for (int i = 0; i < index3.size(); i++) {
                search3.add("Director: " + castList.get(index1.get(i)));
            }
            ListView l3 = new ListView(this);
            l3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, search3) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setTextColor(Color.rgb(255, 255, 255));
                    return textView;
                }
            });
            linearLayout2.addView(l3);

            button.setText("New Search");
        }else if (button.getText().equals("New Search")){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }
}
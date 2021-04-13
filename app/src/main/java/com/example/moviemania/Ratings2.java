package com.example.moviemania;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class Ratings2 extends AppCompatActivity {
    TextView movieFinalTitle;
    TextView movieFinalRating;
    String selectedMovie;
    String finalTitle;
    String finalRating;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_ratings2);

        Toast.makeText(Ratings2.this,"LOADING MOVIE",Toast.LENGTH_LONG).show();

        movieFinalTitle=findViewById(R.id.textViewRatings2);
        movieFinalRating=findViewById(R.id.textViewFinalRating);
        moviePoster=findViewById(R.id.imageViewIMDB);

        Intent intent = getIntent();
        selectedMovie = intent.getExtras().getString("selectedMovie");

        RatingsURL ratingsURL = new RatingsURL();
        ratingsURL.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class RatingsURL extends AsyncTask<Void,Void,Void> {
        boolean nullTerm = false;
        String url;
        String url1;
        String inputLine;
        String inputLine1;
        String outputData;
        StringBuffer response = new StringBuffer();
        StringBuffer response1 = new StringBuffer();
        Bitmap bmp;
        String imageURL;

        @Override
        protected Void doInBackground(Void... voids) {
            url = "https://imdb-api.com/en/API/SearchTitle/k_pg5zm10q";
            url1 = "https://imdb-api.com/en/API/UserRatings/k_pg5zm10q";

            try {
                if (selectedMovie.isEmpty()){
                    nullTerm = true;
                }else {
                    URL obj = new URL(url + "/" + selectedMovie);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        if (response.toString().contains("\"expression\": null") || response.toString().isEmpty()) {
                            nullTerm = true;
                        } else {
                            JSONObject jo = new JSONObject(response.toString());
                            JSONArray jsonArray = jo.getJSONArray("results");
                            JSONObject jo1 = jsonArray.getJSONObject(0);
                            outputData = (String) jo1.get("id");

                            imageURL = (String) jo1.get("image");
                            URL imageOutputURL = new URL(imageURL);
                            URLConnection conn = imageOutputURL.openConnection();
                            conn.connect();
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            bmp = BitmapFactory.decodeStream(bis);
                            bis.close();

                            URL obj1 = new URL(url1 + "/" + outputData);
                            HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();
                            con1.setRequestMethod("GET");
                            int responseCode1 = con1.getResponseCode();

                            if (responseCode1 == 200) {
                                BufferedReader in1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
                                while ((inputLine1 = in1.readLine()) != null) {
                                    response1.append(inputLine1);
                                }
                                in.close();
                                JSONObject newJo = new JSONObject(response1.toString());
                                finalTitle = newJo.getString("fullTitle");
                                finalRating = newJo.getString("totalRating");
                            }
                        }
                    } else {
                        nullTerm = true;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            if (nullTerm) {
                Toast.makeText(Ratings2.this,"Error",Toast.LENGTH_LONG).show();
            } else {
                movieFinalTitle.setText(finalTitle);
                moviePoster.setImageBitmap(bmp);
                moviePoster.setVisibility(View.VISIBLE);
                movieFinalRating.setText("Final Rating : " + finalRating);
            }
        }
    }
}
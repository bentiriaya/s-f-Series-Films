package com.example.sf.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sf.Adapter.FilmListAdapter;
import com.example.sf.Domain.FilmItem;
import com.example.sf.Domain.listFilm;
import com.example.sf.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
private RecyclerView.Adapter adapterNewMovie,adapterUpComing,adapterSearch;
private RecyclerView recyclerviewNewMovies,recyclerviewUpComing,recyclerViewSearch;
private RequestQueue mRequestQueue;
private StringRequest mStringRequest1,mStringRequest2;
private ProgressBar loading1,loading2;
private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch = findViewById(R.id.editTextText3);
        recyclerViewSearch=findViewById(R.id.recyclerViewSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ne rien faire avant que le texte change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lancer la recherche de films à chaque changement de texte
                String searchString = editTextSearch.getText().toString();
                if (!searchString.isEmpty()) {
                    performSearch(searchString);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Ne rien faire après que le texte a changé
            }
        });
        initView();
        sendRequest1();
        sendRequest2();
    }
    private void performSearch(String searchString) {
        // Utilisez votre API de films pour effectuer la recherche
        String url = "https://votre-api-de-films.com/search?q=" + searchString;

        // Création de la requête de recherche
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Traitement de la réponse de l'API et mise à jour du RecyclerView
                        Gson gson = new Gson();
                        listFilm searchResults = gson.fromJson(response, listFilm.class);
                        adapterSearch = new FilmListAdapter(searchResults);
                        recyclerViewSearch.setAdapter(adapterSearch);

                        // Afficher le RecyclerView des résultats de recherche et masquer les autres
                        recyclerViewSearch.setVisibility(View.VISIBLE);
                        recyclerviewNewMovies.setVisibility(View.GONE);
                        recyclerviewUpComing.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérer les erreurs de recherche
                        Toast.makeText(MainActivity.this, "Erreur de recherche", Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajout de la requête à la file d'attente de Volley
        mRequestQueue.add(searchRequest);
    }
    private void sendRequest1() {

        mRequestQueue= Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest1=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
            Gson gson=new Gson();
            loading1.setVisibility(View.GONE);


            listFilm items=gson.fromJson(response, listFilm.class);

            adapterNewMovie=new FilmListAdapter(items);
            recyclerviewNewMovies.setAdapter(adapterNewMovie);
        }, error -> {
loading1.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest1);
    }
    private void sendRequest2() {

        mRequestQueue= Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", response -> {
            Gson gson=new Gson();
            loading2.setVisibility(View.GONE);
            listFilm items=gson.fromJson(response, listFilm.class);
            adapterUpComing=new FilmListAdapter(items);
            recyclerviewUpComing.setAdapter(adapterUpComing);
        }, error -> {
            loading2.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest2);
    }
    private void initView() {
        recyclerviewNewMovies=findViewById(R.id.rv1);
        recyclerviewNewMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerviewUpComing=findViewById(R.id.rv2);
        recyclerviewUpComing.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        loading1=findViewById(R.id.loading1);
        loading2=findViewById(R.id.loading2);
    }
}
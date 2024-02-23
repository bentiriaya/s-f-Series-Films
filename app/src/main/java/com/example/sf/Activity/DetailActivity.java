package com.example.sf.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sf.Adapter.FilmListAdapter;
import com.example.sf.Adapter.ImageListAdapter;
import com.example.sf.Domain.FilmItem;
import com.example.sf.Domain.listFilm;
import com.example.sf.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt,movieRateTxt,movieTimeTxt,movieDateTxt,movieSummaryInfo,movieActorInfo;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        idFilm=intent.getIntExtra("id",0);
        initView();
sendRequest();
    }

    private void sendRequest() {
        mRequestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/"+idFilm, response -> {
            Gson gson=new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            FilmItem item=gson.fromJson(response, FilmItem.class);
            Glide.with(DetailActivity.this).load(item.getPoster()).into(pic1);
            Glide.with(DetailActivity.this).load(item.getPoster()).into(pic2);
            titleTxt.setText(item.getTitle());
            movieRateTxt.setText(item.getRated());
            movieTimeTxt.setText(item.getRuntime());
            movieDateTxt.setText(item.getReleased());
            movieSummaryInfo.setText(item.getPlot());
            movieActorInfo.setText(item.getActors());
            if(item.getImages()!=null){
adapterImgList=new ImageListAdapter(item.getImages());
recyclerview.setAdapter(adapterImgList);
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
        });
mRequestQueue.add(mStringRequest);
    }


    private void initView() {
        titleTxt=findViewById(R.id.movieNameTxt);
        progressBar=findViewById(R.id.detailLOading);
        scrollView=findViewById(R.id.scrollView2);
        pic1=findViewById(R.id.PosterNormalImg);
        pic2=findViewById(R.id.PosterBigImg);
        movieRateTxt=findViewById(R.id.movieRateTxt);
        movieTimeTxt=findViewById(R.id.movieTimeTxt);
        movieDateTxt=findViewById(R.id.movieDateTxt);
        movieSummaryInfo=findViewById(R.id.movieSummaryTxt);
        movieActorInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.imageView9);
        recyclerview=findViewById(R.id.imagesRecyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        backImg.setOnClickListener(this::retour);



    }

    private void retour(View view) {
        finish();
    }
}
package com.example.sf.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sf.R;

public class intro extends AppCompatActivity {
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(this::getin);
    }

    private void getin(View view) {
        Intent intent=new Intent(intro.this, loginActivity.class);
        startActivity(intent);
    }
}
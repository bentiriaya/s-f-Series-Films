package com.example.sf.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sf.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class loginActivity extends AppCompatActivity {
    private EditText ed1, ed2;
    private TextView txt1, txt2;
    private AppCompatButton loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVIew();


    }

    private void initVIew() {
        ed1 = findViewById(R.id.editTextText);
        ed2 = findViewById(R.id.editTextText2);
        loginbtn = findViewById(R.id.btn);
        loginbtn.setOnClickListener(this::login);
    }

    private void login(View view) {
        String username = ed1.getText().toString();
        String psw = ed2.getText().toString();

        try {
            InputStream stream = getAssets().open("data.txt");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
                String[] t = line.split(" ");

                if (TextUtils.isEmpty(username) || !username.matches("^[A-Za-z0-9]{8}@gmail.com$")) {
                    ed1.setError("Username incorrect");
                } else if (TextUtils.isEmpty(psw)) {
                    ed2.setError("Password incorrect");
                } else if ((t[0].equals(username) || t[1].equals(username)) && t[2].equals(psw)) {
                    startActivity(new Intent(loginActivity.this, MainActivity.class));
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                }
                else if((t[0].equals(username) || t[1].equals(username)) && !t[2].equals(psw)){
                    Toast.makeText(this, "psw incorrect if you forgort ur password click on forget your password", Toast.LENGTH_SHORT).show();
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
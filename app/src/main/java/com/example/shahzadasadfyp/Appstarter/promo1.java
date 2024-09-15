package com.example.shahzadasadfyp.Appstarter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shahzadasadfyp.R;
import com.example.shahzadasadfyp.SignUp;

public class promo1 extends AppCompatActivity {

    TextView prmo1_skip;
    Button prmo1_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo1);

        prmo1_skip=findViewById(R.id.prmo1_skip);
        prmo1_continue=findViewById(R.id.prmo1_continue);

        prmo1_continue.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), promo2.class));
        });

        prmo1_skip.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
        });

       
    }
}
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

public class promo2 extends AppCompatActivity {
    TextView prmo2_skip;
    Button prmo2_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo2);

        prmo2_skip=findViewById(R.id.prmo2_skip);
        prmo2_continue=findViewById(R.id.prmo2_continue);

        prmo2_continue.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GetStarted.class));
        });

        prmo2_skip.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
        });





    }
}
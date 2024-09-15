package com.example.shahzadasadfyp.Appstarter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shahzadasadfyp.R;
import com.example.shahzadasadfyp.SignUp;

public class GetStarted extends AppCompatActivity {
    private Button getStartedButton;
    private ImageView splashImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // Initialize views
        getStartedButton = findViewById(R.id.getStartedButton);
        splashImageView = findViewById(R.id.splashImageView);

        // Load and set the bitmap for the ImageView with proper scaling
        setSplashImage();

        // Set click listener for the Get Started button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetStarted.this, promo1.class));
                finish(); // Finish the current activity
            }
        });
    }

    private void setSplashImage() {
        // Decode a scaled bitmap to avoid memory issues
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2; // adjust the sample size to reduce memory usage
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.numa, options);

        splashImageView.setImageBitmap(bitmap);
    }
}

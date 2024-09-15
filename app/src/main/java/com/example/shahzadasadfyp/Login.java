package com.example.shahzadasadfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shahzadasadfyp.AdminUI.AdminDashboard;
import com.example.shahzadasadfyp.Client.TouristDashboard;
import com.example.shahzadasadfyp.Partner.PartnerDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView userResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        userResetPassword=findViewById(R.id.userResetPassword);

        userResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveFcmToken(user.getUid()); // Save FCM token after successful login
                            redirectToDashboard(user.getUid());
                        }
                    } else {
                        Toast.makeText(Login.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void redirectToDashboard(String userId) {
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("userType").getValue(String.class);

                if ("Admin".equals(userType)) {
                    startActivity(new Intent(Login.this, AdminDashboard.class));
                } else if ("Tourist".equals(userType)) {
                    startActivity(new Intent(Login.this, TouristDashboard.class));
                } else if ("Partner".equals(userType)) {
                    startActivity(new Intent(Login.this, PartnerDashboardActivity.class));
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, "Failed to read user data"+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveFcmToken(String userId) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String token = task.getResult();
                DatabaseReference userRef = mDatabase.child("users").child(userId);
                userRef.child("fcmToken").setValue(token)
                        .addOnCompleteListener(tokenTask -> {
                            if (tokenTask.isSuccessful()) {
                                // Token saved successfully
                            } else {
                                // Handle token saving failure
                            }
                        });
            } else {
                // Handle token retrieval failure
            }
        });
    }
}

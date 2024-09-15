//package com.example.shahzadasadfyp;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.shahzadasadfyp.Model.User;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class SignUp extends AppCompatActivity {
//
//    private TextInputEditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
//    private RadioGroup userTypeRadioGroup;
//    private RadioButton touristRadioButton, partnerRadioButton;
//    private Button signUpButton, loginBtn;
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        nameEditText = findViewById(R.id.CreateUserName);
//        emailEditText = findViewById(R.id.CreateEmail);
//        passwordEditText = findViewById(R.id.CreateUserPassword);
//        confirmPasswordEditText = findViewById(R.id.CreateConfirmUserPassword);
//        userTypeRadioGroup = findViewById(R.id.userradiogroup);
//        touristRadioButton = findViewById(R.id.createTourist_radio);
//        partnerRadioButton = findViewById(R.id.createPartner_radio);
//        signUpButton = findViewById(R.id.userRegistrationBtn);
//        loginBtn=findViewById(R.id.userLoginBtn);
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Login.class));
//            }
//        });
//
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerUser();
//            }
//        });
//    }
//
//    private void registerUser() {
//        String name = nameEditText.getText().toString().trim();
//        String email = emailEditText.getText().toString().trim();
//        String password = passwordEditText.getText().toString().trim();
//        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
//        String userType = touristRadioButton.isChecked() ? "Tourist" : partnerRadioButton.isChecked() ? "Partner" : "";
//
//        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userType.isEmpty()) {
//            Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if (user != null) {
//                            writeNewUser(user.getUid(), name, email, userType);
//                            Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                            clearFields();
//                            Intent intent = new Intent(SignUp.this, Login.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    } else {
//                        Toast.makeText(SignUp.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//    private void writeNewUser(String userId, String name, String email, String userType) {
//
//        User user = new User(name, userId,email, userType, "null", "null", "null", "null","null", null);
//        mDatabase.child("users").child(userId).setValue(user);
//    }
//
//    private void clearFields() {
//        nameEditText.setText("");
//        emailEditText.setText("");
//        passwordEditText.setText("");
//        confirmPasswordEditText.setText("");
//        userTypeRadioGroup.clearCheck();
//    }
//}
package com.example.shahzadasadfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shahzadasadfyp.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private TextInputEditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private RadioGroup userTypeRadioGroup;
    private RadioButton touristRadioButton, partnerRadioButton;
    private Button signUpButton, loginBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameEditText = findViewById(R.id.CreateUserName);
        emailEditText = findViewById(R.id.CreateEmail);
        passwordEditText = findViewById(R.id.CreateUserPassword);
        confirmPasswordEditText = findViewById(R.id.CreateConfirmUserPassword);
        userTypeRadioGroup = findViewById(R.id.userradiogroup);
        touristRadioButton = findViewById(R.id.createTourist_radio);
        partnerRadioButton = findViewById(R.id.createPartner_radio);
        signUpButton = findViewById(R.id.userRegistrationBtn);
        loginBtn = findViewById(R.id.userLoginBtn);

        loginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));

        signUpButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String userType = touristRadioButton.isChecked() ? "Tourist" : partnerRadioButton.isChecked() ? "Partner" : "";

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userType.isEmpty()) {
            Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            writeNewUser(userId, name, email, userType);
                            Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            clearFields();
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(SignUp.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void writeNewUser(String userId, String name, String email, String userType) {
        User user = new User(name, userId, email, userType, "null", "null", "null", "null", "null", null);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void clearFields() {
        nameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
        userTypeRadioGroup.clearCheck();
    }
}

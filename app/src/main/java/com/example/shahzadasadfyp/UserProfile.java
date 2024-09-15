package com.example.shahzadasadfyp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shahzadasadfyp.Messaging.ChatActivity;
import com.example.shahzadasadfyp.Messaging.UserDisplaying;
import com.example.shahzadasadfyp.Model.User;
import com.example.shahzadasadfyp.UserProfilecontent.AboutApp;
import com.example.shahzadasadfyp.UserProfilecontent.LogoutBottomSheetFragment;
import com.example.shahzadasadfyp.UserProfilecontent.Profil;
import com.example.shahzadasadfyp.UserProfilecontent.UserNotification;
import com.example.shahzadasadfyp.UserProfilecontent.UserSettingpage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    private ImageView profileImage,profilechevron;
    private TextView profileName,settings,my_profile, profileEmail,about_app,notifications,my_profile_messages;

    private DatabaseReference databaseReference;
    private Button logoutTextView;
    private FirebaseUser currentUser;
    private StorageReference storageReference=storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileImage = findViewById(R.id.profileImage);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        about_app=findViewById(R.id.about_app);
        settings=findViewById(R.id.settings);
        logoutTextView = findViewById(R.id.logout);
        my_profile=findViewById(R.id.my_profile);
        profileImage=findViewById(R.id.profileImage);
        profilechevron=findViewById(R.id.profilechevron);
        my_profile_messages=findViewById(R.id.my_profile_messages);
        notifications=findViewById(R.id.notifications);

        StorageReference fileReference = storageReference.child("profileImages/");


        notifications.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UserNotification.class));
        });


        my_profile_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserDisplaying.class));
            }
        });

        profilechevron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        about_app.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), AboutApp.class));
        });


        logoutTextView.setOnClickListener(v -> {
            LogoutBottomSheetFragment bottomSheet = new LogoutBottomSheetFragment();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        settings.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), UserSettingpage.class));

        });


        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), Profil.class));

            }
        });



        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            loadUserData();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        profileName.setText(user.getName());
                        profileEmail.setText(user.getEmail());

                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                            Picasso.get().load(user.getProfileImage()).into(profileImage);
                        }
                        else {
                            profileImage.setImageResource(R.drawable.usr);
                        }

                    }
                } else {
                    Toast.makeText(UserProfile.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}

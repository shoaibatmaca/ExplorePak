
package com.example.shahzadasadfyp.AdminUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.shahzadasadfyp.Login;
import com.example.shahzadasadfyp.Messaging.UserDisplaying;
import com.example.shahzadasadfyp.Model.User;
import com.example.shahzadasadfyp.R;
import com.example.shahzadasadfyp.UserProfile;
import com.example.shahzadasadfyp.UserProfilecontent.UserNotification;
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


public class AdminDashboard extends AppCompatActivity {

    private CardView cardView1, adminchatting, adminNotification, cardView4;

    private boolean isCard1Selected = false, isCard2Selected = false, isCard3Selected = false, isCard4Selected = false;
    private ImageView user_profile,back_to_login;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference=storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseUser currentUser;
    private TextView profileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardView1 = findViewById(R.id.admin_usermanage_card);
        adminchatting = findViewById(R.id.adminchatting);
        adminNotification=findViewById(R.id.adminNotification);
        user_profile=findViewById(R.id.user_profile);
        back_to_login=findViewById(R.id.back_to_login);
        profileName=findViewById(R.id.profileName);





        databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference fileReference = storageReference.child("profileImages/");



        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            loadUserData();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }




        back_to_login.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
        });

        user_profile.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        });


        // Set onClickListeners for each card
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCard1Selected = !isCard1Selected;
                startActivity(new Intent(getApplicationContext(), UserManagementActivity.class));
            }
        });



        adminchatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserDisplaying.class));
            }
        });

        adminNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserNotification.class));
            }
        });






    }

    private void loadUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        profileName.setText(user.getName());


                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                            Picasso.get().load(user.getProfileImage()).into(user_profile);
                        }
                        if (user.getProfileImage() == null && user.getProfileImage().isEmpty()){
                            user_profile.setImageResource(R.drawable.usr);
                        }

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

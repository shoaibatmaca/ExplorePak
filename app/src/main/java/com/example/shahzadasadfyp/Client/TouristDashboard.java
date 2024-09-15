//package com.example.shahzadasadfyp.Client;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shahzadasadfyp.Adapter.HotelListAdapter;
//import com.example.shahzadasadfyp.Login;
//import com.example.shahzadasadfyp.Model.Hotel;
//import com.example.shahzadasadfyp.Model.User;
//import com.example.shahzadasadfyp.R;
//import com.example.shahzadasadfyp.UserProfile;
//import com.example.shahzadasadfyp.UserProfilecontent.UserNotification;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TouristDashboard extends AppCompatActivity {
//
//
//    private RecyclerView hotelListRecyclerView;
//    private HotelListAdapter hotelAdapter;
//    private List<Hotel> hotelList;
//    ImageView user_profile,notification_icon,back_to_login;
//    private DatabaseReference databaseReference;
//    private StorageReference storageReference=storageReference = FirebaseStorage.getInstance().getReference();
//    private FirebaseUser currentUser;
//    Button favoritesButton;
//    private EditText search_input;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tourist_dashboard);
//
//
//        user_profile=findViewById(R.id.user_profile);
//        back_to_login=findViewById(R.id.back_to_login);
//        notification_icon=findViewById(R.id.notification_icon);
//
//
////        Get User Credentional:
////        databaseReference = FirebaseDatabase.getInstance().getReference();
//        StorageReference fileReference = storageReference.child("profileImages/");
//
//
//
//        currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
//
//            loadUserData();
//        } else {
//            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
//        }
//
//
//
//
//
//        notification_icon.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), UserNotification.class));
//        });
//
//        user_profile.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), UserProfile.class));
//        });
//
//
//        back_to_login.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), Login.class));
//        });
//
//
//        // Initialize RecyclerView
//        hotelListRecyclerView = findViewById(R.id.hotel_list_recycler);
//        hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        hotelList = new ArrayList<>();
//        // Add data to your hotelList here
//
//        hotelAdapter = new HotelListAdapter(hotelList, this);
//        hotelListRecyclerView.setAdapter(hotelAdapter);
//
//
////         favoritesButton = findViewById(R.id.fvrt);
//
//
//
//
//        loadHotelData();
//    }
//
//
//
//    private void loadHotelData() {
//        DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference("PartnerHotel");
//
//        hotelRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                hotelList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Hotel hotel = snapshot.getValue(Hotel.class);
//                    hotelList.add(hotel);
//                }
//                hotelAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(TouristDashboard.this, "Failed to load hotel data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    private void loadUserData() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    User user = dataSnapshot.getValue(User.class);
//                    if (user != null) {
//
//                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
//                            Picasso.get().load(user.getProfileImage()).into(user_profile);
//                        }
//                        else{
//                            user_profile.setImageResource(R.drawable.usr);
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "User data not found", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//}
package com.example.shahzadasadfyp.Client;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.HotelListAdapter;
import com.example.shahzadasadfyp.Login;
import com.example.shahzadasadfyp.Model.Hotel;
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

import java.util.ArrayList;
import java.util.List;

public class TouristDashboard extends AppCompatActivity {

    private RecyclerView hotelListRecyclerView;
    private HotelListAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private ImageView user_profile, notification_icon, back_to_login, myfavourite;
    private DatabaseReference databaseReference;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseUser currentUser;
    private EditText search_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_dashboard);

        initializeViews();
        setupFirebase();
        setupClickListeners();
        initializeRecyclerView();
    }

    private void initializeViews() {
        user_profile = findViewById(R.id.user_profile);
        back_to_login = findViewById(R.id.back_to_login);
        notification_icon = findViewById(R.id.notification_icon);
        search_input = findViewById(R.id.search_input);
//        myfavourite=findViewById(R.id.myfavourite);
    }

    private void setupFirebase() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
            loadUserData();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupClickListeners() {
        notification_icon.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UserNotification.class));
        });

        user_profile.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        });

        back_to_login.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });

        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterHotelList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

//        myfavourite.setOnClickListener(view -> {
////            startActivity(new Intent(getApplicationContext(), MyFavourite.class));
//        });
//

    }

    private void initializeRecyclerView() {
        hotelListRecyclerView = findViewById(R.id.hotel_list_recycler);
        hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelListAdapter(hotelList, this);
        hotelListRecyclerView.setAdapter(hotelAdapter);
        loadHotelData();
    }

    private void filterHotelList(String query) {
        List<Hotel> filteredList = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            if (hotel.getHotelname().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(hotel);
            }
        }
        hotelAdapter.updateList(filteredList);
    }

    private void loadHotelData() {
        DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference("PartnerHotel");
        hotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hotelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Hotel hotel = snapshot.getValue(Hotel.class);
                    hotelList.add(hotel);
                }
                hotelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TouristDashboard.this, "Failed to load hotel data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null && user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                        Picasso.get().load(user.getProfileImage()).into(user_profile);
                    }
                    if (user == null && user.getProfileImage() == null && user.getProfileImage().isEmpty()){
                        user_profile.setImageResource(R.drawable.usr);
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


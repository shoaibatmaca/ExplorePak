package com.example.shahzadasadfyp.Client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.FavouriteHotelAdapter;
import com.example.shahzadasadfyp.Adapter.HotelListAdapter;
import com.example.shahzadasadfyp.Model.Hotel;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFavourite extends AppCompatActivity {
private ImageView back_to_touristDashboard;

    private RecyclerView recyclerView;
    private FavouriteHotelAdapter adapter;
    private List<Hotel> favoriteHotels;
    private DatabaseReference favoritesRef;
    private DatabaseReference hotelsRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        back_to_touristDashboard=findViewById(R.id.back_to_touristDashboard);
        back_to_touristDashboard.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteHotels = new ArrayList<>();
        adapter = new FavouriteHotelAdapter(this);
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        // Reference to user's favorites in Firebase
        favoritesRef = FirebaseDatabase.getInstance().getReference("Favorites").child(userId);
        // Reference to all hotels
        hotelsRef = FirebaseDatabase.getInstance().getReference("Hotels");

        loadFavoriteHotels();






    }

    private void loadFavoriteHotels() {
        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteHotels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String hotelId = snapshot.getKey();
                    hotelsRef.child(hotelId).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            Hotel hotel = task.getResult().getValue(Hotel.class);
                            if (hotel != null) {
                                favoriteHotels.add(hotel);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to load favorite hotels", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
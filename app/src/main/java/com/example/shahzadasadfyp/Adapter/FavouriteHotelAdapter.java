package com.example.shahzadasadfyp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Model.Hotel;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteHotelAdapter extends RecyclerView.Adapter<FavouriteHotelAdapter.HotelViewHolder> {
    private List<Hotel> favoriteHotels = new ArrayList<>();
    private Context context;
    private DatabaseReference favoritesRef;
    private FirebaseAuth mAuth;

    public FavouriteHotelAdapter(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        favoritesRef = FirebaseDatabase.getInstance().getReference("Favorites").child(mAuth.getCurrentUser().getUid());
        loadFavoriteHotelsFromFirebase(); // Load favorite hotels when adapter is created
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_items, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = favoriteHotels.get(position);

        holder.hotelName.setText(hotel.getHotelname());
        holder.hotelPrice.setText("$" + hotel.getStartingcharges());

        if (hotel.getHotelimg() != null && !hotel.getHotelimg().isEmpty()) {
            Picasso.get().load(hotel.getHotelimg()).into(holder.hotelImg);
        }

        holder.itemView.setOnClickListener(v -> {
            // Handle item click, if needed
            // For example, you could open the hotel's detail page or booking page
        });
    }

    @Override
    public int getItemCount() {
        return favoriteHotels.size();
    }

    private void loadFavoriteHotelsFromFirebase() {
        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteHotels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String hotelId = snapshot.getKey();
                    // Assuming you have a method to fetch the Hotel object by its ID
                    fetchHotelById(hotelId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchHotelById(String hotelId) {
        // Assuming you have a reference to the Hotels node in Firebase
        DatabaseReference hotelsRef = FirebaseDatabase.getInstance().getReference("Hotels");

        hotelsRef.child(hotelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Hotel hotel = snapshot.getValue(Hotel.class);
                if (hotel != null) {
                    favoriteHotels.add(hotel);
                    notifyDataSetChanged(); // Update the RecyclerView when a new favorite hotel is added
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelPrice;
        ImageView hotelImg;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelPrice = itemView.findViewById(R.id.hotel_price);
            hotelImg = itemView.findViewById(R.id.hotel_image);
        }
    }
}

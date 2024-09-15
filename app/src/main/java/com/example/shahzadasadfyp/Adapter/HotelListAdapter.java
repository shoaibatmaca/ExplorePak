//package com.example.shahzadasadfyp.Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shahzadasadfyp.Client.ManagePackages;
//import com.example.shahzadasadfyp.Model.Hotel;
//import com.example.shahzadasadfyp.R;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {
//    private List<Hotel> hotelList;
//    private Context context;
//    private List<Hotel> favoriteHotels = new ArrayList<>();
//
//    public HotelListAdapter(List<Hotel> hotelList, Context context) {
//        this.hotelList = hotelList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_items, parent, false);
//        return new HotelViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
//        Hotel hotel = hotelList.get(position);
//
//        if (hotel.getHotelname() != null) {
//            holder.hotelName.setText(hotel.getHotelname());
//        }
//
//        if (hotel.getStartingcharges() != null) {
//            holder.hotelPrice.setText("$" + hotel.getStartingcharges());
//        }
//
//        if (hotel.getHotelimg() != null && !hotel.getHotelimg().isEmpty()) {
//            Picasso.get().load(hotel.getHotelimg()).into(holder.hotelImg);
//        }
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ManagePackages.class);
//            intent.putExtra("hotelPartnerId", hotel.getId());
//            context.startActivity(intent);
//        });
//
//        holder.favoriteButton.setOnCheckedChangeListener(null); // Unbind listener
//
//        // Set the checked state and drawable
//        boolean isFavorite = favoriteHotels.contains(hotel);
//        holder.favoriteButton.setChecked(isFavorite);
//        holder.favoriteButton.setButtonDrawable(isFavorite ? R.drawable.ic_fullheart : R.drawable.heart);
//
//        holder.favoriteButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                if (!favoriteHotels.contains(hotel)) {
//                    favoriteHotels.add(hotel);
//                }
//                holder.favoriteButton.setButtonDrawable(R.drawable.ic_fullheart);
//            } else {
//                favoriteHotels.remove(hotel);
//                holder.favoriteButton.setButtonDrawable(R.drawable.heart);
//            }
//            notifyDataSetChanged(); // Refresh UI if needed
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return hotelList.size();
//    }
//
//    public List<Hotel> getFavoriteHotels() {
//        return favoriteHotels;
//    }
//
//    public class HotelViewHolder extends RecyclerView.ViewHolder {
//        TextView hotelName, hotelPrice;
//        ImageView hotelImg;
//        CheckBox favoriteButton;
//
//        public HotelViewHolder(@NonNull View itemView) {
//            super(itemView);
//            hotelName = itemView.findViewById(R.id.hotel_name);
//            hotelPrice = itemView.findViewById(R.id.hotel_price);
//            hotelImg=itemView.findViewById(R.id.hotel_image);
//
//            favoriteButton = itemView.findViewById(R.id.favorite_button);
//        }
//    }
//
//
//    public void updateList(List<Hotel> filteredList) {
//        hotelList = filteredList;
//        notifyDataSetChanged();
//    }
//
//}

package com.example.shahzadasadfyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Client.ManagePackages;
import com.example.shahzadasadfyp.Model.Hotel;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {
    private List<Hotel> hotelList;
    private Context context;
    private List<Hotel> favoriteHotels = new ArrayList<>();
    private DatabaseReference favoritesRef;
    private FirebaseAuth mAuth;

    public HotelListAdapter(List<Hotel> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
//        favoritesRef = FirebaseDatabase.getInstance().getReference("Favorites").child(mAuth.getCurrentUser().getUid());
//        loadFavoriteHotelsFromFirebase(); // Load favorites when adapter is created

    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_list_items, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);

        if (hotel.getHotelname() != null) {
            holder.hotelName.setText(hotel.getHotelname());
        }

        if (hotel.getStartingcharges() != null) {
            holder.hotelPrice.setText("$" + hotel.getStartingcharges());
        }

        if (hotel.getHotelimg() != null && !hotel.getHotelimg().isEmpty()) {
            Picasso.get().load(hotel.getHotelimg()).into(holder.hotelImg);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ManagePackages.class);
            intent.putExtra("hotelPartnerId", hotel.getId());
            context.startActivity(intent);
        });

//        holder.favoriteButton.setOnCheckedChangeListener(null); // Unbind listener
//
//        // Set the checked state and drawable
//        boolean isFavorite = favoriteHotels.contains(hotel);
//        holder.favoriteButton.setChecked(isFavorite);
//        holder.favoriteButton.setButtonDrawable(isFavorite ? R.drawable.ic_fullheart : R.drawable.heart);
//
//        holder.favoriteButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                if (!favoriteHotels.contains(hotel)) {
//                    favoriteHotels.add(hotel);
//                    saveToFirebase(hotel.getId(), true);
//                }
//                holder.favoriteButton.setButtonDrawable(R.drawable.ic_fullheart);
//            } else {
//                favoriteHotels.remove(hotel);
//                saveToFirebase(hotel.getId(), false);
//                holder.favoriteButton.setButtonDrawable(R.drawable.heart);
//            }
//        });
    }

    private void saveToFirebase(String hotelId, boolean isFavorite) {
        if (isFavorite) {
            favoritesRef.child(hotelId).setValue(true); // Save hotel ID in favorites
        } else {
            favoritesRef.child(hotelId).removeValue(); // Remove hotel ID from favorites
        }
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public List<Hotel> getFavoriteHotels() {
        return favoriteHotels;
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName, hotelPrice;
        ImageView hotelImg;
        CheckBox favoriteButton;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name);
            hotelPrice = itemView.findViewById(R.id.hotel_price);
            hotelImg = itemView.findViewById(R.id.hotel_image);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }

    public void updateList(List<Hotel> filteredList) {
        hotelList = filteredList;
        notifyDataSetChanged();
    }

    private void loadFavoriteHotelsFromFirebase() {
        favoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteHotels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String hotelId = snapshot.getKey();
                    for (Hotel hotel : hotelList) {
                        if (hotel.getId().equals(hotelId)) {
                            favoriteHotels.add(hotel);
                            break;
                        }
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

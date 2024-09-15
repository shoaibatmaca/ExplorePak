package com.example.shahzadasadfyp.Partner.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shahzadasadfyp.Model.Hotel;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewHotelsFragment2 extends Fragment {
    private RecyclerView recyclerView;
//    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;
    private DatabaseReference hotelRef;

    public ViewHotelsFragment2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_view_hotels2, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewHotels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        hotelList = new ArrayList<>();
//        hotelAdapter = new HotelAdapter(getContext(), hotelList);
//        recyclerView.setAdapter(hotelAdapter);

//        fetchHotelsData();
        return  v;

    }


//    private void fetchHotelsData() {
//
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//            hotelRef = FirebaseDatabase.getInstance().getReference("PartnerHotel").child(userId);
//
//            hotelRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    hotelList.clear();
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        Hotel hotel = dataSnapshot.getValue(Hotel.class);
//                        hotelList.add(hotel);
//                    }
//                    hotelAdapter.notifyDataSetChanged();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                    Toast.makeText(getContext(), "Failed to load data.", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

}
package com.example.shahzadasadfyp.Client;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.RoomAdapter;
import com.example.shahzadasadfyp.Model.Booking;
import com.example.shahzadasadfyp.Model.Room;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManagePackages extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView back_to_clientDashboard;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;
    private DatabaseReference databaseReference;
    private Spinner filterSpinner;
    private String selectedCategory = "basic"; // Default category
    private String hotelPartnerId;
    private Room selectedRoom;
    private String startDate, endDate, startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_packages);

        back_to_clientDashboard=findViewById(R.id.back_to_clientDashboard);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(this, roomList, this::onRoomSelected);
        recyclerView.setAdapter(roomAdapter);

        filterSpinner = findViewById(R.id.filterSpinner);
        setupSpinner();

        back_to_clientDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TouristDashboard.class));
            }
        });

        // Retrieve hotelPartnerId from Intent
        if (getIntent().hasExtra("hotelPartnerId")) {
            hotelPartnerId = getIntent().getStringExtra("hotelPartnerId");
            databaseReference = FirebaseDatabase.getInstance().getReference("HotelRoom")
                    .child(hotelPartnerId);
            fetchRoomData();
        } else {
            // Handle the case where hotelPartnerId is not provided
            Toast.makeText(this, "Hotel Partner ID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                fetchRoomData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optionally handle this case
            }
        });
    }

    private void showDateTimePickers() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    startDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute1) -> {
                                startTime = hourOfDay + ":" + minute1;
                                endDate = startDate;
                                endTime = startTime;
                                // Proceed with selecting end date/time
                                showEndDateTimePicker();
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showEndDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    endDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute1) -> {
                                endTime = hourOfDay + ":" + minute1;
                                // Proceed to the booking confirmation activity
                                confirmBooking();
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void onRoomSelected(Room room) {
        selectedRoom = room;
        if (selectedRoom != null) {
            showDateTimePickers();
        } else {
            Toast.makeText(this, "Please select a room", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmBooking() {
        if (selectedRoom != null && startDate != null && startTime != null && endDate != null && endTime != null) {
            Intent intent = new Intent(ManagePackages.this, BookingConfirmation.class);
            intent.putExtra("roomType", selectedRoom.getRoomType());
            intent.putExtra("userId", selectedRoom.getUserId());
            intent.putExtra("roomImageUrl", selectedRoom.getRoompic());
            intent.putExtra("price", selectedRoom.getPrice());
            intent.putExtra("startDate", startDate);
            intent.putExtra("startTime", startTime);
            intent.putExtra("endDate", endDate);
            intent.putExtra("endTime", endTime);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please complete all selections", Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchRoomData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Room room = snapshot.getValue(Room.class);
                    if (room != null && room.getRoomType().equalsIgnoreCase(selectedCategory)) {
                        roomList.add(room);
                    }
                }
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagePackages.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }







}

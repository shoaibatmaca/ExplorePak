package com.example.shahzadasadfyp.AdminUI;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.ComplaintAdapter;
import com.example.shahzadasadfyp.Model.Report;
import com.example.shahzadasadfyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComplaints;
    private ComplaintAdapter complaintAdapter;
    private List<Report> complaintList;
    private DatabaseReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        recyclerViewComplaints = findViewById(R.id.recyclerViewComplaints);

        complaintList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(complaintList, this);
        recyclerViewComplaints.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewComplaints.setAdapter(complaintAdapter);

        complaintsRef = FirebaseDatabase.getInstance().getReference("Complaints");

        loadComplaints();
    }

    private void loadComplaints() {
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Report report = dataSnapshot.getValue(Report.class);
                    if (report != null) {
                        complaintList.add(report);
                    }
                }
                complaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserManagementActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

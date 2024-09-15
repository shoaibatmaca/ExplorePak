//package com.example.shahzadasadfyp.Messaging;
//
//import android.os.Bundle;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shahzadasadfyp.Adapter.UserDisplayingAdapter;
//import com.example.shahzadasadfyp.Model.User;
//import com.example.shahzadasadfyp.R;
//import com.example.shahzadasadfyp.UserProfile;
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
//public class UserDisplaying extends AppCompatActivity {
//
//
//    private RecyclerView userdisplayingrecycler;
//    private UserDisplayingAdapter userDisplayingAdapter;
//    private List<User> userList = new ArrayList<>();
//    private String currentUserId;
//    private ImageView profileImage,backchrn,chatSearch;
//    private FirebaseUser currentUser;
//    private DatabaseReference databaseReference;
//    private StorageReference storageReference=storageReference = FirebaseStorage.getInstance().getReference();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_user_displaying);
//
//
//
//        initializeView();
//        setupFirebaseCreditional();
//
//
//
//        userdisplayingrecycler = findViewById(R.id.UserDisplayingr);
//        userdisplayingrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        userDisplayingAdapter = new UserDisplayingAdapter(this, userList);
//        userdisplayingrecycler.setAdapter(userDisplayingAdapter);
//
//        // Get the current user's ID
//        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        StorageReference fileReference = storageReference.child("profileImages/");
//
//        loadUserDisplayingContent();
//
//    }
//
//    private void setupFirebaseCreditional() {
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
//    }
//
//    private void initializeView() {
//        backchrn=findViewById(R.id.backtouristDashboard);
//        chatSearch=findViewById(R.id.chatSearch);
//        profileImage=findViewById(R.id.userPic);
//    }
//
//    private void loadUserDisplayingContent() {
//        DatabaseReference userdisplayref = FirebaseDatabase.getInstance().getReference("users");
//        userdisplayref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userList.clear();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                    User user = snapshot1.getValue(User.class);
//                    // Only add the user to the list if their ID is not the current user's ID
//                    if (user != null && !user.getUserId().equals(currentUserId)) {
//                        userList.add(user);
//                    }
//                }
//                userDisplayingAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Failed to load users", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
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
//
//                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
//                            Picasso.get().load(user.getProfileImage()).into(profileImage);
//                        }
//                        if (user.getProfileImage() == null && user.getProfileImage().isEmpty()){
//                            profileImage.setImageResource(R.drawable.usr);
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
//}


package com.example.shahzadasadfyp.Messaging;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.UserDisplayingAdapter;
import com.example.shahzadasadfyp.Model.User;
import com.example.shahzadasadfyp.R;
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

public class UserDisplaying extends AppCompatActivity {

    private RecyclerView userdisplayingrecycler;
    private UserDisplayingAdapter userDisplayingAdapter;
    private List<User> userList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();
    private String currentUserId;
    private ImageView profileImage, backchrn, chatSearch;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_displaying);

        initializeView();
        setupFirebaseCreditional();

        userdisplayingrecycler = findViewById(R.id.UserDisplayingr);
        userdisplayingrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        userDisplayingAdapter = new UserDisplayingAdapter(this, filteredUserList);
        userdisplayingrecycler.setAdapter(userDisplayingAdapter);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadUserDisplayingContent();

        chatSearch.setOnClickListener(v -> {
            searchView.setVisibility(View.VISIBLE);
            searchView.setIconified(false);
        });

        backchrn.setOnClickListener(view -> {
            finish();
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return false;
            }
        });
    }

    private void setupFirebaseCreditional() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
            loadUserData();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeView() {
        backchrn = findViewById(R.id.backtouristDashboard);
        chatSearch = findViewById(R.id.chatSearch);
        profileImage = findViewById(R.id.userPic);
        searchView = findViewById(R.id.searchView);
    }

    private void loadUserDisplayingContent() {
        DatabaseReference userdisplayref = FirebaseDatabase.getInstance().getReference("users");
        userdisplayref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                filteredUserList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    if (user != null && !user.getUserId().equals(currentUserId)) {
                        userList.add(user);
                        filteredUserList.add(user);
                    }
                }
                userDisplayingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterUsers(String query) {
        filteredUserList.clear();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredUserList.add(user);
            }
        }
        userDisplayingAdapter.notifyDataSetChanged();
    }

    private void loadUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                            Picasso.get().load(user.getProfileImage()).into(profileImage);
                        } else {
                            profileImage.setImageResource(R.drawable.usr);
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

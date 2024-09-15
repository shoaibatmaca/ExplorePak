package com.example.shahzadasadfyp.Partner.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shahzadasadfyp.Model.Room;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BlankFragment extends Fragment {



    public BlankFragment() {
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
        View view = inflater.inflate(R.layout.fragment_blank, container, false);







        return view;
    }

//    private void chooseImage() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Choose Image"), imagePickerCode);
//    }


//
//    private void uploadData() {
//        // Retrieve text data from EditTexts
//        String roomTypeStr = roomType.getText().toString();
//        String facilitiesStr = facilities.getText().toString();
//        String foodOptionsStr = foodOptions.getText().toString();
//        String relaxationServicesStr = relaxationServices.getText().toString();
//        String businessServicesStr = businessServices.getText().toString();
//        String priceStr = price.getText().toString();
//
//        // Validate that required fields are not empty
//        if (roomTypeStr.isEmpty() || facilitiesStr.isEmpty() || foodOptionsStr.isEmpty() ||
//                relaxationServicesStr.isEmpty() || businessServicesStr.isEmpty() || priceStr.isEmpty()) {
//            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            String userId = user.getUid();
//            String roomId = reference.push().getKey(); // Generate a unique ID for each room
//            Room room = new Room(roomId, roomTypeStr, facilitiesStr, foodOptionsStr,
//                    relaxationServicesStr, businessServicesStr, priceStr, userId);
//            DatabaseReference roomRef = reference.child(roomId);
//            roomRef.setValue(room).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    if (pathRRI != null) {
//                        uploadImage(roomRef);
//                    } else {
//                        Toast.makeText(getActivity(), "Successfully created!", Toast.LENGTH_SHORT).show();
//                        clearFields();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Failed to store room data", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

//    private void uploadImage(DatabaseReference roomRef) {
//
//        if (pathRRI != null) {
//            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference imageRef = storageReference.child("RoomPictures").child(userId);
//            imageRef.putFile(pathRRI)
//                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
//                        String photoUrl = uri.toString();
//                        roomRef.child("photoUrl").setValue(photoUrl).addOnCompleteListener(task -> {
//                            Toast.makeText(getActivity(), "Successfully created!", Toast.LENGTH_SHORT).show();
//                            clearFields();
//                        });
//                    }))
//                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show());
//        }
//    }

//    private void clearFields() {
//        roomType.setText("");
//        facilities.setText("");
//        foodOptions.setText("");
//        relaxationServices.setText("");
//        businessServices.setText("");
//        price.setText("");
//        roomImage.setImageResource(R.drawable.image); // Ensure placeholder image exists
//        pathRRI = null;
//    }


}

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shahzadasadfyp.Model.Hotel;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HotelRegistraionFragment extends Fragment {

    private EditText hotelNameEditText, etTotalRoom, etAddress, etContactNumber, etEmail, etWebsite, hotelDescriptionEditText,
            etCancellationPolicy, etPaymentPolicy, etStartingCharges;
    private Button btnSubmit;
    private ImageView uploadHotelImage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private static final int IMAGE_PICKER_CODE = 1;
    private Uri imageUri;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_registraion, container, false);

        // Initialize views
        initializeViews(view);

        // Firebase instances
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Set click listeners
        setClickListeners();

        return view;
    }

    private void initializeViews(View view) {
        hotelNameEditText = view.findViewById(R.id.etHotelName);
        etTotalRoom = view.findViewById(R.id.etRoomNumber);
        etAddress = view.findViewById(R.id.etAddress);
        etContactNumber = view.findViewById(R.id.etContactNumber);
        etEmail = view.findViewById(R.id.etEmail);
        etWebsite = view.findViewById(R.id.etWebsite);
        hotelDescriptionEditText = view.findViewById(R.id.etDescription);
        etCancellationPolicy = view.findViewById(R.id.etCancellationPolicy);
        etPaymentPolicy = view.findViewById(R.id.etPaymentPolicy);
        etStartingCharges = view.findViewById(R.id.etStartingCharges);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        uploadHotelImage = view.findViewById(R.id.uploadHotelImage);
    }

    private void setClickListeners() {
        btnSubmit.setOnClickListener(v -> uploadHotelData());
        uploadHotelImage.setOnClickListener(v -> chooseImage());
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), IMAGE_PICKER_CODE);
    }

    private void uploadHotelData() {
        String hotelName = hotelNameEditText.getText().toString().trim();
        String totalRoom = etTotalRoom.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contactNumber = etContactNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String website = etWebsite.getText().toString().trim();
        String description = hotelDescriptionEditText.getText().toString().trim();
        String cancellationPolicy = etCancellationPolicy.getText().toString().trim();
        String paymentPolicy = etPaymentPolicy.getText().toString().trim();
        String startingCharges = etStartingCharges.getText().toString().trim();

        if (hotelName.isEmpty() || totalRoom.isEmpty() || address.isEmpty() || contactNumber.isEmpty() ||
                email.isEmpty() || website.isEmpty() || description.isEmpty() ||
                cancellationPolicy.isEmpty() || paymentPolicy.isEmpty() || startingCharges.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference("PartnerHotel").child(userId);

//            // Create a new Hotel object
//            Hotel hotel = new Hotel(hotelName, totalRoom, address, contactNumber, email, website, description,
//                    cancellationPolicy, paymentPolicy, startingCharges, userId, null);
            Hotel hotel= new Hotel(hotelName, totalRoom, address,contactNumber, email, website, description,
                    cancellationPolicy, paymentPolicy,startingCharges, userId, "");
            // Upload hotel data
            hotelRef.setValue(hotel).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Upload image if hotel data upload is successful
                    if (imageUri != null) {
                        uploadImage(hotelRef);
                    } else {
                        Toast.makeText(getContext(), "Hotel registration successful", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to upload hotel data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadImage(DatabaseReference hotelRef) {

        if (imageUri != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference imageRef = storageReference.child("HotelImages").child(userId);
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        // Update the hotel entry with the image URL
                        hotelRef.child("hotelimg").setValue(imageUrl).addOnCompleteListener(task -> {
                            Toast.makeText(getContext(), "Hotel registration successful", Toast.LENGTH_SHORT).show();
                            clearFields();
                        });
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearFields() {
        hotelNameEditText.setText("");
        etTotalRoom.setText("");
        etAddress.setText("");
        etContactNumber.setText("");
        etEmail.setText("");
        etWebsite.setText("");
        hotelDescriptionEditText.setText("");
        etCancellationPolicy.setText("");
        etPaymentPolicy.setText("");
        etStartingCharges.setText("");
        uploadHotelImage.setImageURI(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadHotelImage.setImageURI(imageUri);
        }
    }
}

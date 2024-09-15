
package com.example.shahzadasadfyp.Partner.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.shahzadasadfyp.Model.Room;
import com.example.shahzadasadfyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadRoomDataFragment extends Fragment {
    private EditText RoomType, Amenities, FoodOption, RelaxationService, BusinessServices, RoomPrice;
    private Button UploadRoomData;
    private ImageView roomPic;
    private static final int IMAGE_PICKER_CODE = 1;
    private Uri imageUri;
    private StorageReference storageReference;

    public UploadRoomDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_room_data, container, false);

        RoomType = view.findViewById(R.id.edtRoomType);
        Amenities = view.findViewById(R.id.edtAmentities);
        FoodOption = view.findViewById(R.id.edtFoodOptions);
        RelaxationService = view.findViewById(R.id.edtRelaxationServices);
        BusinessServices = view.findViewById(R.id.edtBusinessServices);
        RoomPrice = view.findViewById(R.id.edtPrice);
        UploadRoomData = view.findViewById(R.id.UploadRoomData);
        roomPic = view.findViewById(R.id.roomPic);

        roomPic.setOnClickListener(v -> ChooseImage());
        UploadRoomData.setOnClickListener(v -> UploadRoom());

        return view;
    }

    private void UploadRoom() {
        String type = RoomType.getText().toString();
        String amnties = Amenities.getText().toString();
        String Options = FoodOption.getText().toString();
        String lxService = RelaxationService.getText().toString();
        String BussnesServices = BusinessServices.getText().toString();
        String Price = RoomPrice.getText().toString();

        if (type.isEmpty() || amnties.isEmpty() || Options.isEmpty() ||
                lxService.isEmpty() || BussnesServices.isEmpty() || Price.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("HotelRoom").child(userId).push();


            Room room = new Room(type, amnties, Options, lxService, BussnesServices, Price, "", userId);
            roomRef.setValue(room).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (imageUri != null) {
                        UploadRoomImage(roomRef);
                    } else {
                        Toast.makeText(getContext(), "Room added successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to add room", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void UploadRoomImage(DatabaseReference roomRef) {
        if (imageUri != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference imageRef = storageReference.child("HotelRoomPic").child(userId).child(imageUri.getLastPathSegment());
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        roomRef.child("roompic").setValue(imageUrl).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Room image uploaded successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to upload room image", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }))
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void ChooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), IMAGE_PICKER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            roomPic.setImageURI(imageUri);
        }
    }
}

package com.example.shahzadasadfyp.UserProfilecontent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.shahzadasadfyp.Login;
import com.example.shahzadasadfyp.R;
import com.example.shahzadasadfyp.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSettingpage extends AppCompatActivity {

    private LinearLayout accountSetting, notificationsSetting, logoutSetting, deleteAccountSetting,  setting_usernotification;
ImageView settingchevon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settingpage);

        accountSetting = findViewById(R.id.account_setting);
        notificationsSetting = findViewById(R.id.notifications_setting);
        logoutSetting = findViewById(R.id.logout_setting);
        deleteAccountSetting = findViewById(R.id.delete_account_setting);

        settingchevon=findViewById(R.id.settingchevon);

        settingchevon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Set up listeners
        accountSetting.setOnClickListener(v -> {
            // Navigate to Account Settings
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        });

        notificationsSetting.setOnClickListener(v -> {
            // Navigate to Notifications Settings
            startActivity(new Intent(getApplicationContext(), UserNotification.class));
        });

        logoutSetting.setOnClickListener(v -> {
            // Handle logout
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        deleteAccountSetting.setOnClickListener(v -> {
            // Show confirmation dialog for account deletion
            showDeleteAccountDialog();
        });


    }


    private void showDeleteAccountDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Delete", (dialog, which) -> deleteUserAccount())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteUserAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Delete user data from the database
            userRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Delete user account
                    user.delete().addOnCompleteListener(deleteTask -> {
                        if (deleteTask.isSuccessful()) {
                            Toast.makeText(UserSettingpage.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();
                            // Sign out and navigate to login
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(UserSettingpage.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserSettingpage.this, "Failed to delete account. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(UserSettingpage.this, "Failed to delete user data. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

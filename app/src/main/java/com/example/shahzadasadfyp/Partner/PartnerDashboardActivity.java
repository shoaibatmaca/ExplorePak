package com.example.shahzadasadfyp.Partner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shahzadasadfyp.Partner.Fragments.HotelRegistraionFragment;
import com.example.shahzadasadfyp.Partner.Fragments.UploadRoomDataFragment;
import com.example.shahzadasadfyp.Partner.Fragments.ViewHotelsFragment2;
import com.example.shahzadasadfyp.R;
import com.example.shahzadasadfyp.UserProfile;
import com.example.shahzadasadfyp.UserProfilecontent.UserSettingpage;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class PartnerDashboardActivity extends AppCompatActivity {

    private AnimatedBottomBar bottomBar;
    private ScrollView scrollView;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_partner_dashboard);

        // Initialize views
        bottomBar = findViewById(R.id.bottom_bar);
        scrollView = findViewById(R.id.scroll_view);
        contentLayout = findViewById(R.id.content_layout);

        // Set up tab selection listener
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                if (newIndex == 0) {
                    // Handle Home tab selection
                    loadHotelRegistrationFragment();
                }
                else if(newIndex == 1){
//                    viewHotel();

                }
                else if (newIndex == 2) {
                    // Handle Add Room tab selection
                    // Implement your logic for Add Room tab if needed
                    addroom();
                }
                else if (newIndex == 3) {

                    startUserProfileActivity();
                }
                else {
                    // Handle default or other cases
                    loadDefaultContent();
                }
            }

            @Override
            public void onTabReselected(int index, @NonNull AnimatedBottomBar.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        // Load the initial content
        loadHotelRegistrationFragment();
    }

    private void loadHotelRegistrationFragment() {
        // Load HotelRegistrationFragment
        Fragment fragment = new HotelRegistraionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
    }
private void addroom() {
        // Load HotelRegistrationFragment
        Fragment fragment = new UploadRoomDataFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
    }
    private  void viewHotel(){
        Fragment fragment= new ViewHotelsFragment2();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
    }

    private void startUserProfileActivity() {
        // Start UserProfile activity
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    private void startUserSettingPageActivity() {
        // Start UserSettingPage activity
        Intent intent = new Intent(this, UserSettingpage.class);
        startActivity(intent);
    }

    private void loadDefaultContent() {
        // Clear previous content
        contentLayout.removeAllViews();
        // Add default content
        // Example: add a TextView
        // TextView textView = new TextView(this);
        // textView.setText("Default content");
        // contentLayout.addView(textView);
    }

}

//package com.example.shahzadasadfyp.Client;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.example.shahzadasadfyp.Model.Booking;
//import com.example.shahzadasadfyp.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//public class BookingConfirmation extends AppCompatActivity {
//
//    private ImageView roomImage;
//    private TextView roomType, checkInDate, checkInTime, checkOutDate, checkOutTime,roomprice;
//    private Button bookButton;
//    private ImageView back_to_managepackages;
//    String postData;
//    WebView payement_webview;
//    private final static String paymentReturnUri= "http://localhost/order.php";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_confirmation);
//
//        roomImage = findViewById(R.id.roomImage);
//        roomType = findViewById(R.id.roomType);
//        checkInDate = findViewById(R.id.checkInDate);
//        checkInTime = findViewById(R.id.checkInTime);
//        checkOutDate = findViewById(R.id.checkOutDate);
//        checkOutTime = findViewById(R.id.checkOutTime);
//        roomprice=findViewById(R.id.roomprice);
//        bookButton = findViewById(R.id.bookButton);
//        payement_webview=findViewById(R.id.payement_webview);
//
//        back_to_managepackages=findViewById(R.id.back_to_managepackages);
//
//        back_to_managepackages.setOnClickListener(view -> {
//            startActivity(new Intent(getApplicationContext(), ManagePackages.class));
//        });
//
//        // Retrieve data from Intent
//        Intent intent = getIntent();
//        String roomTypeText = intent.getStringExtra("roomType");
//        String startDate = intent.getStringExtra("startDate");
//        String startTime = intent.getStringExtra("startTime");
//        String endDate = intent.getStringExtra("endDate");
//        String endTime = intent.getStringExtra("endTime");
//        String roomImageUrl = intent.getStringExtra("roomImageUrl");
//        String price= intent.getStringExtra("price");
//        String roomId = intent.getStringExtra("roomId");
//
//        // Set the data to views
//        roomType.setText(roomTypeText);
//        checkInDate.setText(startDate);
//        checkInTime.setText(startTime);
//        checkOutDate.setText(endDate);
//        checkOutTime.setText(endTime);
//        roomprice.setText(price);
//
//
//
////   Enabling javascript for payment gateway for rendering webview:
//        WebSettings webSettings=payement_webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        payement_webview.setWebViewClient(new WebViewClient());
//        webSettings.setDomStorageEnabled(true);
//        payement_webview.addJavascriptInterface(new FormDataInterface(), "FORMOUT");
//
//
////For payment gateway integration we need to data send to http post and page redirection:
//        String [] values=price.split("\\.");
//        price= values[0];
//        price=price + "00";
//
//        Date date= new Date();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddkkmmss");
//        String DateString = dateFormat.format(date);
//
////        convert current date to calender:
//        Calendar c= Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.HOUR, 1);
//
//
////      convert calender back to Date:
//        Date currentDateHourPlusOne=c.getTime();
//        String expireDateString= dateFormat.format(currentDateHourPlusOne);
//
//
////        For Transaction Id:
//        String TransactionIdString= "T"+ DateString;
//
////        merchant detail:
//        String pp_MerchantID="MC119128";
//        String pp_Password="9vy504041u";
//        String IntegritySalt="vvu4yb1u84";
//        String pp_ReturnURL="http://localhost/order.php";
//        String pp_Amount=price;
//        String pp_TxnDateTime=DateString;
//        String pp_TxnExpiryDateTime=expireDateString;
//        String pp_TxnRefNo=TransactionIdString;
//
//        String pp_Version= "1.1";
//        String pp_TxnType= "";
//        String pp_Language= "EN";
//        String pp_SubMerchantID= "";
//        String pp_BankID= "TBANK";
//        String pp_ProductId= roomId;
//        String pp_TxnCurrency= "PKR";
//        String pp_BillReference= "bilRef";
//        String pp_Description= "Description of transaction";
//        String pp_SecureHash= "";
//        String pp_mpf_1="1";
//        String pp_mpf_2="2";
//        String pp_mpf_3="3";
//        String pp_mpf_4="4";
//        String pp_mpf_5="5";
//
//        try{
//        postData += URLEncoder.encode("pp_version", "UTF-8")
//                + "=" + URLEncoder.encode(pp_Version,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_TxnType", "UTF-8")
//                + "=" + URLEncoder.encode(pp_TxnType,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_Language", "UTF-8")
//                + "=" + URLEncoder.encode(pp_Language,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_MerchantID", "UTF-8")
//                + "=" + URLEncoder.encode(pp_MerchantID,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_SubMerchantID", "UTF-8")
//                + "=" + URLEncoder.encode(pp_SubMerchantID,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_Password", "UTF-8")
//                + "=" + URLEncoder.encode(pp_Password,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_BankID", "UTF-8")
//                + "=" + URLEncoder.encode(pp_BankID,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_ProductID", "UTF-8")
//                + "=" + URLEncoder.encode(pp_ProductId,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_TxnRefNo", "UTF-8")
//                + "=" + URLEncoder.encode(pp_TxnRefNo,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_Amount", "UTF-8")
//                + "=" + URLEncoder.encode(pp_Amount,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_TxnCurrency", "UTF-8")
//                + "=" + URLEncoder.encode(pp_TxnCurrency,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_TxnDateTime", "UTF-8")
//                + "=" + URLEncoder.encode(pp_TxnDateTime,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_BillReference", "UTF-8")
//                + "=" + URLEncoder.encode(pp_BillReference,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_Description", "UTF-8")
//                + "=" + URLEncoder.encode(pp_Description,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_TxnExpiryDateTime", "UTF-8")
//                + "=" + URLEncoder.encode(pp_TxnExpiryDateTime,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_ReturnURL", "UTF-8")
//                + "=" + URLEncoder.encode(pp_ReturnURL,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_SecureHash", "UTF-8")
//                + "=" + URLEncoder.encode(pp_SecureHash,"UTF-8") + "&";
//
//        postData += URLEncoder.encode("pp_mpf_1", "UTF-8")
//                + "=" + URLEncoder.encode(pp_mpf_1,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_mpf_2", "UTF-8")
//                + "=" + URLEncoder.encode(pp_mpf_2,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_mpf_3", "UTF-8")
//                + "=" + URLEncoder.encode(pp_mpf_3,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_mpf_4", "UTF-8")
//                + "=" + URLEncoder.encode(pp_mpf_4,"UTF-8") + "&";
//        postData += URLEncoder.encode("pp_mpf_5", "UTF-8")
//                + "=" + URLEncoder.encode(pp_mpf_5,"UTF-8") + "&";
//
//        } catch (UnsupportedEncodingException e) {
////            throw new RuntimeException(e);
//            e.printStackTrace();
//        }
//
//
//        payement_webview.postUrl("https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/", postData.getBytes());
//
//        // Set the room image using Glide
//        if (roomImageUrl != null && !roomImageUrl.isEmpty()) {
//            Glide.with(this)
//                    .load(roomImageUrl)
//                    .placeholder(R.drawable.user)
//                    .error(R.drawable.user_b)
//                    .into(roomImage);
//        } else {
//            roomImage.setImageResource(R.drawable.user);
//        }
//
//        // Set up the book button click listener
//        bookButton.setOnClickListener(v -> confirmBooking(roomId, roomTypeText, startDate, startTime, endDate, endTime));
//    }
//
//    private void confirmBooking(String roomId, String roomType, String startDate, String startTime, String endDate, String endTime) {
//        // Check if the room is already booked
//        checkRoomAvailability(roomId, startDate, startTime, endDate, endTime, roomType);
//    }
//
//    private void checkRoomAvailability(String roomId, String startDate, String startTime, String endDate, String endTime, String roomType) {
//        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("Bookings");
//
//        bookingsRef.orderByChild("roomId").equalTo(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                boolean isRoomAvailable = true;
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Booking booking = snapshot.getValue(Booking.class);
//                    if (booking != null) {
//                        // Check if the room is booked during the selected date and time range
//                        if (isDateTimeOverlap(booking, startDate, startTime, endDate, endTime)) {
//                            isRoomAvailable = false;
//                            break;
//                        }
//                    }
//                }
//
//                if (isRoomAvailable) {
//                    // Room is available, proceed with booking
//                    saveBookingToFirebase(roomId, startDate, startTime, endDate, endTime, roomType);
//                } else {
//                    // Room is not available, notify the user
//                    Toast.makeText(BookingConfirmation.this, "Selected room is already booked for the selected dates and times.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(BookingConfirmation.this, "Failed to check room availability", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private boolean isDateTimeOverlap(Booking booking, String startDate, String startTime, String endDate, String endTime) {
//        // Convert dates and times to comparable formats
//        String bookingStart = booking.getStartDate() + " " + booking.getStartTime();
//        String bookingEnd = booking.getEndDate() + " " + booking.getEndTime();
//        String selectedStart = startDate + " " + startTime;
//        String selectedEnd = endDate + " " + endTime;
//
//        return (selectedStart.compareTo(bookingEnd) < 0) && (selectedEnd.compareTo(bookingStart) > 0);
//    }
//
//    private void saveBookingToFirebase(String roomId, String startDate, String startTime, String endDate, String endTime, String roomType) {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser != null) {
//            String userId = firebaseUser.getUid();
//            DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("Bookings").child(userId).push();
//
//            // Create a Booking object
//            String bookingId = bookingRef.getKey();
//            Booking booking = new Booking(roomId, startDate, startTime, endDate, endTime, userId, bookingId);
//
//            bookingRef.setValue(booking).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(BookingConfirmation.this, "Room booked successfully", Toast.LENGTH_SHORT).show();
//                    finish(); // Close the activity after booking
//                } else {
//                    Toast.makeText(BookingConfirmation.this, "Failed to book room", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//
//
//    private class Mywebview extends WebViewClient{
//
//
//        private final String jsCode=""+ "function parseForm(form){" +
//                "var values =''};" +
//                "for(var i=0; i< form.elements.length; i++){" +
//                " values += form.elements[i].name + '=' +form.elements[i].value+ '&'" +
//                "}"+
//                "var url=form.action;"+
//                "console.log('parse form fired');" +
//                "window.FORMOUT.processFormData(url, values);" +
//                " }" +
//                "for(var i=0; i<document.forms.length; i++){" +
//                " parseForm(document.forms[i]);"+
//                "};";
//
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            if(url.equals(paymentReturnUri)){
//                System.out.println("Datefn: return url cancelling");
//                view.stopLoading();
//            }
//            super.onPageStarted(view, url, favicon);
//
//        }
//
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if(url.equals(paymentReturnUri)){
//                return;
//            }
//
//            view.loadUrl("javascript:(function(){" + jsCode + "})()");
//            super.onPageFinished(view, url);
//        }
//
//    }
//
//    private  class FormDataInterface{
//        @JavascriptInterface
//        public void processFormData(String url, String formData){
//            Intent i= new Intent(getApplicationContext(), BookingConfirmation.class);
//
//            System.out.println("DateFn: Url:" + url + "form data"+ formData);
//            if(url.equals(paymentReturnUri)){
//                String [] values=formData.split("&");
//                for(String pair: values){
//                    String [] nameValue= pair.split("=");
//                    if(nameValue.length== 2){
//                    System.out.println("DateFn: Name" + nameValue[0]+ "values");
//                    i.putExtra(nameValue[0], nameValue[1]);
//
//                    }
//                }
//
//                setResult(RESULT_OK, i);
//                finish();
//                return;
//
//
//            }
//
//        }
//
//
//
//
//
//
//
//    }
//
//
//
//
//
//}
//
//
//
//

package com.example.shahzadasadfyp.Client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shahzadasadfyp.Model.Booking;
import com.example.shahzadasadfyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingConfirmation extends AppCompatActivity {

    private ImageView roomImage;
    private TextView roomType, checkInDate, checkInTime, checkOutDate, checkOutTime, roomPrice;
    private Button bookButton;
    private  ImageView back_to_managepackages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        roomImage = findViewById(R.id.roomImage);
        roomType = findViewById(R.id.roomType);
        checkInDate = findViewById(R.id.checkInDate);
        checkInTime = findViewById(R.id.checkInTime);
        checkOutDate = findViewById(R.id.checkOutDate);
        checkOutTime = findViewById(R.id.checkOutTime);
        roomPrice = findViewById(R.id.roomprice);
        bookButton = findViewById(R.id.bookButton);
        back_to_managepackages=findViewById(R.id.back_to_managepackages);

        back_to_managepackages.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), ManagePackages.class));
        });





        // Retrieve data from Intent
        Intent intent = getIntent();
        String roomTypeText = intent.getStringExtra("roomType");
        String startDate = intent.getStringExtra("startDate");
        String startTime = intent.getStringExtra("startTime");
        String endDate = intent.getStringExtra("endDate");
        String endTime = intent.getStringExtra("endTime");
        String roomImageUrl = intent.getStringExtra("roomImageUrl");
        String price = intent.getStringExtra("price");
        String userId=intent.getStringExtra("userId");


        // Set the data to views
        roomType.setText(roomTypeText);
        checkInDate.setText(startDate);
        checkInTime.setText(startTime);
        checkOutDate.setText(endDate);
        checkOutTime.setText(endTime);
        roomPrice.setText(price);





        // Set the room image using Glide
        if (roomImageUrl != null && !roomImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(roomImageUrl)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user_b)
                    .into(roomImage);
        } else {
            roomImage.setImageResource(R.drawable.user);
        }

        // Set up the book button click listener
        bookButton.setOnClickListener(v -> {

            confirmData();
        });
    }

    private void confirmData() {

        Intent id= getIntent();
      String userId= id.getStringExtra("userId");
        if (roomType != null && roomImage != null && roomPrice != null) {
            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);

            intent.putExtra("roomType", String.valueOf(roomType)); // Assuming roomType is a String
            intent.putExtra("roomImageUrl", String.valueOf(roomImage)); // Assuming roomImage is a String
            intent.putExtra("price", String.valueOf(roomPrice)); // Assuming roomPrice is a String or can be converted to String
            intent.putExtra("startDate", String.valueOf(checkInDate)); // Assuming checkInDate is a String
            intent.putExtra("startTime", String.valueOf(checkOutDate)); // Assuming checkOutDate is a String
            intent.putExtra("endDate", String.valueOf(checkInTime)); // Assuming checkInTime is a String
            intent.putExtra("endTime", String.valueOf(checkOutTime)); // Assuming checkOutTime is a String
            intent.putExtra("roomId", userId);


            startActivityForResult(intent, 1);

        } else {
            Toast.makeText(this, "Please complete all selections", Toast.LENGTH_SHORT).show();
        }
    }






}

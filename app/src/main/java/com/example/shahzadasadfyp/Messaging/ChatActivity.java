//package com.example.shahzadasadfyp.Messaging;
//
//import android.Manifest;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shahzadasadfyp.Adapter.MessageAdapter;
//import com.example.shahzadasadfyp.Model.Message;
//import com.example.shahzadasadfyp.Model.Notification;
//import com.example.shahzadasadfyp.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChatActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerViewMessages;
//    private EditText messageEditText;
//    private Button sendButton;
//
//    private MessageAdapter messageAdapter;
//    private List<Message> messageList;
//
//    private DatabaseReference messagesRef;
//
//    private String chatId;
//    private String currentUserId;
//    private String otherUserId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_chat);
//
//        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
//        messageEditText = findViewById(R.id.messageEditText);
//        sendButton = findViewById(R.id.sendButton);
//
//        messageList = new ArrayList<>();
//        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        otherUserId = getIntent().getStringExtra("OTHER_USER_ID");
//        chatId = getChatId(currentUserId, otherUserId);
//        messagesRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatId);
//
//        messageAdapter = new MessageAdapter(messageList, currentUserId);
//        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
//        recyclerViewMessages.setAdapter(messageAdapter);
//
//        createNotificationChannel();
//        loadMessages();
//
//        sendButton.setOnClickListener(v -> sendMessage());
//    }
//
//    private void loadMessages() {
//        messagesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messageList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Message message = dataSnapshot.getValue(Message.class);
//                    if (message != null) {
//                        messageList.add(message);
//                    }
//                }
//                messageAdapter.notifyDataSetChanged();
//                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void sendMessage() {
//        String messageText = messageEditText.getText().toString().trim();
//        if (!messageText.isEmpty()) {
//            String messageId = messagesRef.push().getKey();
//            Message message = new Message(messageId, currentUserId, otherUserId, messageText);
//
//            messagesRef.child(messageId).setValue(message)
//                    .addOnSuccessListener(aVoid -> {
//                        messageEditText.setText("");
//                        storeNotificationInDatabase(messageText);
//                        if (!isUserInChatActivity(otherUserId)) { // Avoid duplicate notifications
//                            sendNotificationToUser(messageText);
//                        }
//                    })
//                    .addOnFailureListener(e ->
//                            Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show());
//        }
//    }
//
//    private void storeNotificationInDatabase(String messageText) {
//        DatabaseReference notificationsRef = FirebaseDatabase.getInstance().getReference("Notifications").child(otherUserId);
//        String notificationId = notificationsRef.push().getKey();
//        Notification notification = new Notification(notificationId, currentUserId, otherUserId, messageText);
//
//        notificationsRef.child(notificationId).setValue(notification)
//                .addOnFailureListener(e ->
//                        Toast.makeText(ChatActivity.this, "Failed to store notification", Toast.LENGTH_SHORT).show());
//    }
//
//    private String getChatId(String userId1, String userId2) {
//        return userId1.compareTo(userId2) > 0 ? userId1 + "_" + userId2 : userId2 + "_" + userId1;
//    }
//
//    private void sendNotificationToUser(String messageText) {
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra("OTHER_USER_ID", otherUserId);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
//
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String currentUserName = snapshot.child("name").getValue(String.class);
//
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ChatActivity.this, "chat_notifications")
//                        .setSmallIcon(R.drawable.bell)
//                        .setContentTitle("New Message from " + currentUserName)
//                        .setContentText(messageText)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setAutoCancel(true)
//                        .setContentIntent(pendingIntent);
//
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatActivity.this);
//                if (ActivityCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//                    return;
//                }
//                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ChatActivity.this, "Failed to send notification", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Chat Notifications";
//            String description = "Notifications for new chat messages";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel("chat_notifications", name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    private boolean isUserInChatActivity(String userId) {
//        // Logic to check if the user is currently in the ChatActivity with the given otherUserId
//        // This can be implemented using LiveData, shared preferences, or an in-memory boolean flag
//        // For now, we'll assume the user is not in the chat activity
//        return false;
//    }
//
//
//
//}

package com.example.shahzadasadfyp.Messaging;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shahzadasadfyp.Adapter.MessageAdapter;
import com.example.shahzadasadfyp.Model.Message;
import com.example.shahzadasadfyp.Model.Notification;
import com.example.shahzadasadfyp.Model.Report;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private EditText messageEditText;
    private ImageView sendButton, backArrow, menuIcon;
    private CircleImageView chatUserImage;
    private TextView chatUserName;

    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    private DatabaseReference messagesRef;
    private String chatId;
    private String currentUserId;
    private String otherUserId;

    private FirebaseUser currentUser;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);

        // Initialize UI components
        initializeUIComponents();

        // Set up Firebase references
        setupFirebase();

        StorageReference fileReference = storageReference.child("profileImages/");

        // Load user data
        loadUserData();

        // Set up RecyclerView and Adapter
        setupRecyclerView();

        // Create notification channel
        createNotificationChannel();

        // Load messages from Firebase
        loadMessages();

        // Set up click listeners
        setupClickListeners();
    }

    private void initializeUIComponents() {
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        backArrow = findViewById(R.id.back_arrow);
        menuIcon = findViewById(R.id.menu_icon);
        chatUserImage = findViewById(R.id.chat_user_image);
        chatUserName = findViewById(R.id.chat_user_name);
    }

    private void setupFirebase() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
            otherUserId = getIntent().getStringExtra("OTHER_USER_ID");
            chatId = getChatId(currentUserId, otherUserId);
            messagesRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatId);
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);
            storageReference = FirebaseStorage.getInstance().getReference();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, currentUserId);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);
    }

    private void setupClickListeners() {
        sendButton.setOnClickListener(v -> sendMessage());

        backArrow.setOnClickListener(v -> onBackPressed());

        menuIcon.setOnClickListener(v -> showPopupMenu(v));
    }

    private void loadMessages() {
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageEditText.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String messageId = messagesRef.push().getKey();
            Message message = new Message(messageId, currentUserId, otherUserId, messageText);

            messagesRef.child(messageId).setValue(message)
                    .addOnSuccessListener(aVoid -> {
                        messageEditText.setText("");
                        storeNotificationInDatabase(messageText);
                        if (!isUserInChatActivity(otherUserId)) { // Avoid duplicate notifications
                            sendNotificationToUser(messageText);
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show());
        }
    }

    private void storeNotificationInDatabase(String messageText) {
        DatabaseReference notificationsRef = FirebaseDatabase.getInstance().getReference("Notifications").child(otherUserId);
        String notificationId = notificationsRef.push().getKey();
        Notification notification = new Notification(notificationId, currentUserId, otherUserId, messageText);

        notificationsRef.child(notificationId).setValue(notification)
                .addOnFailureListener(e ->
                        Toast.makeText(ChatActivity.this, "Failed to store notification", Toast.LENGTH_SHORT).show());
    }

    private String getChatId(String userId1, String userId2) {
        return userId1.compareTo(userId2) > 0 ? userId1 + "_" + userId2 : userId2 + "_" + userId1;
    }

    private void sendNotificationToUser(String messageText) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("OTHER_USER_ID", otherUserId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentUserName = snapshot.child("name").getValue(String.class);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ChatActivity.this, "chat_notifications")
                        .setSmallIcon(R.drawable.bell)
                        .setContentTitle("New Message from " + currentUserName)
                        .setContentText(messageText)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ChatActivity.this);
                if (ActivityCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                    return;
                }
                notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Failed to send notification", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Chat Notifications";
            String description = "Notifications for new chat messages";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("chat_notifications", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean isUserInChatActivity(String userId) {
        // Logic to check if the user is currently in the ChatActivity with the given otherUserId
        // This can be implemented using LiveData, shared preferences, or an in-memory boolean flag
        // For now, we'll assume the user is not in the chat activity
        return false;
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ChatActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.chat_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.clear_all_chat) {
                    deleteChat();
                    return true;
                } else if (item.getItemId() == R.id.report_user) {
                    reportUser();
                    return true;
                } else {
                    return false;
                }
            }
        });
        popupMenu.show();
    }

    private void deleteChat() {
        messagesRef.removeValue()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(ChatActivity.this, "Chat deleted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(ChatActivity.this, "Failed to delete chat", Toast.LENGTH_SHORT).show());
    }


    private void reportUser() {
        String reportId = FirebaseDatabase.getInstance().getReference("Reports").push().getKey();
        if (reportId != null) {
            DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("Reports").child(reportId);
            String reason = "User reported for inappropriate behavior"; // You may want to provide an interface to select/report a reason
            Report report = new Report(reportId, currentUserId, otherUserId, reason, System.currentTimeMillis());

            reportsRef.setValue(report)
                    .addOnSuccessListener(aVoid -> Toast.makeText(ChatActivity.this, "User reported successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Failed to report user", Toast.LENGTH_SHORT).show());
        }
    }

    private void blockUser() {
        DatabaseReference userBlocksRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId).child("BlockedUsers");
        userBlocksRef.child(otherUserId).setValue(true)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ChatActivity.this, "User blocked successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, you can also remove or disable chat functionality with this user
                    removeChatHistory();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Failed to block user", Toast.LENGTH_SHORT).show());
    }

    private void removeChatHistory() {
        // Remove chat history with the blocked user
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats").child(chatId);
        chatRef.removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(ChatActivity.this, "Chat history removed", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Failed to remove chat history", Toast.LENGTH_SHORT).show());
    }


    private void loadUserData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        chatUserName.setText(user.getName());
                        if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
                            Picasso.get().load(user.getProfileImage()).into(chatUserImage);
                        }
                        else {
                            chatUserImage.setImageResource(R.drawable.usr);
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

package com.veames.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "currentUserId";
    private static final String EXTRA_OTHER_USER_ID = "otherUserId";

    private TextView textViewTitle;
    private View viewCircleOnlineStatus;
    private TextView textViewOnlineStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;
    private MessagesAdapter messagesAdapter;

    private String currentUserId;
    private String otherUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Message message = new Message(
                    "text " + i,
                    currentUserId,
                    otherUserId
            );
            messages.add(message);
        }
        for (int i = 0; i < 20; i++) {
            Message message = new Message(
                    "text " + i,
                    otherUserId,
                    currentUserId
            );
            messages.add(message);
        }


        messagesAdapter.setMessages(messages);
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewCircleOnlineStatus = findViewById(R.id.viewCircleOnlineStatus);
        textViewOnlineStatus = findViewById(R.id.textViewOnlineStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

}

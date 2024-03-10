package algonquin.cst2335.rustomlab01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import algonquin.cst2335.rustomlab01.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        chatModel.messages.observe(this, messages -> {
            if (myAdapter != null) {
                myAdapter.notifyDataSetChanged();
            }
        });

        // Initialize adapter
        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @Override
            public int getItemViewType(int position) {
                ArrayList<ChatMessage> messages = chatModel.messages.getValue();
                if (messages != null && messages.get(position).isSend()) {
                    return 0; // ViewType for sent messages
                } else {
                    return 1; // ViewType for received messages
                }
            }

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view;
                if (viewType == 0) { // Sent message
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                } else { // Received message
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_message, parent, false);
                }
                return new MyRowHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ArrayList<ChatMessage> messages = chatModel.messages.getValue();
                if (messages != null && !messages.isEmpty()) {
                    ChatMessage message = messages.get(position);
                    holder.messageText.setText(message.getMessage());
                    holder.timeText.setText(message.getTimeSent());
                }
            }

            @Override
            public int getItemCount() {
                ArrayList<ChatMessage> messages = chatModel.messages.getValue();
                return messages != null ? messages.size() : 0;
            }
        };

        binding.recycleView.setAdapter(myAdapter);

        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textinput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(messageText, currentDateandTime, true);

            ArrayList<ChatMessage> currentMessages = chatModel.messages.getValue();
            if (currentMessages == null) currentMessages = new ArrayList<>();
            currentMessages.add(newMessage);
            chatModel.messages.setValue(currentMessages);
            binding.textinput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            ArrayList<ChatMessage> currentMessages = chatModel.messages.getValue();
            if (currentMessages == null) currentMessages = new ArrayList<>();


            String messageText = binding.textinput.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage(messageText, currentDateandTime, false); // false for received messages

            currentMessages.add(newMessage);
            chatModel.messages.postValue(currentMessages);
            binding.textinput.setText("");
        });

    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}

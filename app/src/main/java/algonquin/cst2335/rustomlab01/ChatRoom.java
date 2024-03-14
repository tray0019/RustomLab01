package algonquin.cst2335.rustomlab01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algonquin.cst2335.rustomlab01.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ChatRoomViewModel chatModel;

    MessageDatabase db;

    ChatMessageDAO mDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /**
         *
         */
        db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-lab8").build();
        mDAO = db.cmDAO();

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
        String dbPath = getApplicationContext().getDatabasePath("database-lab8").getAbsolutePath();
        Log.d("DatabasePath", "Database path: " + dbPath);


        binding.recycleView.setAdapter(myAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        binding.sendButton.setOnClickListener(click -> {
            String messageText = binding.textinput.getText().toString();
            ChatMessage newMessage = new ChatMessage(messageText, currentDateandTime, true);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                // Insert into database
                long messageId = mDAO.insertMessage(newMessage);
                newMessage.setId((int) messageId); // Assuming ID is int. Cast as needed.

                runOnUiThread(() -> {
                    // Add to ArrayList and update UI
                    ArrayList<ChatMessage> currentMessages = chatModel.messages.getValue();
                    if (currentMessages == null) currentMessages = new ArrayList<>();
                    currentMessages.add(newMessage);
                    chatModel.messages.postValue(currentMessages);
                    binding.textinput.setText("");
                });
            });
        });

        binding.receiveButton.setOnClickListener(click -> {
            String messageText = binding.textinput.getText().toString();
            ChatMessage newMessage = new ChatMessage(messageText, currentDateandTime, false);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                // Insert into database
                long messageId = mDAO.insertMessage(newMessage);
                newMessage.setId((int) messageId); // Assuming ID is int. Cast as needed.

                runOnUiThread(() -> {
                    // Add to ArrayList and update UI
                    ArrayList<ChatMessage> currentMessages = chatModel.messages.getValue();
                    if (currentMessages == null) currentMessages = new ArrayList<>();
                    currentMessages.add(newMessage);
                    chatModel.messages.postValue(currentMessages);
                    binding.textinput.setText("");
                });
            });
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {

                int position = getAbsoluteAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    ChatMessage messageToDelete = chatModel.messages.getValue().get(position); // Assuming chatModel is your ViewModel
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                    builder.setMessage("Are you sure you want to delete this message? " + messageText.getText())
                            .setTitle("Delete Message")
                            .setNegativeButton("No",(dialog, cl) -> {}) // No action on clicking 'No'
                            .setPositiveButton("Yes", (dialog, cl) -> {
                                chatModel.deleteMessage(messageToDelete); // Delegate deletion to ViewModel

                                final ChatMessage removedMessage = messageToDelete;
                                final int removedPosition = position;

                                ExecutorService executor = Executors.newSingleThreadExecutor();
                                executor.execute(() -> {
                                    // Delete from database
                                    mDAO.deleteMessage(messageToDelete);

                                    // Now update the UI on the main thread
                                    runOnUiThread(() -> {
                                        // Remove the message from the ArrayList
                                        ArrayList<ChatMessage> currentMessages = chatModel.messages.getValue();
                                        if (currentMessages != null) {
                                            currentMessages.remove(position);
                                            chatModel.messages.postValue(currentMessages); // Update LiveData

                                            // Notify the adapter of item removal
                                            myAdapter.notifyItemRemoved(position);

                                            Snackbar.make(messageText,"You deleted message #"+position,Snackbar.LENGTH_LONG)
                                                    .setAction("Undo", cllk -> {
                                                        executor.execute(() -> {
                                                            // Re-insert into database if necessary, then update UI
                                                            long messageId = mDAO.insertMessage(removedMessage);
                                                            removedMessage.setId((int) messageId);
                                                            runOnUiThread(() -> {
                                                                // Re-insert the message into the ArrayList and notify the adapter
                                                                chatModel.messages.getValue().add(removedPosition, removedMessage);
                                                                chatModel.messages.postValue(chatModel.messages.getValue()); // Update LiveData
                                                                myAdapter.notifyItemInserted(removedPosition);
                                                            });
                                                        });
                                                    })

                                                    .show();
                                        }
                                    });
                                });
                            })
                            .create().show(); // Display the AlertDialog
                }




            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);


        }
    }

}

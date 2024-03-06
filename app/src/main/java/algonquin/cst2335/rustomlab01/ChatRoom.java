package algonquin.cst2335.rustomlab01;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.rustomlab01.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rustomlab01.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    //private RecyclerView.Adapter myAdapter;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);


        chatModel.messages.observe(this, messages -> {
            myAdapter.notifyDataSetChanged();
        });


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.sendButton.setOnClickListener(click -> {
            ArrayList<String> currentMessages = chatModel.messages.getValue();
            if (currentMessages != null) {
                currentMessages.add(binding.textinput.getText().toString());
                chatModel.messages.postValue(currentMessages);
            }
            binding.textinput.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {



            /**
             * This function creates a ViewHolder object
             * It represents a single row in the list
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            /**
             *This initializes a ViewHolder to go at the row specified by the position parameter.
             * @param holder   The ViewHolder which should be updated to represent the contents of the
             *                 item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ArrayList<String> messages = chatModel.messages.getValue();
                if (messages != null) {
                    String message = messages.get(position);
                    holder.messageText.setText(message);
                    // Here you could also set the timeText if you had a timestamp associated with each message
                    holder.timeText.setText("");
                }
            }

            /**
             *This function just returns an int specifying how many items to draw.
             * @return
             */
            @Override
            public int getItemCount() {
                ArrayList<String> messages = chatModel.messages.getValue();
                return messages.size();
            }

            public int getItemViewType(int position){
                return 0;
            }

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
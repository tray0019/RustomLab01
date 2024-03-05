package algonquin.cst2335.rustomlab01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat_room);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}
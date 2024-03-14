package algonquin.cst2335.rustomlab01;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatRoomViewModel extends ViewModel {
        private ChatMessageDAO mDAO; // Assuming you have a way to set this, e.g., through the constructor or ViewModelFactory
        public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());

    public void deleteMessage(ChatMessage message) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                mDAO.deleteMessage(message); // Perform database operation in background

                ArrayList<ChatMessage> currentMessages = new ArrayList<>(messages.getValue());
                currentMessages.remove(message);
                messages.postValue(currentMessages); // Post updated list back to LiveData
            } catch (Exception e) {
                e.printStackTrace(); // Log the error
            }
        });

    }
    }



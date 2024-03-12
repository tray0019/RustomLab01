package algonquin.cst2335.rustomlab01;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @ColumnInfo(name="message")
         boolean isSend;

    @ColumnInfo(name="TimeSent")
        private String message;

    @ColumnInfo(name="SendOrReceive")
        private String timeSent;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public void setSend(boolean send) {
        isSend = send;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;


        public ChatMessage(String message, String timeSent, boolean isSend) {
            this.message = message;
            this.timeSent = timeSent;
            this.isSend = isSend;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        }


    public boolean isSend() {

        return isSend;
    }
}



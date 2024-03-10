package algonquin.cst2335.rustomlab01;

public class ChatMessage {
    boolean isSend;
        private String message;
        private String timeSent;


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



package algonquin.cst2335.rustomlab01;

public class ChatMessage {

        private String message;
        private String timeSent;
        private boolean isSentByUser;

        public ChatMessage(String message, String timeSent, boolean isSentByUser) {
            this.message = message;
            this.timeSent = timeSent;
            this.isSentByUser = isSentByUser;
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

        public boolean isSentByUser() {
            return isSentByUser;
        }

        public void setSentByUser(boolean sentByUser) {
            isSentByUser = sentByUser;
        }
    }



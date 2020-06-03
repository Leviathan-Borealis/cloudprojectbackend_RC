package DataWrappers;

/**
 * This class serves as a wrapper for data retrieved from the database
 */

public class LogEntry {
    private final String userId;
    private final String data;

    public LogEntry(String userId, String data) {
        this.userId = userId;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "userId='" + userId + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

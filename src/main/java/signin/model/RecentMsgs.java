package signin.model;

import java.util.Date;

public class RecentMsgs {
    private String username;
    private Date date;
    private String message;

    public RecentMsgs() {
    }

    @Override
    public String toString() {
        return "RecentMsgs{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

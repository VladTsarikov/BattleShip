package battleship.enums;

public enum NotificationInfo {

    WIN("**********YOU WIN***********"),
    LOSE("!!!!!YOU LOSE!!!!! :("),
    RIVAL_LEAVE("!!!!!RIVAL LEAVE!!!!! :("),
    SERVER_ERROR("!!!!!SERVER ERROR!!!!! :("),
    UNFORESEEN_ERROR("!!!!!UNFORESEEN ERROR!!!!! :(");

    private String notification;

    NotificationInfo(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }
}

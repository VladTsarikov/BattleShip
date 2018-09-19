package framework.enums;

public enum Numbers {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THERTEEN(13),
    FOURTEEN(14),
    FIVETEEN(15);

    private int number;

    Numbers(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}

package battleship.enums;

public enum ShipSize {

    ONE_CELL(1),
    TWO_CELL(2),
    THREE_CELL(3),
    FOUR_CELL(4);

    private int size;

    ShipSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}

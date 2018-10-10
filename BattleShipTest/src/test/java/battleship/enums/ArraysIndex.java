package battleship.enums;

public enum ArraysIndex {

    ZERO_INDEX(0),
    FIRST_INDEX(1),
    SECOND_INDEX(2),
    THIRD_INDEX(3);

    private int index;

    ArraysIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

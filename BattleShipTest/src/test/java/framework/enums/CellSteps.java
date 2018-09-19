package framework.enums;

public enum CellSteps {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private int step;

    CellSteps(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }
}

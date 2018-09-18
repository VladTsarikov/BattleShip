package framework.enums;

public enum CellStatus {

    HIT("cell__hit"),
    KILLED("ship__killed"),
    DONE("cell__done"),
    EMPTY("cell__empty"),
    MISS("cell__miss");

    private String cellStatus;

    CellStatus(String cellStatus) {
        this.cellStatus = cellStatus;
    }

    public String getCellStatus() {
        return cellStatus;
    }
}

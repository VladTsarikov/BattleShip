package battleship;

import battleship.enums.ArraysIndex;

public class BattleShipGameCoordinates {

    private int arrayIndex = 0;
    private final static int OFFSET = 1;
    private final static int ONE_CELL_STEP = 1;
    private final static int TWO_CELL_STEP = 2;
    private final static int FOUR_CELL_STEP = 4;
    private final static int SIX_CELL_STEP = 6;
    private final static int SEVEN_CELL_STEP = 7;
    private final static int EIGHT_CELL_STEP = 8;
    private int[][] fourCellShipFindsCoordinates = new int[24][2];
    private int[][] threeCellShipFindsCoordinates = new int[36][2];
    private int[][] twoCellShipFindsCoordinates = new int[50][2];
    private int[][] oneCellShipFindsCoordinates = new int[100][2];

    public BattleShipGameCoordinates() {
        generateFourCellShipsFindsCoordinates();
        generateThreeCellShipsFindsCoordinates();
        generateTwoCellShipsFindsCoordinates();
        generateOneCellShipsFindsCoordinates();
    }

    private void generateFourCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i, SIX_CELL_STEP);
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i, TWO_CELL_STEP);
        }
    }

    private void generateThreeCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, SEVEN_CELL_STEP);
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, FOUR_CELL_STEP);
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, ONE_CELL_STEP);
        }
    }

    private void generateTwoCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i,EIGHT_CELL_STEP);
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, SIX_CELL_STEP);
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, FOUR_CELL_STEP);
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, TWO_CELL_STEP);
            setOneCellCoordinates(twoCellShipFindsCoordinates,i,i);
        }
    }

    private void generateOneCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int j=0;j<10;j++){
            for(int i=0;i<=9;i++){
                setOneCellCoordinates(oneCellShipFindsCoordinates,j,i);
            }
        }
    }

    /**
     * set coordinates for two cells
     *
     */
    private void setTwoCellCoordinates(int[][] coordinates,int i, int step) {
        if ((i + step) <= 9) {
            coordinates[arrayIndex][ArraysIndex.ZERO_INDEX.getIndex()] = i;
            coordinates[arrayIndex][ArraysIndex.FIRST_INDEX.getIndex()] = i + step;
            coordinates[arrayIndex + OFFSET][ArraysIndex.FIRST_INDEX.getIndex()] = i;
            coordinates[arrayIndex + OFFSET][ArraysIndex.ZERO_INDEX.getIndex()] = i + step;
            arrayIndex += TWO_CELL_STEP;
        }
    }
    /**
     * set coordinates for one cell
     *
     */
    private void setOneCellCoordinates(int[][] coordinates,int j,int i) {
        coordinates[arrayIndex][ArraysIndex.ZERO_INDEX.getIndex()] = j;
        coordinates[arrayIndex][ArraysIndex.FIRST_INDEX.getIndex()] = i;
        arrayIndex++;
    }

    private void clearArrayIndex(){
        arrayIndex = ArraysIndex.ZERO_INDEX.getIndex();
    }

    public int[][] getFourCellShipFindsCoordinates() {
        return fourCellShipFindsCoordinates;
    }

    public int[][] getThreeCellShipFindsCoordinates() {
        return threeCellShipFindsCoordinates;
    }

    public int[][] getTwoCellShipFindsCoordinates() {
        return twoCellShipFindsCoordinates;
    }

    public int[][] getOneCellShipFindsCoordinates() {
        return oneCellShipFindsCoordinates;
    }
}

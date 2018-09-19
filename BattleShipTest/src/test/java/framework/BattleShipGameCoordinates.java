package framework;

import framework.enums.CellSteps;

public class BattleShipGameCoordinates {

    private int arrayIndex = 0;
    private final static int ZERO_INDEX = 0;
    private final static int FIRST_INDEX = 1;
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
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i, CellSteps.SIX.getStep());
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i, CellSteps.TWO.getStep());
        }
    }

    private void generateThreeCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, CellSteps.SEVEN.getStep());
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, CellSteps.FOUR.getStep());
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i, CellSteps.ONE.getStep());
        }
    }

    private void generateTwoCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, CellSteps.EIGHT.getStep());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, CellSteps.SIX.getStep());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, CellSteps.FOUR.getStep());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i, CellSteps.TWO.getStep());
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
            coordinates[arrayIndex][ZERO_INDEX] = i;
            coordinates[arrayIndex][FIRST_INDEX] = i + step;
            coordinates[arrayIndex + FIRST_INDEX][FIRST_INDEX] = i;
            coordinates[arrayIndex + FIRST_INDEX][ZERO_INDEX] = i + step;
            arrayIndex += CellSteps.TWO.getStep();
        }
    }
    /**
     * set coordinates for one cell
     *
     */
    private void setOneCellCoordinates(int[][] coordinates,int j,int i) {
        coordinates[arrayIndex][ZERO_INDEX] = j;
        coordinates[arrayIndex][FIRST_INDEX] = i;
        arrayIndex++;
    }

    private void clearArrayIndex(){
        arrayIndex = ZERO_INDEX;
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

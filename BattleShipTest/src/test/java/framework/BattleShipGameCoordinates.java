package framework;

import framework.enums.Numbers;

public class BattleShipGameCoordinates {

    private int arrayIndex = 0;
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
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i, Numbers.TWO.getNumber());
            setTwoCellCoordinates(fourCellShipFindsCoordinates,i,Numbers.SIX.getNumber());
        }
    }

    private void generateThreeCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i,Numbers.ONE.getNumber());
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i,Numbers.FOUR.getNumber());
            setTwoCellCoordinates(threeCellShipFindsCoordinates,i,Numbers.SEVEN.getNumber());
        }
    }

    private void generateTwoCellShipsFindsCoordinates(){
        clearArrayIndex();
        for(int i=0;i<=9;i++){
            setOneCellCoordinates(twoCellShipFindsCoordinates,i,i);
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i,Numbers.TWO.getNumber());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i,Numbers.FOUR.getNumber());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i,Numbers.SIX.getNumber());
            setTwoCellCoordinates(twoCellShipFindsCoordinates,i,Numbers.EIGHT.getNumber());
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

    private void setTwoCellCoordinates(int[][] coordinates,int i, int step) {
        if ((i + step) <= 9) {
            coordinates[arrayIndex][Numbers.ZERO.getNumber()] = i + step;
            coordinates[arrayIndex][Numbers.ONE.getNumber()] = i;
            coordinates[arrayIndex + Numbers.ONE.getNumber()][Numbers.ONE.getNumber()] = i + step;
            coordinates[arrayIndex + Numbers.ONE.getNumber()][Numbers.ZERO.getNumber()] = i;
            arrayIndex += Numbers.TWO.getNumber();
        }
    }

    private void setOneCellCoordinates(int[][] coordinates,int j,int i) {
        coordinates[arrayIndex][Numbers.ZERO.getNumber()] = j;
        coordinates[arrayIndex][Numbers.ONE.getNumber()] = i;
        arrayIndex++;
    }

    private void clearArrayIndex(){
        arrayIndex = Numbers.ZERO.getNumber();
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

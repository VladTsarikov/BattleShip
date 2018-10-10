package battleship;

import battleship.enums.*;
import battleship.pages.MainPage;
import org.junit.Assert;

public class BattleShipGamePlayer {

    private final static int OFFSET = 1;
    private static int ITERATOR = 0;
    private MainPage mainPage = new MainPage();

    public void playGame(){
        BattleShipGameCoordinates battleShipGameCoordinates = new BattleShipGameCoordinates();
        if(mainPage.isUserMove()){
            try{
                findShip(battleShipGameCoordinates.getFourCellShipFindsCoordinates(), ShipSize.FOUR_CELL.getSize());
                findShip(battleShipGameCoordinates.getThreeCellShipFindsCoordinates(), ShipSize.THREE_CELL.getSize());
                findShip(battleShipGameCoordinates.getTwoCellShipFindsCoordinates(), ShipSize.TWO_CELL.getSize());
                findShip(battleShipGameCoordinates.getOneCellShipFindsCoordinates(), ShipSize.ONE_CELL.getSize());
            } catch (AssertionError assertionError){
                Assert.assertTrue("Win Notification Has Not Found",mainPage.isWinNotificationPresent());
            } finally {
                mainPage.analizeReasonOfGameStopping();
            }
        }
    }

    /**
     * find necessary size ships
     * if all ships are found, the cycle is interrupted
     *
     */
    private void findShip(int[][] coordinates, int shipSize){
        for (int[] coordinate : coordinates) {
            if(mainPage.isShipsFound(shipSize)){
                break;
            }
            if (mainPage.isCellLabelAttributeContainsText(coordinate[ArraysIndex.ZERO_INDEX.getIndex()],
                    coordinate[ArraysIndex.FIRST_INDEX.getIndex()],CellStatus.EMPTY.getCellStatus())) {
                shoot(coordinate[ArraysIndex.ZERO_INDEX.getIndex()],
                        coordinate[ArraysIndex.FIRST_INDEX.getIndex()]);
                waitIfShotMiss(coordinate[ArraysIndex.ZERO_INDEX.getIndex()],coordinate[ArraysIndex.FIRST_INDEX.getIndex()]);
            }
        }
    }

    private void waitIfShotMiss(int x, int y){
        if (mainPage.isCellLabelAttributeContainsText(x,y,CellStatus.MISS.getCellStatus())) {
            waitForPlayerMove();
        }
    }

    private void waitForPlayerMove(){
        mainPage.waitVisibilityUserMoveNotification();
    }

    private void shoot(int x, int y){
        mainPage.waitPresenceOfWaitMoveLabel();
        if(ITERATOR>0){
            waitForPlayerMove();
        }
        mainPage.waitForCellIsClickable(x,y);
        mainPage.clickCellOnRivalField(x,y);
        ITERATOR++;
        if (mainPage.isCellLabelAttributeContainsText(x,y,CellStatus.HIT.getCellStatus())) {
            floodShip(x, y);
        }
    }

    /**
     * ship flooding
     * first: up
     * second:down
     * third: left
     * finally:right
     */
    private void floodShip(int x, int y){
        waitForPlayerMove();
        for (int i=x; i>0; i--) {
            if(mainPage.isShipFlooded(x,y)){
                break;
            }
            if(mainPage.isShotMiss(i-OFFSET,y)){
                break;
            }
            mainPage.clickCellOnRivalField(i-OFFSET,y);
            if(mainPage.isShotMiss(i-OFFSET,y)){
                break;
            }
        }
        waitForPlayerMove();
        for (int i=x; i<9; i++) {
            if(mainPage.isShipFlooded(x,y)){
                break;
            }
            if(mainPage.isShotMiss(i+OFFSET,y)){
                break;
            }
            mainPage.clickCellOnRivalField(i+OFFSET,y);
            if(mainPage.isShotMiss(i+OFFSET,y)){
                break;
            }
        }
        waitForPlayerMove();
        for (int i=y; i>0; i--) {
            if(mainPage.isShipFlooded(x,y)){
                break;
            }
            if(mainPage.isShotMiss(x,i-OFFSET)){
                break;
            }
            mainPage.clickCellOnRivalField(x,i-OFFSET);
            if(mainPage.isShotMiss(x,i-OFFSET)){
                break;
            }
        }
        waitForPlayerMove();
        for (int i=y; i<9; i++) {
            if(mainPage.isShipFlooded(x,y)){
                break;
            }
            if(mainPage.isShotMiss(x,i+OFFSET)){
                break;
            }
            mainPage.clickCellOnRivalField(x,i+OFFSET);
            if(mainPage.isShotMiss(x,i+OFFSET)){
                break;
            }
        }
    }
}

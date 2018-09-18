package battleship.pages;

import framework.BattleShipGameUtils;
import framework.enums.CellStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import webdriver.BaseForm;
import webdriver.elements.*;
import webdriver.waitings.SmartWait;

public class MainPage extends BaseForm {

    private final static String MAIN_LOCATOR = "//div[contains(@class,'battlefields')]";
    private final static String BATTLE_FIELD_CELL_LABEL = "//div[contains(@class,'battlefield battlefield__rival')]//div[@data-y='%s' and @data-x='%s']";
    private final static String BATTLE_FIELD_CELL_STATUS = "//div[contains(@class,'battlefield battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";
    private final static String WAIT_MOVE_LABEL = "//div[contains(@class,'battlefields clearfix')]//div[contains(@class,'self battlefield__wait')]";
    private final static String BATTLE_FIELD_CELL_LABEL_DONE = "//div[contains(@class,'battlefield battlefield__rival')]//div[@data-y='%s' and @data-x='%s']//div[contains(@class,'ship-box')]";
    private final static String SHIP_STATUS_LABEL = "//div[contains(@class,'rival')]//div[contains(@class,'ship-type__len_%s')]//span[contains(@class,'ship-part')]/..";
    private final static String SHIP_STATUS_LABE1L = "//div[contains(@class,'rival')]//div[contains(@class,'ship-type__len_%s')]//span[contains(@class,'ship-part')]/ancestor::[contains@class]";
    private final static String SHIPS_STATUS_LABEL = "//div[contains(@class,'rival')]//div[contains(@class,'ship-type__len_%s')]//span[contains(@class,'ship')]";
    private final static int COUNT_SHIPS_RANDOM_ARRANGE = 15;
    private final static int ONE = 1;
    private final Button btnArrangeShipsRandomly = new Button(By.xpath("//li[contains(@class,'variant__randomly')]"), "Arrange Ships Randomly Button");
    private final Button btnRandomRival = new Button(By.xpath("//div[contains(@class,'component-data')]//p[contains(@class,'name')]"), "Random Rival Button");
    private final Button btnStartGame = new Button(By.xpath("//div[contains(@class,'start-button')]"), "Start Game Button");
    private final Label lblUserFirstMove = new Label(By.xpath("//div[contains(@class,'game-started-move-on')]"), "User First Move Label");
    private final Label lblUserMove = new Label(By.xpath("//div[contains(@class,'notification__move-on')]"), "User Move Label");
    private final Label lblRivalFirstMove = new Label(By.xpath("//div[contains(@class,'game-started-move-off none')]"), "Rival First Move Label");
    private final Label lblRivalMove = new Label(By.xpath("//div[contains(@class,'notification__move-off none')]"), "Rival Move Label");
    private final Label lblBattleFieldCell = new Label(By.xpath("//div[contains(@class,'rival')]//div[@data-y='%s' and @data-x='%s']"), "Battle Field Cell Label");
    private final Label lblWinNotification = new Label(By.xpath("//div[contains(@class,'game-over-win')]"), "Rival Move Label");
    private final Label lblLoseNotification = new Label(By.xpath("//div[contains(@class,'game-over-lose')]"), "Rival Move Label");
    private final Label lblRivalLeaveNotification = new Label(By.xpath("//div[contains(@class,'rival-leave')]"), "Rival Move Label");
    private final Label lblServerErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Rival Move Label");
    private final Label lblUnforeseenErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Rival Move Label");
    private  Label lblShipStatus;
    private  Label lblShipsStatus;
    private  Label lblFieldCellStatus;

    public MainPage() {
        super(By.xpath(MAIN_LOCATOR), "BattleShip Main Page");
    }

    public void chooseRandomRival(){
        new Button(By.xpath("//li[contains(@class,'rival-variant__active')]//a[contains(@class,'variant-link')]"), "Random Rival Button").clickAndWait();
    }

    public void arrangeShipsRandomly(){
        int random = (int) (Math.random()*(COUNT_SHIPS_RANDOM_ARRANGE)) + ONE;
        for(int i=ONE;i<=random;i++) {
            btnArrangeShipsRandomly.clickAndWait();
        }
    }

    public void startGame(){
        new Button(By.xpath("//div[contains(@class,'start-button')]"), "Start Game Button").clickAndWait();
    }

    public void playGame(){
        if(lblUserFirstMove.isPresent() || lblUserMove.isPresent()){
            try{
                findShip(BattleShipGameUtils.fourCellShipFindsCoord,4);
                findShip(BattleShipGameUtils.threeCellShipFindsCoord,3);
                findShip(BattleShipGameUtils.twoCellShipFindsCoord,2);
                findShip(BattleShipGameUtils.oneCellShipFindsCoord,1);
            }finally {
                if(lblLoseNotification.isPresent(5)){
                    logger.fatal("YOU LOSE!");
                }else if(lblRivalLeaveNotification.isPresent(5)){
                    logger.fatal("RIVAL LEAVE!");
                }else if(lblServerErrorNotification.isPresent(5)){
                    logger.fatal("SERVER ERROR!");
                }else if(lblUnforeseenErrorNotification.isPresent(5)){
                    logger.fatal("UNFORESEEN ERROR!");
                }
            }
        }
    }


    private void findShip(int[][] coordinates, int shipSize){
        lblShipsStatus = new Label(By.xpath(String.format(SHIP_STATUS_LABEL, shipSize)));
        for (int[] coordinate : coordinates) {
            lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, coordinate[0], coordinate[1])));
            if(isShipsFound(lblShipsStatus, shipSize)){
                break;
            }
            if (lblFieldCellStatus.getAttribute("class").contains(CellStatus.EMPTY.getCellStatus())) {
                shoot(lblFieldCellStatus, coordinate[0], coordinate[1]);
                waitIfShotMiss(lblFieldCellStatus);
            }
        }
    }

    private void waitForMove(){
        SmartWait.waitFor(ExpectedConditions.presenceOfElementLocated(new Label(By.xpath(WAIT_MOVE_LABEL)).getLocator()));
    }

    private void waitIfShotMiss(Label label){
        if (label.getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
            SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
        }
    }

    private boolean isShipsFound(Label label, int shipSize){
        boolean bool = false;
        switch (shipSize) {
            case 4:
                if (label.getAttribute("class").contains(CellStatus.KILLED.getCellStatus())) {
                    bool = Boolean.TRUE;
                }
                break;
            case 3:
                if(label.getElements().get(0).getAttribute("class").contains("killed") &&
                        label.getElements().get(1).getAttribute("class").contains("killed")){
                    bool = Boolean.TRUE;
                }
                break;
            case 1:
                if(label.getElements().get(0).getAttribute("class").endsWith("killed") &&
                        label.getElements().get(1).getAttribute("class").endsWith("killed") &&
                        label.getElements().get(2).getAttribute("class").endsWith("killed") &&
                        label.getElements().get(3).getAttribute("class").endsWith("killed")){
                    logger.info("**********YOU WIN***********");
                    bool = Boolean.TRUE;
                }
        }
        return bool;
    }

    private void shoot(Label statusLabel,int x, int y){
        waitForMove();
        new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y)), "aaa").click();
        if (statusLabel.getAttribute("class").contains(CellStatus.HIT.getCellStatus())) {
            smartKill(x, y);
        }
    }

    private void isUserWin(){

    }

    private void isRivalConnect(){

    }

    private void isStillconnection(){

    }

    private void isShipHited(){

    }



    private void smartKill(int x, int y){
            for (int i = y; i > 0; i--) {
                if(isShipFlooded(x,y)){
                    break;
                }
                if(isShotMiss(x,i-1)){
                    break;
                }
                if(shootAndVerifyIfShotMiss(x,i-1)){
                    break;
                }
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y) + "/..")).getAttribute("class").contains(CellStatus.DONE.getCellStatus())) {
//                    break;
//                }
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, i - 1) + "/..")).getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
//                    break;
//                }
//                SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
//                new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, i - 1))).click();
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, i - 1) + "/..")).getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
//                    break;
//                }
            }

        //стреляю вниз от подбитого корабля
            for (int i = y; i < 9; i++) {
                if(isShipFlooded(x,y)){
                    break;
                }
                if(isShotMiss(x,i+1)){
                    break;
                }
                if(shootAndVerifyIfShotMiss(x,i+1)){
                    break;
                }
                //Прверяю не убит ли корабль
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y) + "/..")).getAttribute("class").contains(CellStatus.DONE.getCellStatus())) {
//                    break;
//                }
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, i + 1) + "/..")).getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
//                    System.out.println("22");
//                    break;
//                }
//                    SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
//                    System.out.println("222");
//                    new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, i + 1))).click();
//                    if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL,x, i + 1) + "/..")).getAttribute("class").contains("cell__miss")) {
//                        System.out.println("11");
//                        break;
//                    }
            }

        //стреляю влево от подбитого корабля
            for (int i = x; i > 0; i--) {
                if(isShipFlooded(x,y)){
                    break;
                }
                if(isShotMiss(i-1,y)){
                    break;
                }
                if(shootAndVerifyIfShotMiss(i-1,y)){
                    break;
                }
                //Прверяю не убит ли корабль
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y) + "/..")).getAttribute("class").contains("cell__done")) {
//                    break;
//                }
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i - 1, y) + "/..")).getAttribute("class").contains("cell__miss")) {
//                    System.out.println("33");
//                    break;
//                }
//                    SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
//                    System.out.println("333");
//                    new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i - 1, y))).click();
//                    if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i - 1, y) + "/..")).getAttribute("class").contains("cell__miss")) {
//                        System.out.println("11");
//                        break;
//                    }

            }

        //стреляю вправо от подбитого корабля
            for (int i = x; i <9; i++) {
                if(isShipFlooded(x,y)){
                    break;
                }
                if(isShotMiss(i+1,y)){
                    break;
                }
                if(shootAndVerifyIfShotMiss(i+1,y)){
                    break;
                }
                //Прверяю не убит ли корабль
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y) + "/..")).getAttribute("class").contains("cell__done")) {
//                    break;
//                }
//                if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i + 1, y) + "/..")).getAttribute("class").contains("cell__miss")) {
//                    System.out.println("44");
//                    break;
//                }
//                    SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
//                    System.out.println("444");
//                    new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i + 1, y))).click();
//                    if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, i + 1, y) + "/..")).getAttribute("class").contains("cell__miss")) {
//                        System.out.println("11");
//                        break;
//                    }

            }
    }

//    private boolean linearFindHitShip(int x,int y, int offsetX, int offsetY){
//        boolean bool = false;
//            if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, x, y))).getAttribute("class").contains(CellStatus.DONE.getCellStatus())) {
//                bool = Boolean.TRUE;
//            }
//            lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, offsetX, offsetY)));
//            if (lblFieldCellStatus.getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
//                bool = Boolean.TRUE;
//            }
//                SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
//                lblFieldCellStatus.click();
//            if (lblFieldCellStatus.getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
//                bool = Boolean.TRUE;
//            }
//            return bool;
//    }

    private boolean isShipFlooded(int x,int y){
        boolean bool = false;
        if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, x, y))).getAttribute("class").contains(CellStatus.DONE.getCellStatus())) {
            bool = Boolean.TRUE;
        }
        return bool;
    }

    private boolean isShotMiss(int offsetX, int offsetY){
        boolean bool = false;
        lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, offsetX, offsetY)));
        if (lblFieldCellStatus.getAttribute("class").contains(CellStatus.MISS.getCellStatus())) {
            bool = Boolean.TRUE;
        }
        return bool;
    }

    private boolean shootAndVerifyIfShotMiss(int offsetX, int offsetY){
        lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, offsetX, offsetY)));
        SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
        lblFieldCellStatus.click();
        return isShotMiss(offsetX,offsetY);
    }

}
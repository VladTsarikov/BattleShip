package battleship.pages;

import framework.BattleShipGameCoordinates;
import framework.enums.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.*;
import webdriver.waitings.SmartWait;

public class MainPage extends BaseForm {

    private final static String MAIN_LOCATOR = "//div[contains(@class,'battlefields')]";
    private final static String BATTLE_FIELD_CELL_LABEL = "//div[contains(@class,'battlefield battlefield__rival')]//div[@data-y='%s' and @data-x='%s']";
    private final static String BATTLE_FIELD_CELL_STATUS = "//div[contains(@class,'battlefield battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";
    private final static String WAIT_MOVE_LABEL = "//div[contains(@class,'battlefields clearfix')]//div[contains(@class,'self battlefield__wait')]";
    private final static String SHIP_STATUS_LABEL = "//div[contains(@class,'rival')]//div[contains(@class,'ship-type__len_%s')]//span[contains(@class,'ship-part')]/..";
    private final Button btnArrangeShipsRandomly = new Button(By.xpath("//li[contains(@class,'variant__randomly')]"), "Arrange Ships Randomly Button");
    private final Label lblUserFirstMove = new Label(By.xpath("//div[contains(@class,'game-started-move-on')]"), "User First Move Label");
    private final Label lblUserMove = new Label(By.xpath("//div[contains(@class,'notification__move-on')]"), "User Move Label");
    private final Label lblLoseNotification = new Label(By.xpath("//div[contains(@class,'game-over-lose')]"), "Lose Notification Label");
    private final Label lblRivalLeaveNotification = new Label(By.xpath("//div[contains(@class,'rival-leave')]"), "Rival Leave Notification Label");
    private final Label lblServerErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Server Error Notification Label");
    private final Label lblUnforeseenErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Unforeseen Error Notification Label");
    private  Label lblShipsStatus;
    private  Label lblFieldCellStatus;

    public MainPage() {
        super(By.xpath(MAIN_LOCATOR), "BattleShip Main Page");
    }

    public void chooseRandomRival(){
        new Button(By.xpath("//li[contains(@class,'rival-variant__active')]//a[contains(@class,'variant-link')]"),
                "Random Rival Button").clickAndWait();
    }

    public void arrangeShipsRandomly(){
        int random = (int) (Math.random()*(Numbers.FIVETEEN.getNumber())) + Numbers.ONE.getNumber();
        for(int i=1;i<=random;i++) {
            btnArrangeShipsRandomly.clickAndWait();
        }
    }

    public void startGame(){
        new Button(By.xpath("//div[contains(@class,'start-button')]"), "Start Game Button").clickAndWait();
    }

    public void playGame(){
        BattleShipGameCoordinates battleShipGameCoordinates = new BattleShipGameCoordinates();
        if(lblUserFirstMove.isPresent(Browser.getTimeoutForElementDisplayed()) || lblUserMove.isPresent(Browser.getTimeoutForElementDisplayed())){
            try{
                findShip(battleShipGameCoordinates.getFourCellShipFindsCoordinates(),Numbers.FOUR.getNumber());
                findShip(battleShipGameCoordinates.getThreeCellShipFindsCoordinates(),Numbers.THREE.getNumber());
                findShip(battleShipGameCoordinates.getTwoCellShipFindsCoordinates(),Numbers.TWO.getNumber());
                findShip(battleShipGameCoordinates.getOneCellShipFindsCoordinates(),Numbers.ONE.getNumber());
            }finally {
                if(lblLoseNotification.isPresent(Browser.getTimeoutForElementDisplayed())){
                    logger.fatal(NotificationInfo.LOSE.getNotification());
                }else if(lblRivalLeaveNotification.isPresent(Browser.getTimeoutForElementDisplayed())){
                    logger.fatal(NotificationInfo.RIVAL_LEAVE.getNotification());
                }else if(lblServerErrorNotification.isPresent(Browser.getTimeoutForElementDisplayed())){
                    logger.fatal(NotificationInfo.SERVER_ERROR.getNotification());
                }else if(lblUnforeseenErrorNotification.isPresent(Browser.getTimeoutForElementDisplayed())){
                    logger.fatal(NotificationInfo.UNFORESEEN_ERROR.getNotification());
                }
            }
        }
    }

    private void findShip(int[][] coordinates, int shipSize){
        lblShipsStatus = new Label(By.xpath(String.format(SHIP_STATUS_LABEL, shipSize)),
                String.format("Ships Size%s Status Label",shipSize));
        for (int[] coordinate : coordinates) {
            lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS,
                    coordinate[Numbers.ZERO.getNumber()], coordinate[Numbers.ONE.getNumber()])),
                    String.format("Cell With Coordinates:x=%s y=%s Status Label",
                            coordinate[Numbers.ONE.getNumber()],coordinate[Numbers.ZERO.getNumber()]));
            if(isShipsFound(lblShipsStatus, shipSize)){
                break;
            }
            if (lblFieldCellStatus.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.EMPTY.getCellStatus())) {
                shoot(lblFieldCellStatus, coordinate[Numbers.ZERO.getNumber()], coordinate[Numbers.ONE.getNumber()]);
                waitIfShotMiss(lblFieldCellStatus);
            }
        }
    }

    private void waitForMove(){
        SmartWait.waitFor(ExpectedConditions.presenceOfElementLocated(new Label(By.xpath(WAIT_MOVE_LABEL)).getLocator()));
    }

    private void waitIfShotMiss(Label label){
        if (label.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.MISS.getCellStatus())) {
            SmartWait.waitFor(ExpectedConditions.visibilityOf(lblUserMove.getElement()));
        }
    }

    private boolean isShipsFound(Label label, int shipSize){
        boolean bool = false;
        switch (shipSize) {
            case 4:
                if (label.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.KILLED.getCellStatus())) {
                    bool = Boolean.TRUE;
                }
                break;
            case 3:
                if(label.getElements().get(Numbers.ZERO.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                        .contains(CellStatus.KILLED.getCellStatus()) &&
                        label.getElements().get(Numbers.ONE.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                                .contains(CellStatus.KILLED.getCellStatus())){
                    bool = Boolean.TRUE;
                }
                break;
            case 1:
                if(label.getElements().get(Numbers.ZERO.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                        .endsWith(CellStatus.KILLED.getCellStatus()) &&
                        label.getElements().get(Numbers.ONE.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                                .endsWith(CellStatus.KILLED.getCellStatus()) &&
                        label.getElements().get(Numbers.TWO.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                                .endsWith(CellStatus.KILLED.getCellStatus()) &&
                        label.getElements().get(Numbers.THREE.getNumber()).getAttribute(ElementAttributeName.CLASS.getName())
                                .endsWith(CellStatus.KILLED.getCellStatus())){
                    logger.info(NotificationInfo.WIN.getNotification());
                    bool = Boolean.TRUE;
                }
        }
        return bool;
    }

    private void shoot(Label statusLabel,int x, int y){
        waitForMove();
        new Label(By.xpath(String.format(BATTLE_FIELD_CELL_LABEL, x, y)),
                String.format("Cell Label With Coordinates: x=%s : y=%s",y,x)).click();
        if (statusLabel.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.HIT.getCellStatus())) {
            smartKill(x, y);
        }
    }

    private void smartKill(int x, int y){
        for (int i = y; i > 0; i--) {
            if(isShipFlooded(x,y)){
                break;
            }
            if(isShotMiss(x,i-Numbers.ONE.getNumber())){
                break;
            }
            if(shootAndVerifyIfShotMiss(x,i-Numbers.ONE.getNumber())){
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
            if(isShotMiss(x,i+Numbers.ONE.getNumber())){
                break;
            }
            if(shootAndVerifyIfShotMiss(x,i+Numbers.ONE.getNumber())){
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
            if(isShotMiss(i-Numbers.ONE.getNumber(),y)){
                break;
            }
            if(shootAndVerifyIfShotMiss(i-Numbers.ONE.getNumber(),y)){
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
            if(isShotMiss(i+Numbers.ONE.getNumber(),y)){
                break;
            }
            if(shootAndVerifyIfShotMiss(i+Numbers.ONE.getNumber(),y)){
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
        if (new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, x, y))).getAttribute(ElementAttributeName.CLASS.getName())
                .contains(CellStatus.DONE.getCellStatus())) {
            bool = Boolean.TRUE;
        }
        return bool;
    }

    private boolean isShotMiss(int offsetX, int offsetY){
        boolean bool = false;
        lblFieldCellStatus = new Label(By.xpath(String.format(BATTLE_FIELD_CELL_STATUS, offsetX, offsetY)));
        if (lblFieldCellStatus.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.MISS.getCellStatus())) {
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
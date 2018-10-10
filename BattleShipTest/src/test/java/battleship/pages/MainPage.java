package battleship.pages;

import battleship.enums.*;
import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.BoolUtils;
import webdriver.Browser;
import webdriver.elements.*;
import webdriver.enums.ElementAttributeName;

public class MainPage extends BaseForm {

    private final static String MAIN_LOCATOR = "//div[contains(@class,'battlefields')]";
    private final static String FORMAT_CELL_LABEL_LOCATOR = "//div[contains(@class,'battlefield__rival')]//div[@data-y='%s' and @data-x='%s']";
    private final static String FORMAT_CELL_STATUS_LOCATOR = "//div[contains(@class,'battlefield__rival')]//div[@data-y='%s' and @data-x='%s']/..";
    private final static String WAIT_MOVE_LABEL_LOCATOR = "//div[contains(@class,'clearfix')]//div[contains(@class,'self') and contains(@class,'wait')]";
    private final static String FORMAT_SHIP_STATUS_LABEL_LOCATOR = "//div[contains(@class,'rival')]//div[contains(@class,'ship-type__len_%s')]//span[contains(@class,'ship-part')]/..";
    private final Label lblUserFirstMove = new Label(By.xpath("//div[contains(@class,'game-started-move-on')]"), "User First Move Label");
    private final Label lblUserMove = new Label(By.xpath("//div[contains(@class,'notification__move-on')]"), "User Move Label");
    private final Label lblLoseNotification = new Label(By.xpath("//div[contains(@class,'game-over-lose')]"), "Lose Notification Label");
    private final Label lblWinNotification = new Label(By.xpath("//div[contains(@class,'game-over-win')]"), "Win Notification Label");
    private final Label lblRivalLeaveNotification = new Label(By.xpath("//div[contains(@class,'rival-leave')]"), "Rival Leave Notification Label");
    private final Label lblServerErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Server Error Notification Label");
    private final Label lblUnforeseenErrorNotification = new Label(By.xpath("//div[contains(@class,'server-error')]"), "Unforeseen Error Notification Label");
    private final static int OFFSET = 1;

    public MainPage() {
        super(By.xpath(MAIN_LOCATOR), "BattleShip Main Page");
    }

    public void chooseRandomRival(){
        new Button(By.xpath("//li[contains(@class,'rival-variant__active')]//a[contains(@class,'variant-link')]"),
                "Random Rival Button").clickAndWait();
    }

    public void startGame(){
        new Button(By.xpath("//div[contains(@class,'start-button')]"), "Start Game Button").clickAndWait();
    }

    /**
     * return true if necessary size ship is found
     *
     */
    public boolean isShipsFound(int shipSize){
        Label lblShipsStatus = new Label(By.xpath(String.format(FORMAT_SHIP_STATUS_LABEL_LOCATOR, shipSize)),
                String.format("Ships Size%s Status Label", shipSize));
        boolean bool = false;
        switch (shipSize) {
            case 4:
                if (lblShipsStatus.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.KILLED.getCellStatus())) {
                    bool = Boolean.TRUE;
                }
                break;
            case 3:
                if(lblShipsStatus.getElements().get(ArraysIndex.ZERO_INDEX.getIndex()).getAttribute(ElementAttributeName.CLASS.getName())
                        .contains(CellStatus.KILLED.getCellStatus()) &&
                        lblShipsStatus.getElements().get(ArraysIndex.FIRST_INDEX.getIndex()).getAttribute(ElementAttributeName.CLASS.getName())
                                .contains(CellStatus.KILLED.getCellStatus())){
                    bool = Boolean.TRUE;
                }
                break;
        }
        return bool;
    }

    public void arrangeShipsRandomly(int randomlyFindCount) {
        int random = (int) (Math.random() * (randomlyFindCount)) + OFFSET;
        for (int i = 1; i <= random; i++) {
            new Button(By.xpath("//li[contains(@class,'variant__randomly')]"), "Arrange Ships Randomly Button").clickAndWait();
        }
    }

    public void clickCellOnRivalField(int x, int y){
        new Label(By.xpath(String.format(FORMAT_CELL_LABEL_LOCATOR, x, y)),
                String.format("Cell Label With Coordinates: x=%s : y=%s",y,x)).clickViaJS();
    }

    public boolean isShipFlooded(int x, int y){
        return BoolUtils.getBoolValueByCondition(new Label(By.xpath(String.format(FORMAT_CELL_STATUS_LOCATOR, x, y)),
                String.format("Cell With Coordinates:x=%s y=%s Status Label", y,x))
                .getAttribute(ElementAttributeName.CLASS.getName())
                .contains(CellStatus.DONE.getCellStatus()));
    }

    public boolean isShotMiss(int x, int y){
        Label lblFieldCellStatus = new Label(By.xpath(String.format(FORMAT_CELL_STATUS_LOCATOR, x, y)),
                String.format("Cell With Coordinates:x=%s y=%s Status Label", y,x));
        return BoolUtils.getBoolValueByCondition(lblFieldCellStatus.getAttribute(ElementAttributeName.CLASS.getName()).contains(CellStatus.MISS.getCellStatus()));
    }

    public boolean isWinNotificationPresent(){
        return BoolUtils.getBoolValueByCondition(lblWinNotification.isPresent(Browser.getTimeoutForElementDisplayed()));
    }

    public boolean isCellLabelAttributeContainsText(int x, int y, String text){
        return BoolUtils.getBoolValueByCondition(new Label(By.xpath(String.format(FORMAT_CELL_STATUS_LOCATOR,x,y)),
                String.format("Cell With Coordinates:x=%s y=%s Status Label", y,x))
                .getAttribute(ElementAttributeName.CLASS.getName()).contains(text));
    }

    public boolean isUserMove(){
        return BoolUtils.getBoolValueByCondition(lblUserFirstMove.isPresent(Browser.getTimeoutForElementDisplayed())
                || lblUserMove.isPresent(Browser.getTimeoutForElementDisplayed()));
    }

    public void waitVisibilityUserMoveNotification(){
        lblUserMove.waitForVisibilityOfElement();
    }

    public void waitPresenceOfWaitMoveLabel(){
        new Label(By.xpath(WAIT_MOVE_LABEL_LOCATOR), "Wait Move Label").waitForPresenceOfElementLocated();
    }

    public void waitForCellIsClickable(int x, int y){
        new Label(By.xpath(String.format(FORMAT_CELL_LABEL_LOCATOR, x, y)),
                String.format("Cell Label With Coordinates: x=%s : y=%s",y,x)).waitForElementClickable();
    }

    public void analizeReasonOfGameStopping(){
        while(true) {
            if (lblLoseNotification.isPresent(Browser.getTimeoutForElementDisplayed())) {
                logger.fatal(NotificationInfo.LOSE.getNotification());
                break;
            } else if (lblWinNotification.isPresent(Browser.getTimeoutForElementDisplayed())) {
                logger.fatal(NotificationInfo.WIN.getNotification());
                break;
            }else if (lblRivalLeaveNotification.isPresent(Browser.getTimeoutForElementDisplayed())) {
                logger.fatal(NotificationInfo.RIVAL_LEAVE.getNotification());
                break;
            }
            else if (lblServerErrorNotification.isPresent(Browser.getTimeoutForElementDisplayed())) {
                logger.fatal(NotificationInfo.SERVER_ERROR.getNotification());
            } else if (lblUnforeseenErrorNotification.isPresent(Browser.getTimeoutForElementDisplayed())) {
                logger.fatal(NotificationInfo.UNFORESEEN_ERROR.getNotification());
            }
            break;
        }
    }
}
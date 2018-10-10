package battleship.tests;

import battleship.BattleShipGamePlayer;
import battleship.pages.*;
import webdriver.BaseTest;

public class BattleShipGameTest extends BaseTest {

    private final static int RANDOMLY_FIND_COUNT = 15;

    @Override
    public void runTest() {
        logStep(1, "OPENING battleship.com...");
        MainPage mainPage = new MainPage();

        logStep(2, "CHOOSE RANDOM RIVAL...");
        mainPage.chooseRandomRival();

        logStep(3, "ARRANGE SHIPS RANDOMLY...");
        mainPage.arrangeShipsRandomly(RANDOMLY_FIND_COUNT);

        logStep(4, "STARTING GAME AND WAITING RIVAL...");
        mainPage.startGame();

        logStep(5, "PLAYING GAME...");
        BattleShipGamePlayer battleShipGamePlayer = new BattleShipGamePlayer();
        battleShipGamePlayer.playGame();
    }
}
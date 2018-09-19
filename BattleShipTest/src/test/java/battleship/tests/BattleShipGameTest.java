package battleship.tests;

import battleship.pages.*;
import webdriver.BaseTest;

public class BattleShipGameTest extends BaseTest {

    @Override
    public void runTest() {
        logStep(1, "OPENING battleship.com...");
        MainPage mainPage = new MainPage();

        logStep(2, "CHOOSE RANDOM RIVAL...");
        mainPage.chooseRandomRival();

        logStep(3, "ARRANGE SHIPS RANDOMLY...");
        mainPage.arrangeShipsRandomly();

        logStep(4, "STARTING GAME AND WAITING RIVAL...");
        mainPage.startGame();

        logStep(5, "PLAYING GAME...");
        mainPage.playGame();
    }
}
package webdriver;

import org.openqa.selenium.By;
import webdriver.elements.Label;

public class BaseMenu {

    private String menuLocator;

    protected BaseMenu(String menuLocator){
        this.menuLocator = menuLocator;
    }

    public void clickMenuLabel(String labelName) {
        new Label(By.xpath(String.format(menuLocator, labelName)),
                labelName + " Label").clickAndWait();
    }
}

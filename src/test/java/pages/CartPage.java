package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private By cartbutton = By.id("nav-cart");
    private By proceedtobuybutton = By.xpath("//input[@name='proceedToRetailCheckout' and @value='Proceed to checkout']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
        this.actions = new Actions(driver);
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void redirecttocart() {
        waitForElementToBeClickable(cartbutton).click();
    }
    public void proceedtobuy() {
        waitForElementToBeClickable(proceedtobuybutton).click();
    }

}


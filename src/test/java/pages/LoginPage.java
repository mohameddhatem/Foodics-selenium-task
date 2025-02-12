package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By emailField = By.id("ap_email");
    private By passwordField = By.id("ap_password");
    private By continueLoginButton = By.className("a-button-input");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set explicit wait duration
    }

    private WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void login(String email, String password) {
        waitForElementVisible(emailField).sendKeys(email);
        waitForElementClickable(continueLoginButton).click();
        waitForElementVisible(passwordField).sendKeys(password);
        waitForElementClickable(continueLoginButton).click();
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By clicksign = By.xpath("//a[@id='nav-link-accountList']/span");
    private By allMenu = By.id("nav-hamburger-menu");
    private By seeAllCategories = By.xpath("//a[@aria-label='See All Categories']");
    private By videoGamesLink = By.xpath("//a[@class='hmenu-item' and @data-menu-id='16']");
    private By allVideoGames = By.xpath("//a[@class='hmenu-item' and contains(text(), 'All Video Games')]");

    public HomePage(WebDriver driver) {
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

    public void clickSignin() {
        waitForElementToBeClickable(clicksign).click();
    }

    public void navigateToVideoGames() {
        waitForElementToBeClickable(allMenu).click();
        waitForElementVisible(seeAllCategories).click();
        System.out.println("Clicked 'See All Categories'");

        WebElement videoGamesElement = waitForElementVisible(videoGamesLink);
        actions.moveToElement(videoGamesElement).perform();
        videoGamesElement.click();
        System.out.println("Video Games clicked");

        // Small delay to allow the menu to expand
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Use JavaScript Executor to click 'All Video Games'
        WebElement allVideoGamesElement = wait.until(ExpectedConditions.elementToBeClickable(allVideoGames));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", allVideoGamesElement);
        System.out.println("All Video Games clicked via JavaScript");
    }


}

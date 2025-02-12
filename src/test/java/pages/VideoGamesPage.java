package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VideoGamesPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    private By clickfreeshippingicon = By.xpath("//i[contains(@class, 'a-icon-checkbox')]");
    private By clicknew = By.xpath("//li[@id='p_n_condition-type/28071525031']//span[@class='a-size-base a-color-base' and text()='New']");
    private By dropdown = By.className("a-dropdown-label");
    private By priceHighToLowFilter = By.xpath("//a[text()='Price: High to Low']");
    private By productWholePrice = By.xpath("//span[@class='a-price-whole']");
    private By productDecimalPrice = By.xpath("//span[@class='a-price-decimal']");
    private By addtocart = By.xpath("//button[contains(@aria-label, 'Add to cart') and contains(@class, 'a-button-text')]");

    public VideoGamesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static double extractPrice(String priceText) {
        String numericPrice = priceText.replaceAll("[^\\d.]", "");
        double price = 0.0;
        if (!numericPrice.isEmpty()) {
            try {
                price = Double.parseDouble(numericPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format: " + priceText);
            }
        }
        return price;
    }

    public void addVideoGameToCart() {
        waitForElementToBeClickable(clickfreeshippingicon).click();
        System.out.println("Clicked 'free shipping'");

        waitForElementToBeClickable(clicknew).click();
        System.out.println("Clicked 'New'");

        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        dropdownElement.click();
        System.out.println("Clicked 'dropdown'");

        WebElement priceOption = wait.until(ExpectedConditions.elementToBeClickable(priceHighToLowFilter));
        priceOption.click();
        System.out.println("Clicked 'High to low'");

        // Wait for the product list to update after applying filters
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productWholePrice));
        System.out.println("Products are now filtered and loaded.");

        // Get the updated product price elements using the correct XPath
        List<WebElement> priceElements = driver.findElements(productWholePrice);

        if (priceElements.isEmpty()) {
            System.out.println("No products found after filtering.");
            return;
        }

        // Set to keep track of products that have been added to the cart
        Set<WebElement> addedToCart = new HashSet<>();

        // Loop through each product to extract the price and apply the filter
        for (int i = 0; i < priceElements.size(); i++) {
            WebElement priceElement = priceElements.get(i);

            try {
                // Extract the price text (whole part of the price)
                String wholePrice = priceElement.getText().replaceAll("[^\\d.]", "").trim();
                System.out.println("Extracted price: " + wholePrice);

                double price = extractPrice(wholePrice);

                // Check if the price is under 15000 EGP
                if (price < 15000) {
                    // Locate the "Add to Cart" button relative to the price element
                    WebElement addToCartButton = driver.findElement(By.xpath("//button[contains(@aria-label, 'Add to cart')]"));
                    actions.moveToElement(addToCartButton).perform();



                    // Ensure that the 'Add to Cart' button is displayed and clickable
                    if (addToCartButton.isDisplayed() && addToCartButton.isEnabled()) {
                        // Ensure the product has not been added before
                        if (!addedToCart.contains(priceElement)) {
                            addToCartButton.click();
                            addedToCart.add(priceElement); // Track this product as added
                            System.out.println("Added product to cart with price: " + wholePrice);

                            // Wait to ensure the cart is updated (or the product is added)
                            Thread.sleep(500); // Add slight delay to ensure the page responds
                        } else {
                            System.out.println("Product already added to cart: " + wholePrice);
                        }

                        // Optionally, you can break the loop after adding the first valid product
                        break; // Remove this if you want to continue processing all products
                    } else {
                        System.out.println("Add to Cart button not clickable for product: " + wholePrice);
                    }
                } else {
                    System.out.println("Product too expensive: " + wholePrice);
                }

            } catch (Exception e) {
                System.out.println("Error processing product: " + e.getMessage());
            }

            // Add a small delay to allow for switching to the next product
            try {
                Thread.sleep(500); // Adding delay between actions (adjust timing if needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished processing all products under 15k EGP.");
    }

}

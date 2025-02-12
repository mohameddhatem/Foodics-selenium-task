package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.VideoGamesPage;

public class AmazonTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private VideoGamesPage videoGamesPage;
    private CartPage cartPage;

    @BeforeClass
    public void setUpTest() {
        setUp(); // Start WebDriver
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        videoGamesPage = new VideoGamesPage(driver);
         cartPage = new CartPage(driver);
    }

    @Test
    public void testAmazonFlow() throws InterruptedException {
        driver.get(BASE_URL);
        homePage.clickSignin();
        loginPage.login("xdevious2@gmail.com","Tester21$");
        homePage.navigateToVideoGames();
        videoGamesPage.addVideoGameToCart();
        cartPage.redirecttocart();
        cartPage.proceedtobuy();



        /*// Navigate to Video Games
        homePage.navigateToVideoGames();*/

    }

    /*@AfterClass
    public void tearDownTest() {
        tearDown();
    }*/
}

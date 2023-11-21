import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class TestTask1 {
    private WebDriver driver;
    private PastebinHomePage homePage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://pastebin.com/");
        homePage = new PastebinHomePage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCreateNewPaste() {
        String code = "Hello from WebDriver";
        String expiration = "10 Minutes";
        String title = "helloweb";

        homePage.createPaste(code, expiration, title);

        // Verify that the paste is created successfully
        boolean isPasteCreated = homePage.isPasteCreated(title);
        assertTrue("Failed to create a new paste.", isPasteCreated);
    }
}

class PastebinHomePage {

    private final WebDriver driver;

    public PastebinHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void createPaste(String code, String expiration, String title){

        // Apply polit. conf.
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.xpath("//*[@id='qc-cmp2-ui']/div[2]/div/button[2]")))
                .click();

        // Enter code
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.id("postform-text")))
                .sendKeys(code);

        // to scrolling down (without it exception)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");

        // Select expiration
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.id("select2-postform-expiration-container")))
                .click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.xpath("//li[text()='" + expiration + "']")))
                .click();

        // to scrolling down (without it exception)
        js.executeScript("window.scrollBy(0,500)", "");

        // Enter title
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.id("postform-name")))
                .sendKeys(title);

        // Click on 'Create New Paste' button
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                .elementToBeClickable(By.xpath("//button[text()='Create New Paste']")))
                .click();
    }

    public boolean isPasteCreated(String title) {
        String titleOnPage = driver.getTitle();
        title = title + " - Pastebin.com";
        return title.equals(titleOnPage);
    }
}

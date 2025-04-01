import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class Basicfunc {
    WebDriver driver;
    private static final String BASE_URL = "https://duckduckgo.com/";
    private static final Duration TIMEOUT = Duration.ofSeconds(20);

    @Test
    public void openbrowser() throws IOException {
        driver = BrowserSetup.getDriver();
        try {
            driver.get(BASE_URL);
            Assert.assertEquals(driver.getTitle(), "DuckDuckGo - Protection. Privacy. Peace of mind.");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void checkLogo() throws IOException {
        driver = BrowserSetup.getDriver();
        try {
            driver.get("https://duckduckgo.com/about");
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
            List<WebElement> images = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("a[title='Go to DuckDuckGo homepage'] img")
            ));
            Assert.assertFalse(images.isEmpty(), "Logo image should exist");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void findfirstLink() throws IOException {
        driver = BrowserSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        try {
            driver.get(BASE_URL);
            WebElement inputSearch = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[name='q']")
            ));

            inputSearch.sendKeys("selenium webdriver" + Keys.RETURN);

            List<WebElement> allLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("[data-testid='result-title-a']")
            ));

            String firstLink = allLinks.get(0).getAttribute("href");
            Assert.assertEquals(firstLink, "https://www.selenium.dev/documentation/webdriver/");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void fourthTestNGLink() throws IOException {
        driver =new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        try {
            driver.get(BASE_URL);
            WebElement inputSearch = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("input[name='q']")
            ));

            inputSearch.sendKeys("TestNG" + Keys.RETURN);

            List<WebElement> allLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("a[data-testid='result-title-a']")
            ));

            boolean found = allLinks.stream()
                    .map(WebElement::getText)
                    .anyMatch(text -> text.contains("TestNG Tutorial"));

            Assert.assertTrue(found, "Expected 'TestNG Tutorial' link not found");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void checkBox() throws IOException {
        driver = BrowserSetup.getDriver();
        try {
            driver.get("http://the-internet.herokuapp.com/checkboxes");
            List<WebElement> checkBoxes = driver.findElements(By.cssSelector("input"));

            checkBoxes.get(0).click();
            checkBoxes.get(1).click();

            Assert.assertTrue(checkBoxes.get(0).isSelected(), "Checkbox 1 should be selected");
            Assert.assertFalse(checkBoxes.get(1).isSelected(), "Checkbox 2 should be deselected");
        } finally {
            driver.quit();
        }
    }

    @Test
    public void checkValueInTable() throws IOException {
        driver = BrowserSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        try {
            driver.get("https://www.w3schools.com/html/html_tables.asp");

            List<WebElement> allRows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("table#customers tr")
            ));

            Optional<String> country = allRows.stream()
                    .skip(1)
                    .map(row -> row.findElements(By.tagName("td")))
                    .filter(cells -> cells.size() >= 3)
                    .filter(cells -> cells.get(0).getText().equals("Ernst Handel"))
                    .map(cells -> cells.get(2).getText())
                    .findFirst();

            Assert.assertTrue(country.isPresent(), "Company 'Ernst Handel' not found");
            Assert.assertEquals(country.get(), "Austria", "Country mismatch");
        } finally {
            driver.quit();
        }
    }

    @Test

    public void uploadFile() throws IOException, InterruptedException {
        driver = BrowserSetup.getDriver();
        try {
            driver.get("http://the-internet.herokuapp.com/upload");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for visibility of upload field
            WebElement upload = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("file-upload")
            ));

            // Use relative file path
            String filePath = new File("C:\\Users\\dell\\Desktop\\sql_training\\training.sql").getAbsolutePath();
            upload.sendKeys(filePath);

            driver.findElement(By.id("file-submit")).click();

            // Wait for result page to load
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.tagName("h3")
            ));
            Assert.assertEquals(header.getText(), "File Uploaded!");
        } finally {
            Thread.sleep(500); // Allow time for cleanup
            driver.quit();
        }
    }

    @Test
    public void testDragAndDrop() throws IOException {
        driver = BrowserSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        try {
            driver.get("https://jqueryui.com/resources/demos/droppable/default.html");

            WebElement draggable = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.id("draggable")
            ));

            WebElement droppable = driver.findElement(By.id("droppable"));
            new Actions(driver).dragAndDrop(draggable, droppable).perform();

            Assert.assertEquals(droppable.getText(), "Dropped!", "Drag and drop failed");
        } finally {
            driver.quit();
        }
    }
}
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @Test
    public void openbrowser() throws InterruptedException, IOException {
        // Set up the ChromeDriver (Make sure the chromedriver binary is in your system PATH or specify its location)
        driver = BrowserSetup.getDriver();
        driver.get("https://duckduckgo.com/");  // Provide a valid URL
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        System.out.println(driver.getTitle());

       Assert.assertEquals(driver.getTitle() , "DuckDuckGo - Protection. Privacy. Peace of mind.");


        driver.quit();
    }

    @Test
    public  void checkLogo() throws IOException {
        driver = BrowserSetup.getDriver();
        driver.get("https://duckduckgo.com/about");  // Provide a valid URL
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));


        List<WebElement> images = driver.findElements(By.cssSelector("a[title='Go to DuckDuckGo homepage'] img"));
        Assert.assertFalse(images.isEmpty(), "Image should exist!");

        System.out.println("Number of images found: " + images.size());

        driver.quit();

    }

    @Test
    public void findfirstLink() throws IOException {
        driver = BrowserSetup.getDriver();
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        driver.get("https://duckduckgo.com/");  // Provide a valid URL
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));


        WebElement inputSearch = driver.findElement(By.cssSelector("input[name='q']"));

        inputSearch.sendKeys("selenium webdriver");
        inputSearch.sendKeys(Keys.RETURN);

        By locatorofLinks = By.cssSelector("[data-testid='result-title-a']");
        List <WebElement> allLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                locatorofLinks
        ));

        String getThelink = allLinks.get(0).getDomAttribute("href");

        System.out.println("nabiiiiiiil");
        System.out.println(getThelink);
        //Assert.assertFalse(images.isEmpty(), "Image should exist!");
        Assert.assertEquals(getThelink, "https://www.selenium.dev/documentation/webdriver/");


        driver.quit();


    }

    @Test
    public void fourthTestNGLink()
    {

        driver =new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        driver.get("https://duckduckgo.com/");  // Provide a valid URL
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));


        WebElement inputSearch = driver.findElement(By.cssSelector("input[name='q']"));

        inputSearch.sendKeys("TestNG");
        inputSearch.sendKeys(Keys.RETURN);

        By locatorofLinks = By.cssSelector("a[data-testid='result-title-a'] span");
        List <WebElement> allLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                locatorofLinks
        ));
        System.out.println("nabiiiiiiiiil");
        String getThelink = allLinks.get(3).getText();
         String[] title = getThelink.split(":");

        Assert.assertEquals(title[0],"TestNG Tutorial");

    }

    @Test
    public void checkBox() throws IOException {
        driver = BrowserSetup.getDriver();
        driver.get("http://the-internet.herokuapp.com/checkboxes");

        List<WebElement> checkBoxs = driver.findElements(By.cssSelector("input"));

        WebElement checkBox1 = checkBoxs.get(0);
        WebElement checkBox2 = checkBoxs.get(1);
        checkBox1.click();
        checkBox2.click();
    }

    @Test
    public void CheckvalueinTable() throws IOException {

        driver = BrowserSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
        driver.get("https://www.w3schools.com/html/html_tables.asp");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> allRows = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector("table#customers tr")));

        // Process rows using Stream
        Optional<String> country = allRows.stream()
                .skip(1) // Skip header row (index 0)
                .map(row -> row.findElements(By.tagName("td")))
                .filter(cells -> cells.size() >= 3)
                .filter(cells -> cells.get(0).getText().trim().equals("Ernst Handel"))
                .map(cells -> cells.get(2).getText().trim())
                .findFirst();

        // Assertions
        Assert.assertTrue(country.isPresent(), "Company 'Ernst Handel' not found!");
        Assert.assertEquals(country.get(), "Austria", "Country mismatch!");
    }

    @Test
    public void UploadFile() throws IOException {

        driver = BrowserSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
        driver.get("http://the-internet.herokuapp.com/upload");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement upload = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("file-upload")
        ));

        upload.sendKeys(new File("C:\\Users\\dell\\Desktop\\sql_training\\training.sql").getAbsolutePath() );
        driver.findElement(By.id("file-submit")).click();

        WebElement header = driver.findElement(By.tagName("h3"));

        Assert.assertEquals(header.getText(), "File Uploaded!");


    }

    @Test
    public void Dragging() throws InterruptedException, IOException {

        driver = BrowserSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait
        driver.get("https://jqueryui.com/resources/demos/droppable/default.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement draggable = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("draggable")
        ));

        WebElement droppable = driver.findElement(By.id("droppable"));

        new Actions(driver).dragAndDrop(draggable,droppable).build().perform();

        //Thread.sleep(2000);

        driver.close();




    }
}





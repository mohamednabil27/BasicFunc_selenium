import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BrowserSetup {
    private static WebDriver driver;
    private static final String PROPERTIES_PATH = "src/main/resources/config.properties";

    public static WebDriver getDriver() throws IOException {
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(PROPERTIES_PATH);
        properties.load(input);
        input.close();

        String browser = properties.getProperty("TargetBrowser").toLowerCase();

        switch (browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        return driver;
    }
}
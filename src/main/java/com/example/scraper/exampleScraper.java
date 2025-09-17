import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class ExampleScraper {
    
    public static void main(String[] args) {
        WebDriver driver = null;
        
        try {
            // Setup ChromeDriver automatically (works with Chromium too)
            WebDriverManager.chromedriver().setup();
            
            // Set up Chromium options for headless mode
            ChromeOptions options = new ChromeOptions();
            
            // Set Chromium binary location
            String chromiumPath = getChromiumPath();
            if (chromiumPath != null) {
                options.setBinary(chromiumPath);
                System.out.println("Using Chromium binary: " + chromiumPath);
            }
            
            // Chromium-optimized arguments
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-plugins");
            options.addArguments("--remote-debugging-port=9222");
            
            // Initialize ChromeDriver (works with Chromium)
            driver = new ChromeDriver(options);
            
            // Set up WebDriverWait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Navigate to the website
            System.out.println("Navigating to example.com...");
            driver.get("https://example.com");
            
            // Wait for the page to load and find elements
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
            
            // Scrape data
            String title = driver.getTitle();
            System.out.println("Page Title: " + title);
            
            // Get the main heading
            WebElement heading = driver.findElement(By.tagName("h1"));
            System.out.println("Main Heading: " + heading.getText());
            
            // Get all paragraphs
            System.out.println("\nParagraphs:");
            var paragraphs = driver.findElements(By.tagName("p"));
            for (int i = 0; i < paragraphs.size(); i++) {
                System.out.println("Paragraph " + (i + 1) + ": " + paragraphs.get(i).getText());
            }
            
            // Get all links
            System.out.println("\nLinks:");
            var links = driver.findElements(By.tagName("a"));
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                String linkText = link.getText();
                if (href != null && !href.isEmpty()) {
                    System.out.println("Link: " + linkText + " -> " + href);
                }
            }
            
            System.out.println("\nScraping completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error occurred during scraping: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up and close the browser
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed.");
            }
        }
    }
}
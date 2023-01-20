package packageTestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	// Fields list
	By USERNAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@name='login']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//div[@id='page-wrapper']/div[2]/div/h2");

	// Test Data
	String username = "demo@techfios.com";
	String password = "abc123";
	String dashboard = "Dashboard";
	String browser = "Chrome";

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("Firefox")) {

			System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
			driver = new ChromeDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://techfios.com/billing/?ng=admin/");

	}
	//@Test in TestNg runs in alphabetic order if there is more than one @Test annotations in a class

	@Test
	public void loginTestNG() throws InterruptedException {
		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboard,
				"DashBoard page not found");

	}

	@AfterMethod
	public void tearDown() {
		driver.close();// comment driver.close() when browser="Firefox"
		driver.quit();
	}
}

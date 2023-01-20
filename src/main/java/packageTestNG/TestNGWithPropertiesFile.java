package packageTestNG;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGWithPropertiesFile {
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
	/*
	 * In real time or industry or work place we are not allowed to manipulate the
	 * data in our code. In order to do data manipulations //we need to use
	 * properties file where we store the data and values and then link it to out
	 * code by reading the file and assigning the //variables the values from that
	 * file
	 */
	String browser;// going to take browser and url values from properties file or make browser=null;
	String url;
	
	@BeforeClass
	public void readConfig() {
		/*
		 * Java allows us to read a file through 4 classes: 1.Scanner 2. InputStream
		 * 3.BufferedReader and 4.FileReader
		 */
		try {
			InputStream input = new FileInputStream("/Users/comet/SeleniumWorkspace/session5AtHome_TestNG/src/main/java/config/config.properties");// location of .properties file																							
			Properties prop = new Properties();//to make Java understand that it is a key value pair file
			prop.load(input);
			browser = prop.getProperty("browser");// get value of browser and assign it to String variable browser in the code
			url=prop.getProperty("url");//get value of url and assign it to String variable url in the code
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		driver.get(url);

	}

	@Test
	public void loginTestNG() throws InterruptedException {
		driver.findElement(USERNAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Thread.sleep(2000);
		//In TestNG Assert.assertEquals(actual,expected,errormessage)
		//In JUnit Assert.assertEquals(errormessage,expected, actual)
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboard,"DashBoard page not found");

	}

	@AfterMethod
	public void tearDown() {
		driver.close();// comment driver.close() when browser="Firefox"
		driver.quit();
	}

}

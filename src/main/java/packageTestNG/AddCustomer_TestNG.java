package packageTestNG;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddCustomer_TestNG {
	WebDriver driver;
	
	// Fields List
	By USERNAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@name='login']");
	By DASHBOARD_HEADER_FIELD=By.xpath("//div[@id='page-wrapper']/div[2]/div/h2");
	By CUSTOMER_FIELD=By.xpath("//span[text()='Customers']");
	By ADDCUSTOMER_FIELD=By.xpath("//a[text()='Add Customer']");
	By CONTACTSHEADER_FIELD=By.xpath("//div[@id='page-wrapper']/div[2]/div/h2");
	By FULLNAME_FIELD=By.xpath("//input[@id='account']");
	By COMPANY_DROPDOWN_FIELD=By.xpath("//select[@id='cid']");
	By EMAIL_FIELD=By.xpath("//input[@id='email']");
	By PHONE_FIELD=By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD=By.xpath("//input[@id='address']");
	By CITY_FIELD=By.xpath("//input[@id='city']");
	By STATEREGION_FIELD=By.xpath("//input[@id='state']");
	By ZIPPOSTAL_FIELD=By.xpath("//input[@id='zip']");
	By COUNTRYDROPDOWN_FIELD=By.xpath("//select[@id='country']");
	By TAGSDROPDOWN_FIELD=By.xpath("//select[@id='tags']");
	By CURRENCYDROPDOWN_FIELD=By.xpath("//select[@id='currency']");
	By GROUPDROPDOWN_FIELD=By.xpath("//select[@id='group']");
	By PASSWORD_FIELD2=By.xpath("//input[@id='password']");
	By CONFIRMPASSWORD_FIELD=By.xpath("//input[@id='cpassword']");
	By WELCOMEEMAILCHECKBOX_FIELD=By.xpath("//label[text()='Yes']");
	By SAVEBUTTON_FIELD=By.xpath("//button[@id='submit']");
	By ERRORMESSAGE_FIELD=By.xpath("//span[@id='emsgbody']");
	By LISTOFCUSTOMERS_FIELD=By.xpath("//a[text()='List Customers']");
	By SEARCHLISTOFCUSTOMERS_FIELD=By.xpath("//input[@id='foo_filter']");
	
	

	// Test Data
	int randomNumber=generateRandom(999);//calling the method and 
	//storing the returned random number in a var. using this random no to create a unique username email and phone
	String username = "demo@techfios.com";
	String password = "abc123";
	String FullName="Shad1"+randomNumber;//fullname will now be unique
	String Email="shad1"+randomNumber+"@gmail.com";
	String Phone="3456"+randomNumber;
	String Address="10-9-89 abc def";
	String City="Coppell";
	String State="Texas";
	String Zip="123445";
	String password2="abcdef";
	String confirmPassword=password2;
	String text_CompanyDropdown="Amazon";
	String text_CountryDropdown="United States";
	String text_tagsDropdown="Nicho";
	String text_currencyDropdown="USD";	
	String text_groupDropdown="Java";
	String searchInput=FullName;
	
	@BeforeMethod
	public void init() {
		System.setProperty("webdriver.chrome.driver", "/Users/comet/SeleniumWorkspace/driver/chromedriver");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://techfios.com/billing/?ng=admin/");
	}
	//@Test in TestNG runs in alphabetic order if there is more than one @Test annotations in a class
	//@Test(priority=1)//This method will run after addNewContact because @Test runs alphabetically when we dont assign priority
	public void loginTest() throws InterruptedException {
		
			driver.findElement(USERNAME_FIELD).sendKeys(username);
			driver.findElement(PASSWORD_FIELD).sendKeys(password);
			driver.findElement(SIGNIN_BUTTON_FIELD).click();
			Thread.sleep(2000);
		    Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard", "Dashboard page not found");
}
	@Test(priority=2) // //This method will run before loginTest because @Test runs alphabetically in TestNG without priority
	//In TestNG we can prioritize methods and have control over which method should run first
	//the html test report will have alphabetic order of @Test methods . Priority is for execution order
	public void addNewContact() throws InterruptedException {
		
		loginTest();
		driver.findElement(CUSTOMER_FIELD).click();
		driver.findElement(ADDCUSTOMER_FIELD).click();
		explicitWait(driver,30,FULLNAME_FIELD);
		//asserting by seeing if full name field is displayed or not since this field is unique to this page
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Full Name']")).isDisplayed(),"Add Contact page not available");
		
		driver.findElement(FULLNAME_FIELD).sendKeys(FullName);
		
		selectFromDropDown(COMPANY_DROPDOWN_FIELD,text_CompanyDropdown);//calling method selectFromDropDown() and passing arg for drop down
		
		driver.findElement(EMAIL_FIELD).sendKeys(Email);
		driver.findElement(PHONE_FIELD).sendKeys(Phone);
		driver.findElement(ADDRESS_FIELD).sendKeys(Address);
		driver.findElement(CITY_FIELD).sendKeys(City);
		driver.findElement(STATEREGION_FIELD).sendKeys(State);
		driver.findElement(ZIPPOSTAL_FIELD).sendKeys(Zip);
		
		selectFromDropDown(COUNTRYDROPDOWN_FIELD,text_CountryDropdown);
		selectFromDropDown(TAGSDROPDOWN_FIELD,text_tagsDropdown);
		selectFromDropDown(CURRENCYDROPDOWN_FIELD,text_currencyDropdown);
		selectFromDropDown(GROUPDROPDOWN_FIELD,text_groupDropdown);
		
		driver.findElement(PASSWORD_FIELD2).sendKeys(password2);
		driver.findElement(CONFIRMPASSWORD_FIELD).sendKeys(confirmPassword);
		driver.findElement(WELCOMEEMAILCHECKBOX_FIELD).click();
		driver.findElement(SAVEBUTTON_FIELD).click();
		explicitWait(driver, 30, By.id("summary"));//asserting that we are on profile page and profile got created
		
		driver.findElement(LISTOFCUSTOMERS_FIELD).click();
		explicitWait(driver, 50,SEARCHLISTOFCUSTOMERS_FIELD );
		driver.findElement(SEARCHLISTOFCUSTOMERS_FIELD).sendKeys(searchInput);
		explicitWait(driver,50,By.xpath("//div[@id='page-wrapper']/descendant::div[10]/div[2]/table/descendant::td[3]/a"));
		
		//option1 : xpath for clicking on customer name and going to details page
		//driver.findElement(By.xpath("//a[text()='"+ FullName+"']")).click();
		/*concatenating 3 strings here the 
		value of Text keeps changing and it will be same as FullName and hence giving the value here by concatenating it*/
		//option 2: xpath for clicking on customer name and going to details page
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='page-wrapper']/descendant::div[10]/div[2]/table/descendant::td[3]/a")).click();
		explicitWait(driver, 50,By.xpath("//div[@class='ibox-title']") );
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='ibox-title']")).getText(),FullName,"Profile page not found");
		//OR
		//Assert.assertEquals("Profile page not found",FullName,driver.findElement(By.xpath("//div[@id='page-wrapper']/descendant::div[20]/descendant::div[1]/h5")).getText());
	}
/*creating a customized generic method for all drop down elements using Select class 
which we are calling whenever we need to use drop down instead of creating multiple objects of Select class*/
	public void selectFromDropDown(By byLocator, String visibleText) {
		
		Select sel=new Select(driver.findElement(byLocator));
		sel.selectByVisibleText(visibleText);
	}
	//creating a customized generic method for generating random numbers
	public int generateRandom(int boundaryNo) {
		Random rn=new Random();//generating random numbers and concatenating it with user name so that we get a unique name each time
		//generates random nos upto boundaryNo
		int randomNumber=rn.nextInt(boundaryNo);
		return randomNumber;
		
	}
	//creating a customized generic method for generating Explicit wait
	public void explicitWait(WebDriver driver,int time,By byLocator) {
		WebDriverWait wait=new WebDriverWait(driver,time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
		
	}
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
		
	}
}

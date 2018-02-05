package cognizantProject;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.selenium.keywords.SeleniumKeywords;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NewEggTest
{
	SeleniumKeywords keyword= new SeleniumKeywords();
	public static WebDriver driver;
	
	@Given("^Open newegg Login page in GoogleChrome browser$")
	public void open_newegg_Login_page_in_GoogleChrome_browser() throws Exception {
		this.driver=keyword.driver;
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		driver= new ChromeDriver();
		driver.get("https://www.newegg.com/");
		driver.manage().window().maximize();
	    driver.findElement(By.xpath("//a[starts-with(text(),'Log')]")).click();
		keyword.waitForPageToLoad();
	}

	@When("^I enter valid login details$")
	public void i_enter_valid_login_details() throws Exception {
		driver.findElement(By.xpath("//input[@type='text'][@name='UserName']")).sendKeys("ctschallenge20@gmail.com");
		driver.findElement(By.xpath("//input[@type='password'][@name='UserPwd']")).sendKeys("Challenge20");
		driver.findElement(By.xpath("//input[@type='submit'][@name='submit']")).click();
	}

	@Then("^verify user home page$")
	public void verify_user_home_page() throws Exception {
		boolean flg=driver.findElement(By.xpath("//ins[text()='My Account']")).isDisplayed();
		System.out.println("HomePage:"+flg);
		if(flg==true){
			System.out.println("New EGG Login Verified successfully");
		}else{
			System.out.println("New EGG Login Verification failed");
		}
	}

	@Then("^Search for the items given in the excel file and add them to cart$")
	public void search_for_the_items_given_in_the_excel_file_and_add_them_to_cart() throws Exception {
		ArrayList<String> al= new ArrayList<String>();
		Recordset recordset=null;
		Connection connection=null;
		try{
			Fillo fillo=new Fillo();
			connection=fillo.getConnection("D:\\Workplace\\newEGG.xlsx");
			String strQuery="Select * from NewEGG";
			recordset=connection.executeQuery(strQuery);
			while(recordset.next()){
				String price=recordset.getField("Price").trim().split("\\$")[1];
				al.add(recordset.getField("SearchItem").trim()+ ","+recordset.getField("Item").trim()+","+price.trim());
				System.out.print(recordset.getField("SearchItem").trim()+ ","+recordset.getField("Item").trim()+","+price.trim());
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			recordset.close();
			connection.close();
		}
		for (int i = 0; i <= al.size()-1; i++) {
			String[] str=al.get(i).split(",");
			System.out.println();
			driver.findElement(By.xpath("//input[@id='haQuickSearchBox']")).sendKeys(str[0]);	
			driver.findElement(By.xpath("//button[text()='Search']")).click();
			/*if(driver.findElements(By.xpath("//div[contains(@id,'centerPopup')]//a[@class='fa fa-close centerPopup-close']")).size()>0){
				driver.findElement(By.xpath("//div[contains(@id,'centerPopup')]//a[@class='fa fa-close centerPopup-close']")).click();
			}*/
			driver.findElement(By.xpath("//input[@id='SrchInDesc_top']")).sendKeys(str[1]);
			driver.findElement(By.xpath("//button[@id='btn_InnerSearch']")).click();
			driver.get(driver.getCurrentUrl());
			String[] item=str[2].split("\\.");
			
				driver.findElement(By.xpath(""
						+ "//strong[contains(text(),'"+item[0]+"')]/following-sibling::sup[contains(text(),'"+item[1]+"')]"
								+ "/ancestor::div[@class='item-info']//a[@title='View Details']")).click();

			driver.findElement(By.xpath("//div[@id='landingpage-cart']//span[text()='ADD TO CART ']")).click();
		}
	}

	@Then("^Validate whether the right products are added into the cart$")
	public void validate_whether_the_right_products_are_added_into_the_cart() throws Exception {
		driver.findElement(By.xpath("//a[@title='Shopping Cart with Items']")).click();
	}
	
	@Then("^edit and remove an item from cart$")
	public void edit_and_remove_an_item_from_cart() throws Exception {
	    List<WebElement> ctItem = driver.findElements(By.xpath("//input[@name='CHKITEM']"));
	    ctItem.get(1).click();
	    driver.findElement(By.xpath("//*[@id='removeFromCart']")).click();
	}
	
	@Then("^click on checkout and logout$")
	public void click_on_checkout_and_logout() throws Exception {
		Thread.sleep(3000);
	    driver.findElement(By.xpath("//a[@title='Secure Checkout']")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/div[2]/div[1]/div/div[4]/div/div/a")).click();
	    Thread.sleep(5000);
	    driver.navigate().to("https://secure.newegg.com/Shopping/ShoppingCart.aspx?submit=ChangeItem");
	    Thread.sleep(3000);
	    WebElement w1 = driver.findElement(By.xpath("//ins[contains(text(),'My Account')]"));
	    new Actions(driver).moveToElement(w1).perform();
	    Thread.sleep(4000);
	    driver.findElement(By.xpath("//div[@class='top-nav-menu']/div/ul/li[4]/a")).click();
	}
	
	@Then("^login to forgot password$")
	public void login_to_forgot_password() throws Exception {
		driver.findElement(By.xpath("//a[starts-with(text(),'Log')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='text'][@name='UserName']")).sendKeys("ctschallenge20@gmail.com");
		driver.findElement(By.xpath("//a[@title='Forgot your password?']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='loginname']")).sendKeys("ctschallenge20@gmail.com");
		driver.findElement(By.xpath("//input[@name='submit']")).click();		
	}

	@Then("^open gmail and verify the rest password$")
	public void open_gmail_and_verify_the_rest_password() throws Exception {
		((JavascriptExecutor)driver).executeScript("window.open()");
	    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
		driver.get("https://mail.google.com/mail/");
		driver.findElement(By.xpath("//a[text()='Sign In']")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("identifierId")).sendKeys("ctschallenge20@gmail.com");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("2018@Challenge");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='gb_Nc gb_jb gb_Fg gb_R']/a/span")).click();
		driver.findElement(By.xpath("//*[@id='gb_71']")).click();
		driver.switchTo().window(tabs.get(0));
		Thread.sleep(3000);
	}
	
	@Then("^application should be closed$")
	public void application_should_be_closed() throws Exception {
	    driver.quit();
	}
	
	

}
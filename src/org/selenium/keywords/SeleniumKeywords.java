package org.selenium.keywords;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumKeywords
{
	public WebDriver driver;
	public  void click(String xpath){
		if(verifyElementEnabled(xpath)){
			driver.findElement(By.xpath(xpath)).click();
		}
	}
	public  void selectByIndex(String xpath,int index){
		if(verifyElementEnabled(xpath)){
			Select se= new Select(driver.findElement(By.xpath(xpath)));
			se.selectByIndex(index);
		}
	}
	public  void selectByText(String xpath,String optionText){
		if(verifyElementEnabled(xpath)){
			Select se= new Select(driver.findElement(By.xpath(xpath)));
			se.selectByVisibleText(optionText);
		}
	}
	public  void selectByValue(String xpath,String optionValue){
		if(verifyElementEnabled(xpath)){
			Select se= new Select(driver.findElement(By.xpath(xpath)));
			se.deselectByValue(optionValue);
		}
	}
	public  void enterText(String xpath,String data){
		if(verifyElementEnabled(xpath)){
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		}
	}
	public  void clearTextField(String xpath){
		if(verifyElementEnabled(xpath)){
			driver.findElement(By.xpath(xpath)).clear();
		}
	}
	//for radio button and checkbox elements
	public  void Check(String xpath,String data){
		if(verifyElementEnabled(xpath)){
			if(driver.findElement(By.xpath(xpath)).isSelected()==false){
				driver.findElement(By.xpath(xpath)).click();
			}
		}
	}
	
	//for radio button and checkbox elements
	public  void UnCheck(String xpath,String data){
		if(verifyElementEnabled(xpath)){
			if(driver.findElement(By.xpath(xpath)).isSelected()==true){
				driver.findElement(By.xpath(xpath)).click();
			}
		}
	}
	public  boolean verifyElementEnabled(String xpath){
		boolean enableflg=false;
		if(verifyElementDisplayed(xpath)){
			if(driver.findElement(By.xpath(xpath)).isEnabled()){
				//System.out.println("Element is Enabled");
				enableflg= true;
			}else{
				//System.out.println("Element is Displayed but not Enabled");
				enableflg= false;
			}
		}else{
			enableflg=false;
		}
		
	return enableflg;
	}
	public  boolean verifyElementDisplayed(String xpath){
		boolean flg=false;
		if(findElements(xpath)>=1){
			if(driver.findElement(By.xpath(xpath)).isDisplayed()){
				System.out.println(xpath+" Element is displayed");
				flg= true;
			}else{
				System.out.println(xpath+" Element is present but not displayed");
				flg=false;
			}
			
		}else{
			System.out.println("Element is not present and also not displayed");
			flg= false;
		}
		return flg;
	}
	
	public  boolean verifyElementPresent(String xpath){
		if(findElements(xpath)>0){
			return true;
		}else{
			//System.out.println("Element is not present");
			return false;
		}
		
		
	}
	public  int findElements(String xpath){
		int c=0;
		try{
			
			System.out.println("Title:"+driver.getTitle());
		c= driver.findElements(By.xpath(xpath)).size();
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return c;
	}
	public  void launchURL(String URL){
		driver.get(URL);
	}
	public  void ffBrowser(){
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
		driver= new FirefoxDriver(); // firefox browser launcher
	}
	
	public void gcBrowser(){
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		//no need make any changes here and default it takes from the project folder
		driver= new ChromeDriver(); // firefox browser launcher
	}
	
	public  void switchTOWindow(String xpath) {
		WebDriver popup=null;
			Iterator<String> windowitIterator=driver.getWindowHandles().iterator();
			while(windowitIterator.hasNext()){
				String WindowsHandle = windowitIterator.next();
				popup=driver.switchTo().window(WindowsHandle);
				
				if (popup.findElements(By.xpath(xpath)).size()>=1) {			
					System.out.println("SWITCHTOWINDOW PASS :: WINDOW SELECTED ");
					break;	
				}
				
			}

	}
	public void waitForElementTobeDisplayed(String xpath,int millsec) throws InterruptedException{
		int cntr=0;
		while(verifyElementDisplayed(xpath)==false){
			if(cntr==30){
				System.out.println("Reached max time limit for element to be displayed is:"+cntr+" millis secnds");
				break;
			}
			Thread.sleep(1000);
			cntr++;
		
		}
	}
	public void waitForPageToLoad() {
		try{
					ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			        public Boolean apply(WebDriver driver) {
			          return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			        }
			      };
			      
			      WebDriverWait wait = new WebDriverWait(this.driver,60);
			      System.out.println("waitForPageToLoad is initiated with a timeout of "+ 60 + " seconds");
				      try {
				              wait.until(expectation);
				      } catch(Throwable error) {
				    	  System.out.println("Timeout waiting for Page Load Request to complete."+error.getMessage());
				    	  System.out.println("Timeout waiting for Page Load Request to complete."+error.getMessage());
				      }
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	
	}
}

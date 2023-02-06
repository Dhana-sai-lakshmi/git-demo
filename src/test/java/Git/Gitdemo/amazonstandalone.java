package Git.Gitdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class amazonstandalone {
	WebDriver driver;

	@BeforeTest(alwaysRun = true)
	public void launchingBrowser() {
		driver = new ChromeDriver();
		driver.navigate().to("https://www.amazon.in/");
		//driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
	}

	@Test(dataProvider = "drivenTest", priority = 1)
	public void testCaseData(String Email, String password) throws IOException {
		driver.findElement(By.xpath("//a[@data-csa-c-content-id='nav_ya_signin']")).click();
		driver.findElement(By.id("ap_email")).sendKeys(Email);
		driver.findElement(By.cssSelector(".a-button-input")).click();
		driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys(password);
		driver.findElement(By.id("signInSubmit")).click();
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("D:\\Amazon\\Screenshot"));	
		

	}

	@DataProvider(name = "drivenTest")
	public Object[][] getExceldata() throws IOException {
		DataFormatter formatter = new DataFormatter();
		FileInputStream fis = new FileInputStream("D:\\Amazon\\AmazonUserNamePassword.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheetname = wb.getSheetAt(0);
		int rownumbers = sheetname.getPhysicalNumberOfRows();
		XSSFRow row = sheetname.getRow(0);
		int columncount = row.getLastCellNum();
		Object[][] data = new Object[rownumbers - 1][columncount];
		for (int i = 0; i < rownumbers - 1; i++) {
			row = sheetname.getRow(i + 1);
			for (int j = 0; j < columncount; j++) {
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell);
			}
		}
		return data;

	}

	@Test(priority = 2)
	public void search() throws IOException {
		Properties pro=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources.properties");
		pro.load(fis);
		String SearchItem=pro.getProperty("Search");
		//String IteamName="Samsung Galaxy M04 Light Green";
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(SearchItem);
		driver.findElement(By.id("nav-search-submit-button")).click();	
		List<WebElement> Searchlist = driver.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
		for (int i = 0; i < Searchlist.size(); i++)
		{
			System.out.println(Searchlist.get(i).getText());	

		}
		 driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']")).click();
		}
	@Test(priority=3)
	public void AddToCart() throws InterruptedException, IOException
	{
		Set<String> allwindows=driver.getWindowHandles();
		Iterator<String> it=allwindows.iterator();
		String parent=it.next();
		String Child=it.next();
	
		System.out.println( parent);
		System.out.println( Child);
		driver.switchTo().window(Child);
		
		
		Thread.sleep(000);
	    driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
	    WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(4));
	    
	    /*if(true)
	    {
	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@data-feature-id='proceed-to-checkout-action']")));
	    driver.findElement(By.xpath("//input[@data-feature-id='proceed-to-checkout-action']")).click();
	    }*/
	    {
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='attach-sidesheet-checkout-button']")));
	    WebElement ProceedToCheck=driver.findElement(By.xpath("//span[@id='attach-sidesheet-checkout-button']"));
	    }	
	   
		File src1=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src1, new File("D:\\Amazon\\Screenshot1"));
		driver.switchTo().window(parent);
		File src2=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src2, new File("D:\\Amazon\\Screenshot2"));	
	}
	@Test(priority=4)
	public void LogOut() throws IOException, InterruptedException
	{
		Thread.sleep(3000);
		
		
		WebElement Signoutoptions=driver.findElement(By.xpath("//span[text()='Hello, dhana']"));
		Actions act=new Actions(driver);
		act.moveToElement(Signoutoptions).perform();
		driver.findElement(By.xpath("//span[text()='Sign Out']")).click();
		File src3=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src3, new File("D:\\Amazon\\Screenshot3"));	
	}
	@AfterTest()
	public void Teardown()
	{
		driver.quit();
	}

}

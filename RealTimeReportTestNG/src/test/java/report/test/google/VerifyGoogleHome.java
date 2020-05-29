package report.test.google;

import java.net.URL;
import java.net.URLClassLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;

public class VerifyGoogleHome {
	WebDriver driver;

	@BeforeSuite
	public void beforeSuite() {
		ChromeDriverManager.chromedriver().setup();
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
	}

	@Test
	public void testGoogleHome() {
		driver.get("https://www.google.com");
	}
	@Test(dataProviderClass=TestDataPro.class,dataProvider="getdata")
	public void testGoogleWithSearch(String searchData) {
		driver.get("https://www.google.com");
		driver.findElement(By.name("q")).sendKeys(searchData);
	}
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
	public static void main(String[] args) {
		   ClassLoader cl = ClassLoader.getSystemClassLoader();

		    URL[] urls = ((URLClassLoader)cl).getURLs();

		    for(URL url: urls){
		        System.out.println(url.getFile());
		    }
	}
}
